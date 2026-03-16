package ai.behaviors;

import ecs.kinematics.SteeringOutput;
import ecs.kinematics.MotionState;
import util.Vector2;


/**
 * Page 62
 */
public class Arrive {
    protected MotionState entity;
    protected MotionState arriveTarget;
    protected double arrivalRadius, slowDownRadius;
    protected double maxSpeed, maxAcceleration;
    // The time over which to achieve the target speed (likely dt).
    protected double timeToTarget;

    public Arrive(MotionState entity, MotionState target,
                  double arrivalRadius, double slowDownRadius,
                  double maxSpeed, double maxAcceleration,
                  double timeToTarget) {

        this.entity = entity;
        this.arriveTarget = target;
        this.arrivalRadius = arrivalRadius;
        this.slowDownRadius = slowDownRadius;
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.timeToTarget = timeToTarget;
    }

    public SteeringOutput getSteering(){
        SteeringOutput result = new SteeringOutput();

        Vector2 direction = new Vector2(
                arriveTarget.position.x - entity.position.x,
                arriveTarget.position.y - entity.position.y
        );
        double distance = direction.length();

        // Check if we are there, return no steering.
        if (distance < arrivalRadius) {
            return result;
        }

        // If we are outside the slowRadius, then move at max speed.
        double targetSpeed;
        if (distance > slowDownRadius){
            targetSpeed = maxSpeed;
        } else {
            // Otherwise calculate a scaled speed.
            targetSpeed = maxSpeed * distance / slowDownRadius;
        }

        // The target velocity combines speed and direction.
        Vector2 targetVelocity = direction;
        targetVelocity = targetVelocity.normalise();
        targetVelocity = targetVelocity.scale(targetSpeed);

        // Acceleration tries to get to the target velocity.
        Vector2 acceleration = new Vector2(
                targetVelocity.x - entity.velocity.x,
                targetVelocity.y - entity.velocity.y
        );
        // How much do we need to accelerate in this frame to reach the target
        acceleration = acceleration.scale(1/timeToTarget);
        // Clamp acceleration based on entity's stats
        if (acceleration.length() > maxAcceleration){
            acceleration = acceleration.normalise().scale(maxAcceleration);
        }
        result.linear = acceleration;
        result.angular = 0;

        return result;
    }
}
