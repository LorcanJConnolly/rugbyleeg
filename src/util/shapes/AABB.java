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


    public boolean contains(Vector2 point){
        return (
                (point.x >= origin.x && point.x <= origin.x + width) &&
                (point.y >= origin.y && point.y <= origin.y + height)
            );
    }


    public boolean intersects(AABB other){
        return !(
            (other.origin.x > this.origin.x + this.width) ||
            (other.origin.x + other.width < this.origin.x) ||
            (other.origin.y > this.origin.y +this.height) ||
            (other.origin.y + other.height < this.origin.y)
        );
    }
}