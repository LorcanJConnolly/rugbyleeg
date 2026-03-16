package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;
import util.Vector2;

/**
 * We can think of kinematic wander as behaving as a delegated seek behavior. There is a
 * circle around the character on which the target is constrained. Each time the behavior is run,
 * we move the target around the circle a little, by a random amount. The character then seeks
 * the target.
 */
public class Wander extends Face {
    protected double maxAcceleration;
    // The radius and forward offset of the wander circle.
    protected double wanderOffset, wanderRadius;
    // The maximum rate at which the wander orientation can change
    protected double wanderRate;
    // The orientation of the target relative to its wander circle (where on the circumference is it)
    protected double wanderOrientation;

    @Override
    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        // 1. Calculate the target to delegate to face
        MotionState wanderTarget = new MotionState(); // Zero Kinematic

        // Move the wander target's position on the circumference of the wander circle by a random amount
        wanderOrientation += randomBinomial() * wanderRate;

        //  Calculate the combined target orientation for the entity.
        double targetOrientation = wanderOrientation + entity.orientation;

        // Calculate the center of the wander circle.
        wanderTarget.position = entity.position.add(Vector2.fromAngle(entity.orientation).scale(wanderOffset));

        // Calculate the target location
        wanderTarget.position = wanderTarget.position.add(Vector2.fromAngle(targetOrientation).scale(wanderRadius));

        // Delegate to Face
        this.faceTarget = wanderTarget.copyOf();
        result = super.getSteering();

        // 3. Now set the linear acceleration to be at full acceleration in the direction of the orientation.
        result.linear = Vector2.fromAngle(entity.orientation).scale(maxAcceleration);

        return result;
    }

    private double randomBinomial(){
        // Math.random returns [0,1), by subtracting we crate the range of [-1, 1].
        return Math.random() - Math.random();
    }
}
