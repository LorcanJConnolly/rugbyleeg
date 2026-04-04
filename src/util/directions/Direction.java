package util.directions;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction opposite() {
        return switch (this) {
            case UP    -> DOWN;
            case DOWN  -> UP;
            case LEFT  -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public Direction rotateClockwise() {
        return switch (this) {
            case UP    -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN  -> LEFT;
            case LEFT  -> UP;
        };
    }

    public Direction rotateCounterClockwise() {
        return switch (this) {
            case UP    -> LEFT;
            case LEFT  -> DOWN;
            case DOWN  -> RIGHT;
            case RIGHT -> UP;
        };
    }
}
