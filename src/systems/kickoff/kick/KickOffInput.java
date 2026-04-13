package systems.kickoff.kick;


import components.player.inputs.Inputs;
import components.player.team.Member;
import components.singletons.game.GameState;
import components.singletons.game.SingletonEntities;
import components.singletons.pitch.PitchDimensions;
import components.team.direction.Directions;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.commands.KickKickOff;
import ecs.pipelines.update.UpdateSystem;
import input.Button;
import state.game.GameStates;
import util.directions.Direction;
import util.pitch.PitchUtils;
import util.vectors.Vector2;

import java.util.Map;

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

    double x, y, angle;
    double max_x, max_y, max_angle;
    double min_x, min_y, min_angle;
    double increment_x, increment_y, increment_angle;

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

        this.angle = 45.0;
        this.min_angle = 10.0;
        this.max_angle = 80.0;
        this.increment_angle = 5.0;

        this.x = pitchDimensions.aabb.width/2;
        this.min_x = attackDirections.forward == Direction.UP ? 1.0 : pitchDimensions.aabb.width;
        this.max_x = attackDirections.forward == Direction.UP ? pitchDimensions.aabb.width : 1.0;
        this.increment_x = attackDirections.forward == Direction.UP ? 10.0 : -10.0;

        this.y = attackDirections.forward == Direction.UP ? pitchDimensions.aabb.height/4 : 3*pitchDimensions.aabb.height/4;
        this.min_y = attackDirections.forward == Direction.UP ? pitchDimensions.aabb.height/2 : 1.0;
        this.max_y = attackDirections.forward == Direction.UP ? 1.0 : pitchDimensions.aabb.height;
        this.increment_y = attackDirections.forward == Direction.UP ? 10.0 : -10.0;
    }

    @Override
    public void update(double dt) {
        if (!gameState.hasFlag(GameStates.KICK_OFF)) return;

        if (inputs.pressed.get(Button.OPTION_1) || inputs.held.get(Button.OPTION_1)
        ) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)) {
                x += increment_x * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                x += -increment_x * dt;
            }
            x = Math.max(Math.min(x, max_x), min_x);
        } else if (inputs.pressed.get(Button.OPTION_2) || inputs.held.get(Button.OPTION_2)) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)) {
                y += increment_y * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                y += -increment_y * dt;
            }
            y = Math.max(Math.min(y, max_y), min_y);
        } else if (inputs.pressed.get(Button.OPTION_3) || inputs.held.get(Button.OPTION_3)) {
            if (inputs.pressed.get(Button.MOVE_UP) || inputs.held.get(Button.MOVE_UP)){
                angle += increment_angle * dt;

            } else if (inputs.pressed.get(Button.MOVE_DOWN) || inputs.held.get(Button.MOVE_DOWN)) {
                angle += -increment_angle * dt;
            }
            angle = Math.max(Math.min(angle, max_angle), min_angle);
        }

        if (inputs.pressed.get(Button.ACCEPT) || inputs.held.get(Button.ACCEPT)) {
            Vector2 target = new Vector2(x, y);
            commandBus.issue(new KickKickOff(dt, System.nanoTime(), target, angle));
        }
    }
}
