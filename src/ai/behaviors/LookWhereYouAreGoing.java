package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;

public class LookWhereYouAreGoing extends Align {
    protected MotionState lookWhereYouAreGoingTarget;
    protected  double EPSILON;

    @Override
    public SteeringOutput getSteering(){

        // 1. Calculate the target to delegate to Align.
        // Check for a zero direction, and make no change if so.
        if (entity.velocity.length() < EPSILON && entity.velocity.length() > -EPSILON){
            return new SteeringOutput();
        }

        MotionState lookWhereYouAreGoingTarget = new MotionState();
        lookWhereYouAreGoingTarget.orientation = Math.atan2(-entity.velocity.x, entity.velocity.y);

        // 2. Delegate to Align.
        this.alignTarget = lookWhereYouAreGoingTarget.copyOf();
        return super.getSteering();

    }
}
