package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;


public class Flee {
    protected MotionState entity;
    protected MotionState fleeTarget;
    protected double maxAcceleration;

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        // Get the direction to the target.
        result.linear.x = fleeTarget.position.x - entity.position.x;
        result.linear.y = fleeTarget.position.x - entity.position.y;

        // The velocity is along this direction, at full speed.
        result.linear = result.linear.normalise().scale(maxAcceleration);
        // Move in the opposite direction to target's position.
        result.linear = result.linear.scale(-1);

        result.angular = 0;

        return result;
    }
}
