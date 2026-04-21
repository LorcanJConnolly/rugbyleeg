package util.quadtree;

import util.shapes.AABB;
import util.vectors.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            if (this.northEast.insert(point)) return true;
            if (this.southWest.insert(point)) return true;
            return this.southEast.insert(point);
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
                        new Vector2(boundary.origin.x, boundary.origin.y + boundary.width/2),
                        boundary.width/2,
                        boundary.height/2
                ),
                capacity
        );
        this.southEast = new QuadTree(
                new AABB(
                        new Vector2(boundary.origin.x + boundary.width/2, boundary.origin.y + boundary.width/2),
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

    }
}
