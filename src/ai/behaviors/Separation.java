package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;
import util.Vector2;

import java.util.List;

public class Separation {
    protected MotionState entity;
    protected double maxAcceleration;
    protected List<MotionState> separationTargets;
    // Threshold to take action.
    protected double threshold;
    // Decay coefficient of the inverse square law.
    protected double decayCoefficient;

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        for (MotionState separationTarget: separationTargets){
            Vector2 direction = new Vector2(
                    separationTarget.position.x - entity.position.x,
                    separationTarget.position.x - entity.position.y
            );
            double distance = direction.length();

            if (distance < threshold){
                // Calculate the strength of repulsion (here using the inverse square law).
                double strength = Math.min(decayCoefficient / (distance * distance), maxAcceleration);

                // Add the acceleration
                direction = direction.normalise();
                result.linear = result.linear.add(direction.scale(strength));
            }
        }

        return result;
    }
}
