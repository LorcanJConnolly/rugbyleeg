package components.singletons.pitch;

import ecs.Component;
import util.shapes.AABB;
import util.vectors.Vector2;

public class PitchDimensions implements Component {
    // Entire pitch (including in goal areas)
    public AABB aabb;
    // In play area
    public double leftTouch;
    public double rightTouch;
    public double topTryLine;
    public double bottomTryLine;
    // In goal areas
    public AABB topInGoal;
    public AABB bottomInGoal;
    public double topDeadBall;
    public double bottomDeadBall;

    private PitchDimensions(Builder b){
        this.aabb = b.aabb;
        this.leftTouch = b.leftTouch;
        this.rightTouch = b.rightTouch;
        this.topTryLine = b.topTryLine;
        this.bottomTryLine = b.bottomTryLine;
        this.topInGoal = b.topInGoal;
        this.bottomInGoal = b.bottomInGoal;
        this.topDeadBall = b.topDeadBall;
        this.bottomDeadBall = b.bottomDeadBall;
    }

    // Entry points.
    public static Builder builder(){
        return new Builder();
    }


    // Builder pattern.
    public static class Builder{
        // Default values.
        private AABB aabb               = new AABB(new Vector2(0.0, 0.0), 680.0, 1160.0);
        private double leftTouch        = 0.0;
        private double rightTouch       = 680.0;
        private double topTryLine       = 80.0;
        private double bottomTryLine    = 1080.0;
        private AABB topInGoal          = new AABB(new Vector2(0.0, 0.0), 680.0, 80.0);
        private AABB bottomInGoal       = new AABB(new Vector2(0.0, 1080.0), 680.0, 80.0);;
        private double topDeadBall      = 80;
        private double bottomDeadBall   = 1160;


        private Builder(){}

        public Builder aabb(AABB aabb){ this.aabb = aabb; return this; }

        public Builder leftTouch(double leftTouch) { this.leftTouch = leftTouch; return this; }

        public Builder rightTouch(double rightTouch) { this.rightTouch = rightTouch; return this; }

        public Builder topTryLine(double topTryLine) { this.topTryLine = topTryLine; return this; }

        public Builder bottomTryLine(double bottomTryLine) { this.bottomTryLine = bottomTryLine; return this; }

        public Builder topInGoal(AABB topInGoal) { this.topInGoal = topInGoal; return this; }

        public Builder bottomInGoal(AABB bottomInGoal) { this.bottomInGoal = bottomInGoal; return this; }

        public Builder topDeadBall(double topDeadBall) { this.topDeadBall = topDeadBall; return this; }

        public Builder bottomDeadBall(double bottomDeadBall) { this.bottomDeadBall = bottomDeadBall; return this; }

        public PitchDimensions build(){
            return new PitchDimensions(this);
        }
    }
}
