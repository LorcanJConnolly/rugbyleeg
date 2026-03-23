package util.vectors;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")" ;
    }
}
