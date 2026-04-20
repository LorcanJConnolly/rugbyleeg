package world.configurators;

import components.inputs.Inputs;
import components.kinematics.Motion;
import components.kinematics.Transform;
import components.rugby.position.RugbyPosition;
import components.rugby.team.Member;
import ecs.Component;
import ecs.World;
import physics.kinematics.MotionRequest;
import rugby.positions.Position;
import util.vectors.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class which provides a builder pattern for building a player entity and its components using templates in the
 * world builder.
 */
public class PlayerConfig {
    private final List<? extends Component> components;


    private PlayerConfig(Builder b){
        this.components = List.copyOf(b.components);
    }

    public void createPlayer(World world, int entity){
        for (Component component: components){
            world.addComponent(entity, component);
        }
    }

    // Entry points.
    public static Builder builder(){
        return new Builder();
    }


    // Builder pattern.
    public static class Builder{
        // By default, all components can be missing
        private final List<Component> components = new ArrayList<>();

        private Builder() {}

        public Builder transform(Vector2 position, Consumer<Transform.Builder> cfg) {
            // Create required fields.
            Transform.Builder b = Transform.builder(position);
            // Override default values for any existing optional fields.
            // This is called in lambda construction in the WorldBuilder.
            cfg.accept(b);
            // Build component and add to the list of components.
            this.components.add(b.build());
            return this;
        }

        // All player entities have a input component.
        public Builder inputs(){
            this.components.add(new Inputs());
            return this;
        }


        public Builder motion(Consumer<Motion.Builder> cfg) {
            Motion.Builder b = Motion.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }

        public Builder position(Position position) {
            RugbyPosition.Builder b = RugbyPosition.builder(position);
            this.components.add(b.build());
            return this;
        }

        public Builder member(int member) {
            this.components.add(new Member(member));
            return this;
        }

        public PlayerConfig build(){
            return new PlayerConfig(this);
        }
    }
}
