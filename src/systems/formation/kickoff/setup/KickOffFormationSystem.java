package systems.formation.kickoff.setup;

import components.player.kinematics.Transform;
import components.pitch.PitchDimensions;
import components.player.rugby.position.RugbyPosition;
import components.player.team.Member;
import components.singletons.game.GameState;
import components.team.direction.TeamDirections;
import components.team.phase.Phase;
import ecs.World;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import state.game.GameStates;
import util.pitch.PitchUtils;

public class KickOffFormationSystem implements UpdateSystem {
    private final Query gameStateQuery;
    private final Query pitchQuery;
    private final Query teamsQuery;
    private final Query query;

    private GameState gameState;
    private PitchDimensions pitchDimensions;
    private int attack, defence;
    private TeamDirections attackDirections, defenceDirections;

    public KickOffFormationSystem(World world){
        this.gameStateQuery = world.query(GameState.class);
        this.pitchQuery = world.query(PitchDimensions.class);
        this.teamsQuery = world.query(Phase.class, TeamDirections.class);
        this.query = world.query(Transform.class, RugbyPosition.class, Member.class);
    }

    @Override
    public void update(double dt) {
        gameStateQuery.forEach((entity, gameState) -> {
            this.gameState = (GameState) gameState;
        });
        if (!gameState.flags.contains(GameStates.KICK_OFF)) return;

        // TODO new singleton world access methods.
        // TODO build stores.
        pitchQuery.forEach((int pitch, PitchDimensions pitchDimensions) -> {
            this.pitchDimensions = pitchDimensions;
        });
        teamsQuery.forEach((int team, Phase phase, TeamDirections teamDirections) -> {
            if (phase.isAttacking()){
                this.attack = team;
                this.attackDirections = teamDirections;

            } else {
                this.defence = team;
                this.defenceDirections = teamDirections;
            }
        });

        query.forEach((int entity, Transform transform, RugbyPosition position, Member member) -> {
            process(transform, position, member);
        });
    }

    public void process(Transform transform, RugbyPosition position, Member member) {
        // TODO is it right to query each entity without going through the team entity.
        if (member.team == attack) {
            switch (position.position) {
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
        } else {
            // closest to furthest, left to right
            switch (position.position) {
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
