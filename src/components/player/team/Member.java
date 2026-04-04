package components.player.team;

import ecs.Component;

/**
 * Which team entity a (player, slot, etc) entity belongs to.
 */
public class Member implements Component {
    public int team;
}
