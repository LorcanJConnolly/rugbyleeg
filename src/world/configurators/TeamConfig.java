package world.configurators;

import components.direction.Directions;
import components.rugby.formation.defence.Formation;
import components.rugby.position.PositionRegistry;
import ecs.Component;
import ecs.World;
import rugby.positions.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TeamConfig {
    private final List<? extends Component> components;

    private TeamConfig(Builder b){
        this.components = List.copyOf(b.components);
    }

    public void createTeam(int entity, World world){
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


        public Builder direction(Consumer<Directions.Builder> cfg){
            Directions.Builder b = Directions.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }


        public Builder formation(){
            this.components.add(new Formation());
            return this;
        }

        public Builder positionRegistry(){
            this.components.add(new PositionRegistry());
            return this;
        }


        public TeamConfig build(){
            return new TeamConfig(this);
        }
    }
}
