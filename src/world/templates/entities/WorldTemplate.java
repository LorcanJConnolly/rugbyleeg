package world.templates.entities;

import java.util.List;

/**
 * A POJO template consisting of all of the templates used to configure a world.
 */
public class WorldTemplate {
    public List<PlayerTemplate> attackPlayers;
    public List<PlayerTemplate> defencePlayers;
    public TeamTemplate attackingTeam, defendingTeam;
    public GameTemplate game;
    public PitchTemplate pitch;
    public BallTemplate ball;
}
