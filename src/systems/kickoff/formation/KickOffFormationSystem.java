package systems.kickoff.formation;

import components.player.kinematics.Transform;
import components.singletons.pitch.PitchDimensions;
import components.player.rugby.position.RugbyPosition;
import components.player.team.Member;
import components.singletons.game.AttackingTeam;
import components.singletons.game.DefendingTeam;
import components.singletons.game.GameState;
import components.team.direction.TeamDirections;
import ecs.World;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import util.pitch.PitchUtils;

public class KickOffFormationSystem implements UpdateSystem {
    private final Query query;
    private final PitchDimensions pitchDimensions;
    private final AttackingTeam attack;
    private final DefendingTeam defence;
    private final TeamDirections attackDirections, defenceDirections;

    public KickOffFormationSystem(World world){
        this.pitchDimensions = world.getSingleton(PitchDimensions.class);
        this.attack = world.getSingleton(AttackingTeam.class);
        this.defence = world.getSingleton(DefendingTeam.class);
        this.attackDirections = world.getEntityComponent(this.attack.entity, TeamDirections.class);
        this.defenceDirections = world.getEntityComponent(this.defence.entity, TeamDirections.class);
        this.query = world.query(Transform.class, RugbyPosition.class, Member.class);
    }

    @Override
    public void update(double dt) {
        // register command listening

        query.forEach((int entity, Transform transform, RugbyPosition position, Member member) -> {
            process(transform, position, member);
        });
    }

    public void process(Transform transform, RugbyPosition position, Member member) {
        if (member.team == attack.entity) {
            switch (position.getPosition()) {
                // Increments of 8, starting at 0.04 to 0.96
                case WING_5:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.06, 0.45
                    );
                    break;
                case CENTRE_4:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.14, 0.45)
                    ;
                    break;
                case HALFBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.22, 0.45
                    );
                    break;
                case SECOND_ROW_12:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.3, 0.45
                    );
                    break;
                case PROP_8:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.38, 0.45
                    );
                    break;
                case HOOKER:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.46, 0.45
                    );
                    break;
                case PROP_10:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.52, 0.45
                    );
                    break;
                case LOCK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.6, 0.45
                    );
                    break;
                case SECOND_ROW_11:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.68, 0.45
                    );
                    break;
                case FIVE_EIGHTH:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.76, 0.45
                    );
                    break;
                case CENTRE_3:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.84, 0.45
                    );
                    break;
                case WING_2:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.92, 0.45
                    );
                    break;
            }
        } else if (member.team == defence.entity) {
            // closest to furthest, left to right
            switch (position.getPosition()) {
                case CENTRE_3:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.03, 0.38
                    );
                    break;
                case LOCK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.5, 0.35
                    );
                    break;
                case CENTRE_4:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.97, 0.38
                    );
                    break;
                case SECOND_ROW_11:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.03, 0.29
                    );
                    break;
                case HOOKER:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.26, 0.5
                    );

                case SECOND_ROW_12:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.97, 0.29
                    );
                    break;
                case FIVE_EIGHTH:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.18, 0.07
                    );
                    break;
                case HALFBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.72, 0.07
                    );
                    break;
                case WING_2:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, defenceDirections.forward,
                            0.02, 0
                    );
                    break;
                case WING_5:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, defenceDirections.forward,
                            0.98, 0
                    );
                    break;

                case FULLBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.5, 0
                    );
                    break;
                case PROP_8:
                    transform.position = PitchUtils.relativeToPitch(
                            pitchDimensions, attackDirections.forward,
                            0.3, 0.02
                    );
                    break;
                case PROP_10:
                    transform.position = PitchUtils.relativeToPitch(
                            pitchDimensions, attackDirections.forward,
                            0.7, 0.02
                    );
                    break;
            }
        }

    }
}
