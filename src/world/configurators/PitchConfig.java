package world.configurators;

import components.pitch.PitchDimensions;
import ecs.Component;
import ecs.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PitchConfig {
    private final List<? extends Component> components;


    private PitchConfig(Builder b){
        this.components = List.copyOf(b.components); // immutable
    }


    public void createPitch(int entity, World world){
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

        public Builder dimensions(Consumer<PitchDimensions.Builder> cfg) {
            PitchDimensions.Builder b = PitchDimensions.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }

        public PitchConfig build(){
            return new PitchConfig(this);
        }
    }
}
