package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;
import util.Vector2;

public class Face extends Align {
    protected MotionState faceTarget;
    protected double EPSILON;

    @Override
    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        Vector2 direction = new Vector2(
                faceTarget.position.x - entity.position.x,
                faceTarget.position.x - entity.position.y
        );

        // Check for a zero direction, and make no change if so.
        double distance = direction.length();

        if (distance < EPSILON && distance > -EPSILON) return result;

        // Delegate to align.
        faceTarget.orientation = Math.atan2(-direction.x, direction.y);
;       // Create new target
        this.alignTarget = faceTarget.copyOf();;
        return super.getSteering();
    }
}
