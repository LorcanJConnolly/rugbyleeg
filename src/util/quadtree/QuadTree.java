package util.quadtree;

import util.fileloaders.JsonLoader;
import util.shapes.AABB;
import util.vectors.Vector2;
import world.templates.entities.PlayerTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuadTree {
    private final AABB boundary;            // The boundary of the quadtree.
    private final int capacity;             // The capacity of the quadtree.
    private final List<Vector2> points;     // The points in the quadtree.
    // Pointers to the child subdivisions of the quadtree.
    private QuadTree northWest, northEast, southWest, southEast;
    private boolean divided;                // Has this quadtree been divided.


    public QuadTree(AABB boundary, int capacity){
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
        this.northWest = null;
        this.northEast = null;
        this.southWest = null;
        this.southEast = null;
        divided = false;
    }


    /** inserts a point into this quadtree. Returns true if the insertion was successful. */
    public boolean insert(Vector2 point){
        if (!boundary.contains(point)){
            return false;
        }
        if (points.size() < capacity) {
            points.add(point);
            return true;
        } else {
            if (!divided) subdivide();
            // NOTE: bias of order.
            if (this.northWest.insert(point)) return true;
            else if (this.northEast.insert(point)) return true;
            else if (this.southWest.insert(point)) return true;
            else if (this.southEast.insert(point)) return true;
            else return false;
        }
    }


    /** Takes a quadtree and divides it into 4  quadtree objects (NW, NE, SW, SE). */
    public void subdivide(){
        this.northWest = new QuadTree(
                new AABB(
                        boundary.origin,
                        boundary.width/2,
                        boundary.height/2
                ),
                capacity
        );
        this.northEast = new QuadTree(
                new AABB(
                        new Vector2(boundary.origin.x + boundary.width/2, boundary.origin.y),
                        boundary.width/2,
                        boundary.height/2
                ),
                capacity
        );
        this.southWest = new QuadTree(
                new AABB(
                        new Vector2(boundary.origin.x, boundary.origin.y + boundary.height/2),
                        boundary.width/2,
                        boundary.height/2
                ),
                capacity
        );
        this.southEast = new QuadTree(
                new AABB(
                        new Vector2(boundary.origin.x + boundary.width/2, boundary.origin.y + boundary.height/2),
                        boundary.width/2,
                        boundary.height/2
                ),
                capacity
        );

        divided = true;
    }


    /** Returns all the points in a given boundary */
    public List<Vector2> query(AABB query_boundary, List<Vector2> found){
        if (found == null){
            found = new ArrayList<>();
        }
        if (!boundary.intersects(query_boundary)) {
            return found;
        } else {
            for (Vector2 point : points){
                if (query_boundary.contains(point)){
                    found.add(point);
                }
            }
        }
        if (divided){
            found.addAll(this.northWest.query(query_boundary, found));
            found.addAll(this.northEast.query(query_boundary, found));
            found.addAll(this.southWest.query(query_boundary, found));
            found.addAll(this.southEast.query(query_boundary, found));
        }
        return found;
    }


    /** Debug method for drawing the quadtree */
    public void show(Graphics2D g2){
        g2.setColor(Color.yellow);
        g2.draw(
            new Rectangle2D.Double(
                boundary.origin.x,
                boundary.origin.y,
                boundary.width,
                boundary.height
            )
        );
        if (divided){
            northWest.show(g2);
            northEast.show(g2);
            southWest.show(g2);
            southEast.show(g2);
        }

        for (Vector2 point : points){
            g2.draw(new Ellipse2D.Double(
                    (point.x),
                    (point.y),
                    1,
                    1
            ));
        }
    }


    // Basic test
    public static void main(String[] args) {
        double width = 600d;
        double height = 600d;
        AABB boundary = new AABB(new Vector2(0, 0), width, height);
        QuadTree qt = new QuadTree(boundary, 2);

        Random r = new Random();
        for (int i=0; i < 20; i++ ){
            double x = r.nextDouble() * width;
            double y = r.nextDouble() * height;

            qt.insert(new Vector2(x, y));
        }
        // Draw
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                // Draw background
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                qt.show(g2);
            }
        };

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize((int) width, (int) height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBackground(Color.BLACK);
    }
}
