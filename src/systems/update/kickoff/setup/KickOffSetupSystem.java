package systems.update.kickoff.setup;

import components.direction.Directions;
import components.game.GameState;
import components.game.SingletonEntities;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.LineUpForKickOff;
import ecs.commandbus.commands.SetUpKickOff;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import state.game.GameStates;
import util.directions.Direction;

/**
 * Responsible for configuring the variables of a kick off.
 *
 * <p></> Includes: Which team is attacking and which team is defending.
 */
public class KickOffSetupSystem implements UpdateSystem {
    private final CommandBus commandBus;
    private final GameState gameState;
    private final Directions attackDirections, defenceDirections;

    public KickOffSetupSystem(World world, CommandBus commandBus) {
        this.commandBus = commandBus;
        int game = world.getSingletonEntity(GameState.class);
        SingletonEntities singletonEntities = world.getEntityComponent(game, SingletonEntities.class);
        this.gameState = world.getEntityComponent(game, GameState.class);
        this.attackDirections = world.getEntityComponent(singletonEntities.getAttack(), Directions.class);
        this.defenceDirections = world.getEntityComponent(singletonEntities.getDefence(), Directions.class);
    }

    @Override
    public void update(double dt) {
        if (!gameState.hasFlag(GameStates.NEW_GAME)){
            configureTeamDirections();
        }
        commandBus.issue(new LineUpForKickOff(dt, System.nanoTime()));
    }

    /**
     * Swaps the team entity's direction component to the opposite values.
     */
    private void configureTeamDirections(){
        // Swap directions to opposites.
        attackDirections.forward = attackDirections.forward == Direction.UP ? Direction.DOWN : Direction.UP;
        attackDirections.backwards = attackDirections.backwards == Direction.DOWN ? Direction.UP : Direction.DOWN;
        attackDirections.left = attackDirections.left == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
        attackDirections.forward = attackDirections.right == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;

        defenceDirections.forward = defenceDirections.forward == Direction.UP ? Direction.DOWN : Direction.UP;
        defenceDirections.backwards = defenceDirections.backwards == Direction.DOWN ? Direction.UP : Direction.DOWN;
        defenceDirections.left = defenceDirections.left == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
        defenceDirections.forward = defenceDirections.right == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;
    }


    @Override
    public void registerListeners(CommandBus bus){
        bus.register(
            SetUpKickOff.class,
            command -> {
                update(command.getDeltaTime());
                return CommandResult.success();
            }
        );
    }


    @Override
    public void registerSubscriptions(EventBus bus){}
}
