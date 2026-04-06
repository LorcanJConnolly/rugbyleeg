package components.player.kinematics;

import ecs.Component;
import util.vectors.Vector2;

public class Motion implements Component {
    Vector2 velocity;
    double rotation;

    private Motion(Builder b){
        this.velocity = b.velocity;
        this.rotation = b.rotation;
    }

    // Entry point.
    public static Builder builder(){
        return new Builder();
    }

    // Builder pattern.
    public static class Builder{
        // Default values.
        private Vector2     velocity = new Vector2(0, 0);
        private double      rotation = 0.0;

        private Builder(){}

        public Builder velocity(Vector2 velocity){
            this.velocity.add(velocity);
            return this;
        }

        public Builder velocity(double x, double y){
            this.velocity.add(new Vector2(x, y));
            return this;
        }

        public Builder rotation(Vector2 rotation){
            this.velocity.add(velocity);
            return this;
        }
    }
}
