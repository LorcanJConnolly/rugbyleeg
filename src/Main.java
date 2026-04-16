import core.Game;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // Closes the window when 'X' is pressed.
        window.setResizable(false);
        window.setTitle("Rugby leeg");

        Game game = new Game();
        window.add(game);

        window.pack();                                              // Causes this window to be sized to fit its subcomponents (our core.systems.GamePanel)

        window.setLocationRelativeTo(null);                         // Window in the centre of the screen.
        window.setVisible(true);

        game.startGameThread();
    }
}