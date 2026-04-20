package physics.kinematics;

import util.vectors.Vector2;

public record MotionRequestXY (
    Vector2 impulse,                        // Instant velocity "kick".
    Vector2 acceleration,                   // Force over time.
    Vector2 targetVelocity,                 // The "wanted" velocity to be moving at.
    double angularImpulse,                  // Instant angular velocity "kick".
    double angularAcceleration,             // Angular force over time.
    double targetAngularVelocity            // The "wanted" angular velocity to be moving at.
) {}
