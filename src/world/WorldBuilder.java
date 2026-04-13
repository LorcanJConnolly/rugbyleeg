package world;

import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;
import stores.MotionStore;
import stores.RugbyPositionStore;
import stores.TransformStore;
import systems.kickoff.setup.KickOffSetupSystem;
import util.fileloaders.JsonLoader;
import util.vectors.Vector2;
import world.configurators.*;
import world.templates.entities.*;

import java.util.List;

public class WorldBuilder {
    private final int maxEntities;
    private final World world;

    public WorldBuilder(int maxEntities) {
        this.maxEntities = maxEntities;
        this.world = new World(this.maxEntities);
    }

    public World load(String path){

        WorldTemplate world_template = JsonLoader.load(path, WorldTemplate.class);

        // Create stores
        createStores();

        // register systems to world and their associated command handlers and event subscriptions.
        addSystems();

        // Event subscribers
        EventBus eventBus = new EventBus();

        // Command handlers

        // Create entities
        createPlayers(world_template.players);
        int attack = createTeam(world_template.attackingTeam);
        int defence = createTeam(world_template.defendingTeam);
        int pitch = createPitch(world_template.pitch);
        int ball = createBall(world_template.ball);

        createGame(world_template.game, attack, defence, ball, pitch);
        // TODO: add controls to a player entity.

        return world;
    }

    public void createStores(){
        world.registerStore(new MotionStore(this.maxEntities));
        world.registerStore(new TransformStore(this.maxEntities));
        world.registerStore(new RugbyPositionStore(this.maxEntities));
    }


    private void createPlayers(List<PlayerTemplate> players){
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
            if (player.position != null && player.position.position != null){
                builder.position(player.position.position);
            }

            builder.build().createPlayer(world);
        }
    }


    private int createTeam(TeamTemplate team){
        TeamConfig.Builder builder = TeamConfig.builder();

        if (team.direction != null){
            builder.direction(b -> {
                if (team.direction.forward != null) b.forward(team.direction.forward);
                if (team.direction.backwards != null) b.backwards(team.direction.backwards);
                if (team.direction.inside != null) b.left(team.direction.inside);
                if (team.direction.outside != null) b.right(team.direction.outside);
            });
        }

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
        return builder.build().createBall(world);
    }


    private void createGame(GameTemplate game, int attack, int defence, int ball, int pitch){
        GameConfig.Builder builder = GameConfig.builder();

        if (game.state != null){
            builder.state(b ->{
                if (game.state.flags != null) b.flags(game.state.flags);
            });
        }
        // SingletonEntities
        builder.singletons(ball, attack, defence, pitch, b -> {});

        builder.build().createGame(world);
    }


    private void addSystems(){
        EventBus eventBus = world.getEventBus();
        CommandBus commandBus = world.getCommandBus();

        KickOffSetupSystem kickOffSetUp = new KickOffSetupSystem(world, commandBus);
        kickOffSetUp.registerSubscriptions(eventBus);

        world.addSystem(kickOffSetUp);

    }


}
