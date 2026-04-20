package physics.kinematics;


import util.vectors.Vector2;

public record MotionRequestZ (
    double impulse,                        // Instant velocity "kick".
    double acceleration,                   // Force over time.
    double targetVelocity,                 // The "wanted" velocity to be moving at.
    double angularImpulse,                  // Instant angular velocity "kick".
    double angularAcceleration,             // Angular force over time.
    double targetAngularVelocity            // The "wanted" angular velocity to be moving at.                  // The "wanted" velocity to be moving at.
) implements MotionRequest {
}
