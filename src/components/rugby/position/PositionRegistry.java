package components.rugby.position;

import ecs.Component;
import rugby.positions.Position;

import java.util.HashMap;
import java.util.Map;

/** A team entity component for looking up which entity is in a position */
public class PositionRegistry implements Component {
    private final Map<Position, Integer> data = new HashMap<>();

    public Integer getEntity(Position position){
        return data.get(position);
    }

    public void register(Position position, Integer entity){
        if (getEntity(position) != null) {
            throw new RuntimeException("The position '" + position + "' is already in the registry.");
        }
        data.put(position, entity);
    }
}
