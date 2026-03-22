package components.state.game;

import state.game.GameStates;

import java.util.EnumSet;

/**
 * A component of the "singleton" game state entity, storing enum flags which collectively form a game state.
 */
public class GameState {
    public EnumSet<GameStates> flags = EnumSet.noneOf(GameStates.class);
}
