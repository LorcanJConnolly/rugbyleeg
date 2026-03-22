package util.shapes;

import util.vectors.Vector2;

/**
 * A class representation of a Axis-Aligned Bounding Box (AABB).
 */
public class AABB {
    public Vector2 origin;
    public double width, height;

    public AABB(Vector2 origin, double width, double height){
        this.origin = origin;
        this.width = width;
        this.height = height;
    }
}