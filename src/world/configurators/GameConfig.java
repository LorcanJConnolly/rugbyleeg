package world.configurators;

import components.game.GameState;
import components.game.SingletonEntities;
import ecs.Component;
import ecs.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameConfig {
    private final List<? extends Component> components;


    private GameConfig(Builder b) {
        this.components = List.copyOf(b.components);
    }


    public void createGame(World world, int entity){
        for (Component component: components){
            world.addComponent(entity, component);
        }
    }

    // Entry points.
    public static Builder builder(){
        return new Builder();
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
        public Builder singletons(int ball, int attack, int defence, int pitch, int player) {
            this.components.add(SingletonEntities.builder(ball, attack, defence, pitch, player).build());
            return this;
        }


        public GameConfig build(){
            return new GameConfig(this);
        }
    }
}
