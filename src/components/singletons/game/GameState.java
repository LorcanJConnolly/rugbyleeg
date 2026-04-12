package components.singletons.game;

import ecs.Component;
import state.game.GameStates;

import java.util.EnumSet;

/**
 * A component of the "singleton" game state entity, storing enum flags which collectively form a game state.
 */
public class GameState implements Component {
    private final EnumSet<GameStates> flags;

    private GameState(Builder b){
        this.flags = b.flags;
    }

    public boolean hasFlag(GameStates state_flag){
        return this.flags.contains(state_flag);
    }

    public void removeFlag(GameStates state_flag){
        this.flags.remove(state_flag);
    }

    public void addFlag(GameStates state_flag){
        this.flags.add(state_flag);
    }

    // Entry point.
    public static Builder builder(){
        return new Builder();
    }

    // Builder pattern.
    public static class Builder{
        // Default values.
        private EnumSet<GameStates> flags = EnumSet.of(GameStates.KICK_OFF);

        private Builder() {}

        public Builder flags(EnumSet<GameStates> flags){
            this.flags = flags;
            return this;
        }

        // Finalise construction - hand builder to private constructor method.
        public GameState build(){
            return new GameState(this);
        }
    }
}
