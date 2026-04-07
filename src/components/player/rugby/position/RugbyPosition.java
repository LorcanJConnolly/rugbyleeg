package components.player.rugby.position;

import ecs.Component;
import rugby.positions.Position;

public class RugbyPosition implements Component {
    private Position position;

    private RugbyPosition(Builder b) {
        this.position = b.position;
    }

    public Position getPosition() { return position; }
    public int getNumber() { return position.number; };

    public void updatePosition(Position position){ this.position = position; }

    public static Builder builder(Position position) {
        return new Builder(position);
    }

    public static class Builder {
        private final Position position;

        private Builder(Position position) {
            this.position = position;
        }

        public RugbyPosition build() {
            return new RugbyPosition(this);
        }
    }
}
