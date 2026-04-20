package components.kinematics;

import ecs.Component;
import physics.kinematics.MotionRequestXY;
import util.vectors.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Motion implements Component {
    public Vector2 velocity;
    public double rotation;
    private final List<MotionRequestXY> requests;

    private Motion(Builder b){
        this.velocity = b.velocity;
        this.rotation = b.rotation;
        this.requests = b.requests;
    }

    public void addRequest(MotionRequestXY request){
        this.requests.add(request);
    }

    public List<MotionRequestXY> getRequests(){
        return this.requests;
    }

    public void clearRequests(){
        this.requests.clear();
    }

    // Entry point.
    public static Builder builder(){
        return new Builder();
    }

    // Builder pattern.
    public static class Builder{
        // Default values.
        private final Vector2               velocity = new Vector2(0, 0);
        private double                      rotation = 0.0;
        private final List<MotionRequestXY> requests = new ArrayList<>();

        private Builder(){}

        public Builder velocity(Vector2 velocity){
            this.velocity.add(velocity);
            return this;
        }

        public Builder velocity(double x, double y){
            this.velocity.add(new Vector2(x, y));
            return this;
        }

        public Builder rotation(double rotation){
            this.rotation = rotation;
            return this;
        }

        public Builder requests(List<MotionRequestXY> motion_requests){
            this.requests.addAll(motion_requests);
            return this;
        }

        // Finalise construction - hand builder to private constructor method.
        public Motion build(){
            return new Motion(this);
        }
    }
}
