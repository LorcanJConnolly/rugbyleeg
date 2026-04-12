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
import util.pitch.PitchUtils;
import util.vectors.Vector2;

import java.util.Map;

/**
 * A system for translating the user's button inputs to a command to preform a kick for the kick-off.
 */
public class KickOffInput implements UpdateSystem {
    private final Inputs inputs;
    private final Member team;

    private final PitchDimensions pitchDimensions;

    private final Directions attackDirections;
    private final GameState gameState;
    private final CommandBus commandBus;

    private double x  = -1;
    private double y   = -1;

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
    }

    @Override
    public void update(double dt) {
        if (!gameState.hasFlag(GameStates.KICK_OFF)) return;

        Map<Button, Boolean>  current = inputs.current;
        if (x == -1){
            if (current.get(Button.OPTION_1)){
                x = 0.05;
            } else if (current.get(Button.OPTION_2)) {
                x = 0.15;
            } else if (current.get(Button.OPTION_3)) {
                x = 0.25;
            } else if (current.get(Button.OPTION_4)) {
                x = 0.35;
            } else if (current.get(Button.OPTION_5)) {
                x = 0.45;
            }
        } else if (y == -1) {
            if (current.get(Button.OPTION_1)){
                y = 0.05;
            } else if (current.get(Button.OPTION_2)) {
                y = 0.15;
            } else if (current.get(Button.OPTION_3)) {
                y = 0.25;
            } else if (current.get(Button.OPTION_4)) {
                y = 0.35;
            } else if (current.get(Button.OPTION_5)) {
                y = 0.45;
            }
        } else {
            Vector2 target = PitchUtils.relativeToPlayingField(pitchDimensions, attackDirections.forward, x, 0.4 + y);
            // create kick
            commandBus.issue(new KickKickOff(dt, System.nanoTime(), target));
        }
    }
}
