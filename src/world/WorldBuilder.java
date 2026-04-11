package world;

import ecs.World;
import stores.MotionStore;
import stores.RugbyPositionStore;
import stores.TransformStore;
import util.fileloaders.JsonLoader;
import util.vectors.Vector2;
import world.configurators.BallConfig;
import world.configurators.PitchConfig;
import world.configurators.PlayerConfig;
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
        // Create entities
        createPlayers(world_template.players);
        createTeam(world_template.attackingTeam);
        createTeam(world_template.defendingTeam);
        createPitch(world_template.pitch);
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


    private void createTeam(TeamTemplate team){
    }

    private void createPitch(PitchTemplate pitch){
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

        builder.build().createPitch(world);
    }

    private void createBall(BallTemplate ball){
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
        builder.build().createBall(world);
    }

    private void createGame(GameTemplate game){

    }
}
