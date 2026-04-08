package world.templates.components;

import state.game.GameStates;

import java.util.EnumSet;
import java.util.List;

public class GameStateTemplate {
    // TODO: Does Jackson read this as expected?
    public EnumSet<GameStates> states;
}
