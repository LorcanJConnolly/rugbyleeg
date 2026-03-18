package components.inputs;

import java.util.HashMap;
import java.util.Map;

import ecs.Component;
import input.GameAction;

/**
 * A component on which button in the game's controls (note: NOT button of the input source) was pressed by an entity.
 */
public class Inputs implements Component {
    // Current Frame Buttons pressed as described by KeyHandler.
    public final Map<GameAction, Boolean> current;
    // Previous Frame Buttons pressed as described by KeyHandler.
    public final Map<GameAction, Boolean> previous;

    // State of how the buttons have been interacted with.
    // Button went down this frame
    public final Map<GameAction, Boolean> pressed;
    // Button is currently down this frame
    public final Map<GameAction, Boolean> held;
    // Button went up this frame
    public final Map<GameAction, Boolean> released;

    public Inputs() {
        this.current = new HashMap<>();
        this.previous= new HashMap<>();
        this.pressed = new HashMap<>();
        this.held = new HashMap<>();
        this.released = new HashMap<>();

        for (GameAction button : GameAction.values()) {
            current.put(button, false);
            previous.put(button, false);
            pressed.put(button, false);
            held.put(button, false);
            released.put(button, false);
        }
    }
}