package world.configurators;

import components.kinematics.Motion;
import components.kinematics.Transform;
import components.rugby.position.RugbyPosition;
import ecs.Component;
import ecs.World;
import rugby.positions.Position;
import util.vectors.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BallConfig {
    private final List<? extends Component> components;

    // Entry points
    private BallConfig(Builder b){
        this.components = List.copyOf(b.components);
    }

    public int createBall(World world){
        int id = world.createEntity();
        for (Component component: components){
            world.addComponent(id, component);
        }
        return id;
    }

    // Entry points.
    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private final List<Component> components = new ArrayList<>();

        private Builder() {}

        public Builder transform(Vector2 position, Consumer<Transform.Builder> cfg) {
            Transform.Builder b = Transform.builder(position);
            cfg.accept(b);
            this.components.add(b.build());
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

        public BallConfig build(){
            return new BallConfig(this);
        }
    }
}
