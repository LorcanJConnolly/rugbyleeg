package world.configurators.entities;

import components.pitch.PitchDimensions;
import components.player.kinematics.Motion;
import components.player.kinematics.Transform;
import ecs.Component;
import ecs.World;
import util.vectors.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PitchConfig {
    private final List<? extends Component> components;

    // Entry points
    private PitchConfig(Builder b){
        this.components = List.copyOf(b.components); // immutable
    }

    public void createPitch(World world){
        int id = world.createEntity();
        for (Component component: components){
            world.addComponent(id, component);
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

        public Builder dimensions(Consumer<PitchDimensions.Builder> cfg) {
            PitchDimensions.Builder b = PitchDimensions.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }
    }
}
