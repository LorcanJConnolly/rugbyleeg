package systems.inputs;

import components.player.inputs.Inputs;
import ecs.World;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import input.GameAction;

/**
 * A system for determining the states of button presses.
 */
public class InputSystem implements UpdateSystem {
    private final Query query;

    public InputSystem(World world){
        this.query = world.query(Inputs.class);
    }

    @Override
    public void update(double dt) {
        query.forEach((entity, input) -> {
            process((Inputs) input);
        });
    }

    public void process(Inputs input) {
        for (GameAction button: GameAction.values()){
            boolean currentButtonState = input.current.get(button);
            boolean previousButtonState = input.previous.get(button);

            // Is the button is pressed (went down)
            input.pressed.put(button, currentButtonState && !previousButtonState);
            // Is the button is released (went up)
            input.released.put(button, !currentButtonState && previousButtonState);
            // Is the button held (is down)
            input.held.put(button, currentButtonState);

            // Advance current -> previous for next frame.
            input.previous.put(button, currentButtonState);
        }
    }
}
