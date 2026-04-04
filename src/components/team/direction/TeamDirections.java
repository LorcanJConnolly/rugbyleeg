package components.team.direction;

import ecs.Component;
import util.directions.Direction;

public class TeamDirections implements Component {
    public Direction forward, backwards, inside, outside;
}
