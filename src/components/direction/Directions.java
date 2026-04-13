package components.direction;

import ecs.Component;
import util.directions.Direction;

public class Directions implements Component {
    public Direction forward, backwards, left, right;


    private Directions(Builder b){
        this.forward = b.forward;
        this.backwards = b.backwards;
        this.left = b.left;
        this.right = b.right;
    }


    // Entry point.
    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{
        private Direction forward = Direction.UP;
        private Direction backwards = Direction.UP;
        private Direction left = Direction.LEFT;
        private Direction right = Direction.RIGHT;


        private Builder() {}


        public Builder forward(Direction direction){
            this.forward = direction;
            return this;
        }


        public Builder backwards(Direction direction){
            this.backwards = direction;
            return this;
        }


        public Builder left(Direction direction){
            this.left = direction;
            return this;
        }


        public Builder right(Direction direction){
            this.right = direction;
            return this;
        }


        public Directions build(){
            return new Directions(this);
        }
    }
}
