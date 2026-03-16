package ai.behaviors;

import ai.Path;
import ecs.kinematics.SteeringOutput;
import util.Vector2;

/**
 * Predictive version page 79
 */
public class FollowPath extends Seek {
    // The distance along the path to generate the target. Can be negative if the character is moving in the reverse direction.
    protected double pathOffset;
    // The current position on the path.
    protected double currentParam;
    // The time in the future to predict the character's position (likely dt).
    protected double predictTime;

    public SteeringOutput getSteering(){
        // 1. Calculate the target to delegate to Face.
        // Find the predicted future location.
        Vector2 futurePosition = new Vector2(
                entity.position.x + entity.velocity.x * predictTime,
                entity.position.y + entity.velocity.y * predictTime
        );

        // Find the current position on the path.
        // NOTE: There is a typo in the book. currentPos = currentParam
        currentParam = Path.getParam(futurePosition, currentParam);

        // Offset it.
        double targetParam = currentParam + pathOffset;

        this.seekTarget.position = Path.getPosition(targetParam);

        return super.getSteering();
    }
}
