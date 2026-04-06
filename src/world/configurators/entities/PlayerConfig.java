package world.configurators.entities;

import components.player.kinematics.Motion;
import components.player.kinematics.Transform;
import ecs.World;
import util.vectors.Vector2;

import java.util.function.Consumer;

/**
 * A class which provides a builder pattern for building a player entity and its components using templates in the
 * world builder.
 */
public class PlayerConfig {
    public Motion motion;
    public Transform transform;

    // Entry points
    private PlayerConfig(Builder b){
        this.motion = b.motion;
        this.transform = b.transform;
    }

    public void createPlayer(World world, int Id){
        // Add entity to the world
        // Add each component to the entity.
    }

    // Entry points.
    public static Builder builder(){
        return new Builder();
    }


    // Builder pattern.
    public static class Builder{
        // By default, all components can be missing
        private Transform transform         = null;
        private Motion motion               = null;

        private Builder() {}

        public Builder transform(Vector2 position, Consumer<Transform.Builder> cfg) {
            // Create required fields.
            Transform.Builder b = Transform.builder(position);
            // Override default values for any existing optional fields.
            // This is called in lambda construction in the WorldBuilder.
            cfg.accept(b);
            // Build component.
            this.transform = b.build();
            return this;
        }

        public Builder motion(Consumer<Motion.Builder> cfg) {
            Motion.Builder b = Motion.builder();
            cfg.accept(b);
            this.motion = b.build();
            return this;
        }
    }
}
