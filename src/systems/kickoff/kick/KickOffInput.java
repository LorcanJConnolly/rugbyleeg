package systems.kickoff.kick;


import components.inputs.Inputs;
import components.team.Member;
import components.game.GameState;
import components.game.SingletonEntities;
import components.pitch.PitchDimensions;
import components.direction.Directions;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.commands.KickKickOff;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import input.Button;
import state.game.GameStates;
import util.directions.Direction;

/**
 * A system for translating the user's button inputs to a command to preform a kick for the kick-off.
 */
public class KickOffInput implements UpdateSystem {
    private final Inputs inputs;
    private final Member team;

    private final GameState gameState;
    private final CommandBus commandBus;
    private final PitchDimensions pitchDimensions;
    private final Directions attackDirections;

    double v, theta_x, theta_z;
    double min_v, min_theta_x, min_theta_z;
    double max_v, max_theta_x, max_theta_z;
    double increment_v, increment_theta_x, increment_theta_z;

    public KickOffInput(World world, CommandBus commandBus){
        this.pitchDimensions = world.getSingleton(PitchDimensions.class);
        SingletonEntities singletonEntities = world.getEntityComponent(
                world.getSingletonEntity(GameState.class), SingletonEntities.class
        );
        this.attackDirections = world.getEntityComponent(singletonEntities.getAttack(), Directions.class);
        this.gameState = world.getSingleton(GameState.class);
        int player = world.getSingletonEntity(Inputs.class);
        this.inputs = world.getEntityComponent(player, Inputs.class);
        this.team = world.getEntityComponent(player, Member.class);
        this.commandBus = commandBus;

        this.v = 10d;
        this.min_v = 5d;
        this.max_v = 20d;
        this.increment_v = 1d;

        this.theta_z = 45d;
        this.min_theta_z = 10d;
        this.max_theta_z = 80d;
        this.increment_theta_z = 5d;

        // Basic values - Note, kicking vector is corrected for pitch direction after construction.
        this.theta_x = 90d;
        this.min_theta_x = 20d;
        this.max_theta_x = 160d;
        this.increment_theta_x = 5d;
    }

    @Override
    public void update(double dt) {
        if (!gameState.hasFlag(GameStates.KICK_OFF)) return;

        if (inputs.pressed.get(Button.OPTION_1) || inputs.held.get(Button.OPTION_1)
        ) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)) {
                v += increment_v * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                v += -increment_v * dt;
            }
            v = Math.max(Math.min(v, max_v), min_v);
        } else if (inputs.pressed.get(Button.OPTION_2) || inputs.held.get(Button.OPTION_2)) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)) {
                theta_z += increment_theta_z * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                theta_z += -increment_theta_z * dt;
            }
            theta_z = Math.max(Math.min(theta_z, max_theta_z), min_theta_z);
        } else if (inputs.pressed.get(Button.OPTION_3) || inputs.held.get(Button.OPTION_3)) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)){
                theta_x += increment_theta_x * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                theta_x += -increment_theta_x * dt;
            }
            theta_x = Math.max(Math.min(theta_x, max_theta_x), min_theta_x);
        }

        if (inputs.pressed.get(Button.ACCEPT) || inputs.held.get(Button.ACCEPT)) {
            double theta_x = attackDirections.forward == Direction.UP ? this.theta_x : 180 + this.theta_x;
            commandBus.issue(new KickKickOff(dt, System.nanoTime(), v, theta_x, theta_x));
        }
    }


    @Override
    public void registerListeners(CommandBus bus){
        // None
    }


    @Override
    public void registerSubscriptions(EventBus bus){
        // None
    }
}
