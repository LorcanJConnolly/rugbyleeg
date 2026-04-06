package components.player.kinematics;

import ecs.Component;
import util.vectors.Vector2;

public class Transform implements Component {
    public Vector2 position;
    public double orientation;

    private Transform(Builder b){
        this.position = b.position;
        this.orientation = b.orientation;
    }

    // Entry points.
    public static Builder builder(Vector2 position){
        return new Builder(position);
    }

    public static Builder builder(double x, double y){
        return new Builder(x, y);
    }

    // Builder pattern.
    public static class Builder{
        // Required
        private Vector2     position = new Vector2(0, 0);
        // Default values.
        private double      orientation = 0.0;

        private Builder(Vector2 position){
            this.position = position;
        }

        private Builder(double x, double y){
            this.position = new Vector2(x, y);
        }

        public Builder position(Vector2 velocity){
            this.position.add(velocity);
            return this;
        }

        public Builder position(double x, double y){
            this.position.add(new Vector2(x, y));
            return this;
        }

        public Builder orientation(double orientation){
            this.orientation = orientation;
            return this;
        }
    }
}
