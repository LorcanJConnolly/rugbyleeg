package input;

import components.inputs.Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private Inputs inputs;

    public void bind(Inputs input) {
        this.inputs = input;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        case KeyEvent.VK_W -> inputs.current.put(GameAction.MOVE_UP, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
