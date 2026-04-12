package world.configurators;

import components.singletons.game.AttackingTeam;
import components.singletons.game.DefendingTeam;
import components.singletons.game.GameState;
import ecs.Component;
import ecs.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameConfig {
    private final List<? extends Component> components;



    private GameConfig(Builder b, int attackingTeam, int defendingTeam) {
        this.components = List.copyOf(b.components);
    }


    public void createGame(World world){
        int id = world.createEntity();
        for (Component component: components){
            world.addComponent(id, component);
        }
    }

    public static class Builder{

        private final List<Component> components = new ArrayList<>();

        private Builder() {}

        public Builder state(Consumer<GameState.Builder> cfg) {
            GameState.Builder b = GameState.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }

        // Determined after creation in WorldBuilder.
        public Builder attackingTeam(Consumer<AttackingTeam.Builder> cfg) {
            AttackingTeam.Builder b = AttackingTeam.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }

        public Builder defendingTeam(Consumer<DefendingTeam.Builder> cfg) {
            DefendingTeam.Builder b = DefendingTeam.builder();
            cfg.accept(b);
            this.components.add(b.build());
            return this;
        }
    }
}
