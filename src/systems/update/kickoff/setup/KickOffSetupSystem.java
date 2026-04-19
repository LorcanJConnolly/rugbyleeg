package systems.update.kickoff.setup;

import components.direction.Directions;
import components.game.GameState;
import components.game.SingletonEntities;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.LineUpForKickOff;
import ecs.commandbus.commands.SetUpKickOff;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
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
    private final Query ZAxsiQuery;

    public KickOffSetupSystem(World world, CommandBus commandBus) {
        this.commandBus = commandBus;
        int game = world.getSingletonEntity(GameState.class);
        SingletonEntities singletonEntities = world.getEntityComponent(game, SingletonEntities.class);
        this.gameState = world.getEntityComponent(game, GameState.class);
        this.attackDirections = world.getEntityComponent(singletonEntities.getAttack(), Directions.class);
        this.defenceDirections = world.getEntityComponent(singletonEntities.getDefence(), Directions.class);

        this.ZAxsiQuery = world.query(ZAxis.class);
    }



    @Override
    public void update(double dt) {}


    /** Manager method, hands off to handler methods. */
    private void setUp(double dt){
        configureTeamDirections();
        setZAxis();
        commandBus.issue(new LineUpForKickOff(dt, System.nanoTime()));
    }

    /** Swaps the team entity's direction component to the opposite values. */
    private void configureTeamDirections(){
        // Swap directions to opposites.
        attackDirections.forward = attackDirections.forward == Direction.UP ? Direction.DOWN : Direction.UP;
        attackDirections.backwards = attackDirections.backwards == Direction.DOWN ? Direction.UP : Direction.DOWN;
        attackDirections.left = attackDirections.left == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
        attackDirections.right = attackDirections.right == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;

        defenceDirections.forward = defenceDirections.forward == Direction.UP ? Direction.DOWN : Direction.UP;
        defenceDirections.backwards = defenceDirections.backwards == Direction.DOWN ? Direction.UP : Direction.DOWN;
        defenceDirections.left = defenceDirections.left == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
        defenceDirections.right = defenceDirections.right == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;
        System.out.println("attack: " + attackDirections.forward + ", defence: " + defenceDirections.forward);
    }


    /** Clean up method to set every entity's ZAxis data back to 0. */
    private void setZAxis(){
        ZAxsiQuery.forEach((int entity, ZAxis zAxis) -> {
            zAxis.setPosition(0d);
            zAxis.setVelocity(0d);
        });
    }


    @Override
    public void registerListeners(CommandBus bus){
        bus.register(
            SetUpKickOff.class,
            command -> {
                setUp(command.getDeltaTime());
                return CommandResult.success();
            }
        );
    }


    @Override
    public void registerSubscriptions(EventBus bus){}
}
