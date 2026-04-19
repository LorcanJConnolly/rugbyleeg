package core;

import ecs.World;
import input.KeyHandler;
import world.WorldBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the game and implements the game loop.
 * Extends JPanel to implement the game's screen.
 */
public class Game extends JPanel implements Runnable{
    // Screen settings.
    final int originalTileSize = 16;
    final int tileScale = 3;
    public final int tileSize = originalTileSize * tileScale;
    final int maxScreenCol = 32;
    final int maxScreenRow = 24;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    Thread gameThread;

    KeyHandler keyH = new KeyHandler();
    World world;

    public Game(){
        // JPanel game screen configuration.
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   // Drawing from this ecs.component will be done in an offscreen painting buffer -- improves game's rendering performance.
        this.addKeyListener(keyH);
        this.setFocusable(true);        // core.systems.GamePanel can be "focused" to receive key input.

        int MAX_ENTITIES = 64;
        WorldBuilder worldBuilder = new WorldBuilder(keyH, MAX_ENTITIES);
        world = worldBuilder.load("data/games/basic_game.json");
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Thread objects have their run() method called during Thread.start() calls.
        double drawInterval = 1000000000.0/FPS; // 1 second / FPS in units of nanoseconds.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // THE GAME LOOP
        // Delta Game Loop method.
        while(gameThread != null) {
//            System.out.println("start of frame.");
            currentTime = System.nanoTime();   // (1e-9 seconds).
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update(1.0 / FPS);
                repaint(); // Confusing, but calls paintComponent()
                delta--;
            }

        }
    }


    public void update(double dt) {
        world.update(dt);
    }


    /** Java built in method - must call super.
    * @param g the <code>Graphics</code> object to protect, acts as your paintbrush.
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;  // Cast our paintbrush to 2D Graphics.
        world.render(g2, 1.0/FPS);
        g2.dispose();   // Good practice - Dispose of graphics context and release any system resources using it.

    }

}
