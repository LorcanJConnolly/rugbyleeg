package ai.behaviors;

import ai.OrientationUtil;
import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;

/**
 * Page 65
 */
public class Align {
    protected MotionState entity;
    protected MotionState alignTarget;
    protected double arrivalRadius, slowDownRadius;
    protected double maxRotation;
    // The time over which to achieve the target speed (likely dt).
    protected double timeToTarget;

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        double rotation = alignTarget.orientation - entity.orientation;

        // Map the result to the (-pi, pi) interval.
        rotation = OrientationUtil.mapToRange(rotation);
        double rotationSize = Math.abs(rotation);

        // Check if we are there, return no steering.
        if (rotationSize < arrivalRadius) return result;

        // If we are outside the slowRadius, then use maximum rotation.
        if (rotationSize > slowDownRadius) return result;

        // Otherwise calculate a scaled rotation.
        double targetRotation = entity.rotation * rotationSize / slowDownRadius;

        // The final target rotation combines speed (already in the variable) and direction.
        targetRotation *= rotation / rotationSize;

        // Acceleration tries to get to the target rotation.
        result.angular = targetRotation - entity.rotation;

        // How much do we need to rotate in this frame to reach the target
        result.angular /= timeToTarget;

        // Clamp rotation based on entity's stats
        if (result.angular > maxRotation){
            result.angular = maxRotation;
        }

        return result;
    }
}