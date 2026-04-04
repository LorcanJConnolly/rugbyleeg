package input;

import components.player.inputs.Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private Inputs inputs;

    public void bind(Inputs input) {
        this.inputs = input;
    }


    /**
     * Translates a key pressed event to its corresponding in-game input (button press).
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (inputs == null) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> inputs.current.put(GameAction.MOVE_UP, true);
            case KeyEvent.VK_S -> inputs.current.put(GameAction.MOVE_DOWN, true);
            case KeyEvent.VK_A -> inputs.current.put(GameAction.MOVE_LEFT, true);
            case KeyEvent.VK_D -> inputs.current.put(GameAction.MOVE_RIGHT, true);
            case KeyEvent.VK_SPACE -> inputs.current.put(GameAction.ALT_MODIFIER, true);
            case KeyEvent.VK_ENTER -> inputs.current.put(GameAction.ACCEPT, true);
            case KeyEvent.VK_BACK_SPACE -> inputs.current.put(GameAction.DECLINE, true);
            // Number keys
            case KeyEvent.VK_1 -> inputs.current.put(GameAction.OPTION_1, true);
            case KeyEvent.VK_2 -> inputs.current.put(GameAction.OPTION_2, true);
            case KeyEvent.VK_3 -> inputs.current.put(GameAction.OPTION_3, true);
            case KeyEvent.VK_4 -> inputs.current.put(GameAction.OPTION_4, true);
            case KeyEvent.VK_5 -> inputs.current.put(GameAction.OPTION_5, true);
            // Debug button
            case KeyEvent.VK_P -> inputs.current.put(GameAction.DEBUG, true);
        }
    }

    /**
     * Translates a key released event to its corresponding in-game input (button release).
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (inputs == null) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> inputs.current.put(GameAction.MOVE_UP, false);
            case KeyEvent.VK_S -> inputs.current.put(GameAction.MOVE_DOWN, false);
            case KeyEvent.VK_A -> inputs.current.put(GameAction.MOVE_LEFT, false);
            case KeyEvent.VK_D -> inputs.current.put(GameAction.MOVE_RIGHT, false);
            case KeyEvent.VK_SPACE -> inputs.current.put(GameAction.ALT_MODIFIER, false);
            case KeyEvent.VK_ENTER -> inputs.current.put(GameAction.ACCEPT, false);
            case KeyEvent.VK_BACK_SPACE -> inputs.current.put(GameAction.DECLINE, false);
            // Number keys
            case KeyEvent.VK_1 -> inputs.current.put(GameAction.OPTION_1, false);
            case KeyEvent.VK_2 -> inputs.current.put(GameAction.OPTION_2, false);
            case KeyEvent.VK_3 -> inputs.current.put(GameAction.OPTION_3, false);
            case KeyEvent.VK_4 -> inputs.current.put(GameAction.OPTION_4, false);
            case KeyEvent.VK_5 -> inputs.current.put(GameAction.OPTION_5, false);
            // Debug button
            case KeyEvent.VK_P -> inputs.current.put(GameAction.DEBUG, false);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
}
