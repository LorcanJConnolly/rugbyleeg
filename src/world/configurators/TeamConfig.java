package world.configurators;

import ecs.Component;
import ecs.World;

import java.util.ArrayList;
import java.util.List;

public class TeamConfig {
    private final List<? extends Component> components;

    private TeamConfig(Builder b){
        this.components = List.copyOf(b.components);
    }

    public int createTeam(World world){
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


    // Builder pattern.
    public static class Builder{
        // By default, all components can be missing
        private final List<Component> components = new ArrayList<>();


        private Builder() {}

        public direction()
    }
}
