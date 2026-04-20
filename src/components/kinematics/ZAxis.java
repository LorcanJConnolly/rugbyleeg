package components.kinematics;

import ecs.Component;
import physics.kinematics.MotionRequestZ;

import java.util.ArrayList;
import java.util.List;

/**
 * A component for a 2.5D game for faking altitude and related values.
 */
public class ZAxis implements Component {
    private double velocity, position;
    private List<MotionRequestZ> requests;


    private ZAxis(Builder b){
        this.velocity = b.velocity;
        this.position = b.position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getPosition() {
        return position;
    }

    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public void setPosition(double position){
        this.position = position;
    }

    // Entry point.
    public static Builder builder(){
        return new Builder();
    }


    // Builder pattern.
    public static class Builder {
        private double velocity = 0d;
        private double position = 0d;
        private final List<MotionRequestZ> requests = new ArrayList<>();


        private Builder() {}


        public Builder velocity(double velocity) {
            this.velocity += velocity;
            return this;
        }


        public Builder position(double position) {
            this.position += position;
            return this;
        }

        public Builder requests(List<MotionRequestZ> motion_requests){
            this.requests.addAll(motion_requests);
            return this;
        }


        public ZAxis build(){
            return new ZAxis(this);
        }
    }
}
