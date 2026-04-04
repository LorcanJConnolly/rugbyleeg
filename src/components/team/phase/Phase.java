package components.team.phase;

import ecs.Component;
import rugby.team.Team;

/**
 * A component for describing a team entity's current "game state" or "game phase" (attacking or defending).
 */
public class Phase implements Component {
    public Team phase; // ATTACKING or DEFENDING

    public boolean isAttacking(){
        return phase == Team.ATTACK;
    }

    public boolean isDefending(){
        return phase == Team.DEFENCE;
    }
}
