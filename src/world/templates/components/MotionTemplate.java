package world.templates.components;

import physics.kinematics.MotionRequest;
import world.templates.utils.MotionRequestTemplate;
import world.templates.utils.Vector2Template;

import java.util.List;

/**
 * A POJO template for the fields of the motion component.
 */
public class MotionTemplate {
    public Vector2Template velocity;
    public Double rotation;
    public List<MotionRequestTemplate> requests;
}