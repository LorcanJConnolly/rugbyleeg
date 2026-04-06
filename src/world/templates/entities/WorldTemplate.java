package world.templates.entities;

import java.util.List;

/**
 * A POJO template consisting of all of the templates used to configure a world.
 */
public class WorldTemplate {
    public List<PlayerTemplate> players;
    public TeamTemplate attackingTeam, defendingTeam;
    public GameTemplate game;
    public PitchTemplate pitch;
}
