package world.templates.utils;

import util.vectors.Vector2;

public class Vector2Template {
    public Double x, y;

    /**
     * A helper method for converting template values to a Vector2 object, even if values are absent.
     * @param default_x: The value of Vector2.x to default to if absent from template.
     * @param default_y: The value of Vector2.y to default to if absent from template.
     * @return The constructed Vector2 object.
     */
    public Vector2 toVector2(double default_x, double default_y){
        return new Vector2(
            x != null ? x : default_x,
            y != null ? y : default_y
        );
    }
}
