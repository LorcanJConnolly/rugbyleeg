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
        WorldBuilder worldBuilder = new WorldBuilder(MAX_ENTITIES);
//        world = worldBuilder.load("data/");
    }

    @Override
    public void run() {
        return;
    }
}
