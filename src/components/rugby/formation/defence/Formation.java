package components.rugby.formation.defence;

import ecs.Component;
import rugby.formation.defence.Slot;

import java.util.List;

/** A component representing a team entity's formation data. */
public class Formation implements Component {
    public double leftBoundary, rightBoundary;
    public List<Slot> slots;
}
