package world.templates.components;

import world.templates.utils.Vector2Template;

/**
 * A POJO template for the components forming the kinematics of a player entity.
 */
public class KinematicsTemplate {
    public MotionTemplate motion;
    public TransformTemplate transform;


    /**
     * A POJO template for the fields of the motion component.
     */
    public static class MotionTemplate {
        Vector2Template velocity;
        double rotation;
    }

    /**
     * A POJO template for the fields of the transform component.
     */
    public static class TransformTemplate {
        Vector2Template position;
        double orientation;
    }

}
