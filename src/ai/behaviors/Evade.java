package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;
import util.Vector2;

public class Evade extends Flee {
    protected MotionState evadeTarget;
    protected double maximumPredictionTime;

    @Override
    public SteeringOutput getSteering(){
        // 1. Calculate the target to delegate to seek
        // Work out the distance to taget.
        Vector2 direction = new Vector2(
                evadeTarget.position.x - entity.position.x,
                evadeTarget.position.x - entity.position.y
        );
        double distance = direction.length();

        // Work out our current speed
        double speed = entity.velocity.length();

        // Check if speed gives a reasonable prediction time.
        double prediction;
        if (speed <= distance / maximumPredictionTime){
            prediction = maximumPredictionTime;
        } else {
            prediction = distance / speed;
        }

        // Put the Target together to hand-off to Seek
        MotionState steeringTarget = new MotionState();
        steeringTarget.position = evadeTarget.position.add(evadeTarget.velocity.scale(prediction));
        this.fleeTarget = steeringTarget;

        return super.getSteering();
    }
}
