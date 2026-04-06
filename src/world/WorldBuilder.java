package world;

import ecs.World;
import util.fileloaders.JsonLoader;
import util.vectors.Vector2;
import world.configurators.entities.PlayerConfig;
import world.templates.entities.PitchTemplate;
import world.templates.entities.PlayerTemplate;
import world.templates.entities.TeamTemplate;
import world.templates.entities.WorldTemplate;

import java.util.List;

public class WorldBuilder {
    private final World world;

    public WorldBuilder(int maxEntities) {
        this.world = new World(maxEntities);
    }

    public World load(String path){
        WorldTemplate world_template = JsonLoader.load(path, WorldTemplate.class);
        createPlayers(world_template.players);
        createTeam(world_template.attackingTeam);
        createTeam(world_template.defendingTeam);
        createPitch(world_template.pitch);

        return world;
    }



    private void createPlayers(List<PlayerTemplate> players){
        for (PlayerTemplate player: players){
            // PlayerConfig.Builder hands values to Component.Builder in lambda expressions.
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
        }

    }


    private void createTeam(TeamTemplate team){
    }

    private void createPitch(PitchTemplate pitch){

    }
}
