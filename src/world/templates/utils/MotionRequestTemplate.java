package world.templates.utils;

import physics.kinematics.MotionRequest;

public class MotionRequestTemplate {
    Double impulse;
    Vector2Template acceleration;
    Vector2Template velocity;
    Double angular;


    public MotionRequest toMotionRequest(){
        return new MotionRequest(
                impulse,
                acceleration.toVector2(0d, 0d),
                velocity.toVector2(0d, 0d),
                angular
        );
    }
}
