package world.templates.utils;

import physics.kinematics.MotionRequest;

public class MotionRequestTemplate {
    Double impulse;
    Vector2Template acceleration;
    Vector2Template targetVelocity;


    public MotionRequest toMotionRequest(){
        return new MotionRequest(
                impulse,
                acceleration.toVector2(0d, 0d),
                targetVelocity.toVector2(0d, 0d)
        );
    }
}
