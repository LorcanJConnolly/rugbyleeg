package world;

import components.inputs.Inputs;
import components.rugby.position.PositionRegistry;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.middleware.DebugMiddleware;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdatePipeline;
import ecs.pipelines.update.UpdateSystem;
import input.KeyHandler;
import rugby.positions.Position;
import stores.*;
import systems.render.kinematic.TransformRender;
import systems.render.pitch.PitchRender;
import systems.update.events.FlushEventBusSystem;
import systems.update.game.GameStateSystem;
import systems.update.inputs.InputSystem;
import systems.update.kicking.KickBallSystem;
import systems.update.kickoff.formation.KickOffFormationSystem;
import systems.update.kickoff.kick.KickOffInput;
import systems.update.kickoff.setup.KickOffSetupSystem;
import systems.update.physics.gravity.GravitySystem;
import systems.update.physics.kinematics.MotionSystem;
import systems.update.physics.kinematics.TransformSystem;
import util.fileloaders.JsonLoader;
import util.vectors.Vector2;
import world.configurators.*;
import world.templates.WorldTemplate;
import world.templates.entities.*;

import java.util.HashMap;
import java.util.Map;

public class WorldBuilder {
    private final int maxEntities;
    private final World world;
    private final EventBus eventBus;
    private final CommandBus commandBus;
    private final KeyHandler keyH;


    public WorldBuilder(KeyHandler keyHandler, int maxEntities) {
        this.keyH = keyHandler;
        this.maxEntities = maxEntities;
        this.world = new World(this.maxEntities);
        this.eventBus = world.getEventBus();
        this.commandBus = world.getCommandBus();
    }


    public World load(String path){

        WorldTemplate world_template = JsonLoader.load(path, WorldTemplate.class);

        // Create stores
        createStores();

        // Create entities
        int attack = world.createEntity();
        int defence = world.createEntity();
        int pitch = world.createEntity();
        int ball = world.createEntity();

        // Create entity components
        createTeam(attack, world_template.homeTeam);
        createTeam(defence, world_template.awayTeam);
        createPitch(pitch, world_template.pitch);
        createBall(ball, world_template.ball);

        // Lazily assign player controls
        int player = world.getEntityComponent(attack, PositionRegistry.class).getEntity(Position.HALFBACK);
        createGame(world_template.game, attack, defence, ball, pitch, player);
        keyH.bind(world.getEntityComponent(player, Inputs.class));

        // register systems to world and their associated command handlers and event subscriptions.
        addUpdateSystems();
        addRenderSystems(true);
        // Event subscribers and Command handlers
        populateBusses();

        // Emit event that initial state of the world can be configured off using systems.
//        eventBus.emit(new NewGameStarted());

        return world;
    }


    public void createStores(){
        world.registerStore(new MotionStore(this.maxEntities));
        world.registerStore(new TransformStore(this.maxEntities));
        world.registerStore(new RugbyPositionStore(this.maxEntities));
        world.registerStore(new DirectionsStore(this.maxEntities));
        world.registerStore(new GameStateStore(this.maxEntities));
        world.registerStore(new SingletonEntitiesStore(this.maxEntities));
        world.registerStore(new PitchDimensionsStore(this.maxEntities));
        world.registerStore(new InputsStore(this.maxEntities));
        world.registerStore(new MemberStore(this.maxEntities));
        world.registerStore(new ZAxisStore(this.maxEntities));
        world.registerStore(new PositionRegistryStore(this.maxEntities));
    }


    private Integer createPlayer(String path, Position position, int team){
        System.out.println("Creating a player entity '" + position + "' for the team '" + team + "'.");
        if (path == null) return null;
        PlayerTemplate player = JsonLoader.load(path, PlayerTemplate.class);
        int entity = world.createEntity();
        PlayerConfig.Builder builder = PlayerConfig.builder();

        if (player.transform != null){
            // Vectors constructed using template.utils.VectorTemplate
            double x = player.transform.position.x != null ? player.transform.position.x : 0.0;
            double y = player.transform.position.y != null ? player.transform.position.y : 0.0;
            // Transform position is required.
            builder.transform(new Vector2(x, y), b -> {
                if (player.transform.orientation != null) b.orientation(player.transform.orientation);
            });
        }
        if (player.motion != null){
            builder.motion(b -> {
                if (player.motion.velocity != null) b.velocity(player.motion.velocity.toVector2(0.0, 0.0));
                if (player.motion.rotation != null) b.rotation(player.motion.rotation);
            });
        }
        if (position != null){
            builder.position(position);
        }

        builder.member(team);
        builder.inputs();

        System.out.println("Created a player entity '" + position + "' for the team '" + team + "'.");
        builder.build().createPlayer(world, entity);
        return entity;
    }


    private void createTeam(int entity, String path){
        TeamTemplate team = JsonLoader.load(path, TeamTemplate.class);
        TeamConfig.Builder builder = TeamConfig.builder();

        Map<Position, Integer> position_to_entity = new HashMap<>();

        if (team.directions != null){
            builder.direction(b -> {
                if (team.directions.forward != null) b.forward(team.directions.forward);
                if (team.directions.backwards != null) b.backwards(team.directions.backwards);
                if (team.directions.left != null) b.left(team.directions.left);
                if (team.directions.right != null) b.right(team.directions.right);
            });
        }
        builder.positionRegistry();

        System.out.println("Adding team entity to the world");
        builder.build().createTeam(entity, world);

        // Register entities to position registry
        PositionRegistry positionRegistry = world.getEntityComponent(entity, PositionRegistry.class);
        position_to_entity.put(Position.PROP_8, createPlayer(team.prop_8, Position.PROP_8, entity));
        position_to_entity.put(Position.PROP_10, createPlayer(team.prop_10, Position.PROP_10, entity));
        position_to_entity.put(Position.HOOKER, createPlayer(team.hooker, Position.HOOKER, entity));
        position_to_entity.put(Position.SECOND_ROW_11, createPlayer(team.second_row_11, Position.SECOND_ROW_11, entity));
        position_to_entity.put(Position.SECOND_ROW_12, createPlayer(team.second_row_12, Position.SECOND_ROW_12, entity));
        position_to_entity.put(Position.LOCK, createPlayer(team.lock, Position.LOCK, entity));
        position_to_entity.put(Position.HALFBACK, createPlayer(team.halfback, Position.HALFBACK, entity));
        position_to_entity.put(Position.FIVE_EIGHTH, createPlayer(team.five_eighth, Position.FIVE_EIGHTH, entity));
        position_to_entity.put(Position.CENTRE_4,  createPlayer(team.centre_4, Position.CENTRE_4, entity));
        position_to_entity.put(Position.CENTRE_3, createPlayer(team.centre_3, Position.CENTRE_3, entity));
        position_to_entity.put(Position.WING_5, createPlayer(team.wing_5, Position.WING_5, entity));
        position_to_entity.put(Position.WING_2, createPlayer(team.wing_2, Position.WING_2, entity));
        position_to_entity.put(Position.FULLBACK, createPlayer(team.fullback, Position.FULLBACK, entity));
        for (Map.Entry<Position, Integer> entry : position_to_entity.entrySet()) {
            positionRegistry.register(entry.getKey(), entry.getValue());
        }
    }


    private void createPitch(int entity, String path){
        PitchTemplate pitch = JsonLoader.load(path, PitchTemplate.class);
        PitchConfig.Builder builder = PitchConfig.builder();

        if (pitch.dimensions != null){
            builder.dimensions( b-> {
                if (pitch.dimensions.aabb != null) b.aabb(pitch.dimensions.aabb.toAABB());
                if (pitch.dimensions.leftTouch != null) b.leftTouch(pitch.dimensions.leftTouch);
                if (pitch.dimensions.rightTouch != null) b.rightTouch(pitch.dimensions.rightTouch);
                if (pitch.dimensions.topTryLine != null) b.topTryLine(pitch.dimensions.topTryLine);
                if (pitch.dimensions.bottomTryLine != null) b.bottomTryLine(pitch.dimensions.bottomTryLine);
                if (pitch.dimensions.topInGoal != null) b.topInGoal(pitch.dimensions.topInGoal.toAABB());
                if (pitch.dimensions.bottomInGoal != null) b.bottomInGoal(pitch.dimensions.bottomInGoal.toAABB());
            });
        }
        System.out.println("Creating the pitch entity");
        builder.build().createPitch(entity, world);
    }


    private void createBall(int entity, String path){
        BallTemplate ball = JsonLoader.load(path, BallTemplate.class);
        BallConfig.Builder builder = BallConfig.builder();

        if (ball.transform != null){
            // Vectors constructed using template.utils.VectorTemplate
            double x = ball.transform.position.x != null ? ball.transform.position.x : 0.0;
            double y = ball.transform.position.y != null ? ball.transform.position.y : 0.0;
            // Transform position is required.
            builder.transform(new Vector2(x, y), b -> {
                if (ball.transform.orientation != null) b.orientation(ball.transform.orientation);
            });
        }
        if (ball.motion != null){
            builder.motion(b -> {
                if (ball.motion.velocity != null) b.velocity(ball.motion.velocity.toVector2(0.0, 0.0));
                if (ball.motion.rotation != null) b.rotation(ball.motion.rotation);
            });
        }
        if (ball.zAxis != null){
            builder.zAxis(b -> {
                if (ball.zAxis.velocity != null)  b.velocity(ball.zAxis.velocity);
                if (ball.zAxis.position != null) b.position(ball.zAxis.position);
            });
        }
        System.out.println("Creating ball entity");
        builder.build().createBall(entity, world);
    }


    private void createGame(String path, int attack, int defence, int ball, int pitch, int player){
        GameTemplate game = JsonLoader.load(path, GameTemplate.class);
        int entity = world.createEntity();
        GameConfig.Builder builder = GameConfig.builder();

        if (game.state != null){
            builder.state(b ->{
                if (game.state.flags != null) b.flags(game.state.flags);
            });
        }

        // SingletonEntities
        builder.singletons(ball, attack, defence, pitch, player);

        System.out.println("Creating game entity");
        builder.build().createGame(world, entity);
    }


    private void addUpdateSystems(){
        world.addSystem(new InputSystem(world));

        world.addSystem(new GameStateSystem(world, eventBus, commandBus));
        world.addSystem(new KickOffSetupSystem(world, commandBus));
        world.addSystem(new KickOffFormationSystem(world, eventBus));
        world.addSystem(new KickOffInput(world, eventBus, commandBus));

        world.addSystem(new KickBallSystem(world));

        world.addSystem(new MotionSystem(world));
        world.addSystem(new TransformSystem(world));
        // Dampening forces
        world.addSystem(new GravitySystem(world));



        world.addSystem(new FlushEventBusSystem(world, eventBus));

    }


    private void addRenderSystems(Boolean debug){
        if (debug){
        }

        world.addSystem(new TransformRender(world));
        world.addSystem(new PitchRender(world));

    }


    private void populateBusses(){
        UpdatePipeline pipeline = world.getUpdatePipeline();

        for (UpdateSystem system: pipeline.systems){
            system.registerListeners(commandBus);
            system.registerSubscriptions(eventBus);
        }

        // Command chain middleware
        commandBus.addMiddleware(new DebugMiddleware());
    }
}
