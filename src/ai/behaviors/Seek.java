package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;

public class Seek {
    protected MotionState entity;
    protected MotionState seekTarget;
    protected double maxAcceleration;

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        // Get the direction to the target.
        result.linear.x = seekTarget.position.x - entity.position.x;
        result.linear.y = seekTarget.position.x - entity.position.y;

        // The velocity is along this direction, at full speed.
        result.linear = result.linear.normalise().scale(maxAcceleration);

        result.angular = 0;

        return result;
    }
}

