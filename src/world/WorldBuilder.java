package world;

import components.inputs.Inputs;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.middleware.DebugMiddleware;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdatePipeline;
import ecs.pipelines.update.UpdateSystem;
import input.KeyHandler;
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
import util.fileloaders.JsonLoader;
import util.vectors.Vector2;
import world.configurators.*;
import world.templates.entities.*;

import java.util.ArrayList;
import java.util.List;

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

        int attack = createTeam(world_template.attackingTeam);
        int defence = createTeam(world_template.defendingTeam);
        int pitch = createPitch(world_template.pitch);
        int ball = createBall(world_template.ball);
        List<Integer> attackers = createPlayers(world_template.attackPlayers, attack);
        List<Integer> defenders = createPlayers(world_template.defencePlayers, defence);


        if (attackers.isEmpty()) throw new RuntimeException("No attackers created. Cannot continue");
        // Lazy key binding
        int player = attackers.get(0);
        keyH.bind(world.getEntityComponent(player, Inputs.class));

        createGame(world_template.game, attack, defence, ball, pitch, player);
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
    }


    private List<Integer> createPlayers(List<PlayerTemplate> players, int team){
        List<Integer> created_players = new ArrayList<>();
        if (players == null) return created_players;

        for (PlayerTemplate player: players){
            // PlayerConfig.Builder hands values from the PlayerTemplate to Component.Builder in lambda expressions.
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
            if (player.position != null){
                builder.position(player.position);
            }

            builder.member(team);
            builder.inputs();

            System.out.println("Creating a player entity: " + ", team: " + team + ", " + player.position);
            int entity = builder.build().createPlayer(world);
            created_players.add(entity);
        }
        return created_players;
    }


    private int createTeam(TeamTemplate team){
        TeamConfig.Builder builder = TeamConfig.builder();

        if (team.directions != null){
            builder.direction(b -> {
                if (team.directions.forward != null) b.forward(team.directions.forward);
                if (team.directions.backwards != null) b.backwards(team.directions.backwards);
                if (team.directions.left != null) b.left(team.directions.left);
                if (team.directions.right != null) b.right(team.directions.right);
            });
        }

        System.out.println("Creating a team entity");
        return builder.build().createTeam(world);
    }


    private int createPitch(PitchTemplate pitch){
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
        return builder.build().createPitch(world);
    }


    private int createBall(BallTemplate ball){
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
        return builder.build().createBall(world);
    }


    private void createGame(GameTemplate game, int attack, int defence, int ball, int pitch, int player){
        GameConfig.Builder builder = GameConfig.builder();

        if (game.state != null){
            builder.state(b ->{
                if (game.state.flags != null) b.flags(game.state.flags);
            });
        }

        // SingletonEntities
        builder.singletons(ball, attack, defence, pitch, player);

        System.out.println("Creating game entity");
        builder.build().createGame(world);
    }


    private void addUpdateSystems(){
        world.addSystem(new InputSystem(world));

        world.addSystem(new GameStateSystem(world, eventBus, commandBus));
        world.addSystem(new KickOffSetupSystem(world, commandBus));
        world.addSystem(new KickOffFormationSystem(world, eventBus));
        world.addSystem(new KickOffInput(world, eventBus, commandBus));

        world.addSystem(new KickBallSystem(world));

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
