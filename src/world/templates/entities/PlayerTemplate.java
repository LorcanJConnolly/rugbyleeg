package world.templates.entities;

import rugby.positions.Position;
import world.templates.components.MotionTemplate;
import world.templates.components.TransformTemplate;

/**
 * A POJO template for configuring a player entity's components.
 */
public class PlayerTemplate {
    public MotionTemplate motion;
    public TransformTemplate transform;
    public Position position;
}
