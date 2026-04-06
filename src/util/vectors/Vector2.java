package util.vectors;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Static util methods
    public static Vector2 addTogether(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    public Vector2 subtractFrom(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    // Instance methods
    public void add(Vector2 other){
        x += other.x;
        y += other.y;
    }

    public void subtract(Vector2 other){
        x -= other.x;
        y -= other.y;
    }

    public void scale(double factor){
        x *= factor;
        y *= factor;
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")" ;
    }
}
