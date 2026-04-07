package world.templates.utils;

import util.shapes.AABB;
import util.vectors.Vector2;

public class AABBTemplate {
    public Vector2Template origin;
    public double width, height;

    public AABB toAABB(){
        return new AABB(new Vector2(origin.x, origin.y), width, height);
    }
}
