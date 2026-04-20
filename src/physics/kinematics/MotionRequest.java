package physics.kinematics;

import util.vectors.Vector2;

public record MotionRequest(double impulse, Vector2 acceleration, Vector2 targetVelocity) {}
