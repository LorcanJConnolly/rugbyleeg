package systems.update.kickoff.formation;

import components.kinematics.Transform;
import components.game.GameState;
import components.game.SingletonEntities;
import components.pitch.PitchDimensions;
import components.rugby.position.RugbyPosition;
import components.rugby.team.Member;
import components.direction.Directions;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.LineUpForKickOff;
import ecs.eventbus.EventBus;
import ecs.eventbus.events.KickOffLinedUp;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import util.pitch.PitchUtils;

public class KickOffFormationSystem implements UpdateSystem {
    private final EventBus eventBus;
    private final Query query;
    private final PitchDimensions pitchDimensions;
    private final int attack;
    private final int defence;
    private final Directions attackDirections, defenceDirections;
    private final Transform ballTransform;

    public KickOffFormationSystem(World world, EventBus eventBus){
        this.eventBus = eventBus;
        this.pitchDimensions = world.getSingleton(PitchDimensions.class);
        SingletonEntities singletonEntities = world.getEntityComponent(
                world.getSingletonEntity(GameState.class), SingletonEntities.class
        );

        this.attack = singletonEntities.getAttack();
        this.defence = singletonEntities.getDefence();
        this.attackDirections = world.getEntityComponent(this.attack, Directions.class);
        this.defenceDirections = world.getEntityComponent(this.defence, Directions.class);
        this.ballTransform = world.getEntityComponent(singletonEntities.getBall(), Transform.class);
        this.query = world.query(Transform.class, RugbyPosition.class, Member.class);
    }


    @Override
    public void registerListeners(CommandBus bus){
        bus.register(
            LineUpForKickOff.class,
            command -> {
                update(command.getDeltaTime());
                return CommandResult.success();
            }
        );
    }


    @Override
    public void registerSubscriptions(EventBus bus){}


    @Override
    public void update(double dt) {
        // register command listening
        query.forEach((int entity, Transform transform, RugbyPosition position, Member member) -> {
            process(transform, position, member);
        });
        ballTransform.position = PitchUtils.relativeToPlayingField(
                pitchDimensions, attackDirections.forward,
                0.5, 0.5
        );

        eventBus.emit(new KickOffLinedUp(dt));
    }


    public void process(Transform transform, RugbyPosition position, Member member) {
        if (member.getTeam() == attack) {
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
                case FULLBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.forward,
                            0.5, 0.3
                    );
                    break;
            }
        } else if (member.getTeam() == defence) {
            // closest to furthest, left to right
            switch (position.getPosition()) {
                case CENTRE_3:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.03, 0.38
                    );
                    break;
                case LOCK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.5, 0.35
                    );
                    break;
                case CENTRE_4:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.97, 0.38
                    );
                    break;
                case SECOND_ROW_11:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.03, 0.29
                    );
                    break;
                case HOOKER:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.26, 0.5
                    );

                case SECOND_ROW_12:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.97, 0.29
                    );
                    break;
                case FIVE_EIGHTH:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.18, 0.07
                    );
                    break;
                case HALFBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.72, 0.07
                    );
                    break;
                case WING_2:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.02, 0
                    );
                    break;
                case WING_5:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
                            0.98, 0
                    );
                    break;

                case FULLBACK:
                    transform.position = PitchUtils.relativeToPlayingField(
                            pitchDimensions, attackDirections.backwards,
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
