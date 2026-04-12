package components.team.direction;

import ecs.Component;
import util.directions.Direction;

public class Directions implements Component {
    public Direction forward, backwards, inside, outside;


    private Directions(Builder b){
        this.forward = b.forward;
        this.backwards = b.backwards;
        this.inside = b.inside;
        this.outside = b.outside;
    }


    // Entry point.
    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{
        private Direction forward = Direction.UP;
        private Direction backwards = Direction.UP;
        private Direction inside = Direction.LEFT;
        private Direction outside = Direction.RIGHT;


        private Builder() {}


        public Builder forward(Direction direction){
            this.forward = direction;
            return this;
        }


        public Builder backwards(Direction direction){
            this.backwards = direction;
            return this;
        }


        public Builder inside(Direction direction){
            this.inside = direction;
            return this;
        }


        public Builder outside(Direction direction){
            this.outside = direction;
            return this;
        }


        public Directions build(){
            return new Directions(this);
        }
    }
}
