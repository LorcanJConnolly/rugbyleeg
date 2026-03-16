package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;

/**
 * Page 67
 */
public class VelocityMatch {
    protected MotionState entity;
    protected MotionState velocityMatchTarget;
    protected double maxAcceleration;
    // The time over which to achieve the target speed (likely dt).
    protected double timeToTarget;

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        // Acceleration tries to get to the target velocity.
        result.linear.x = velocityMatchTarget.velocity.x - entity.velocity.x;
        result.linear.y = velocityMatchTarget.velocity.y - entity.velocity.y;
        result.linear.x /= timeToTarget;
        result.linear.y /= timeToTarget;

        // Check if acceleration is too fast for the entity.
        if (result.linear.length() > maxAcceleration){
            result.linear = result.linear.normalise().scale(maxAcceleration);
        }

        result.angular = 0;
        return result;
    }
}
