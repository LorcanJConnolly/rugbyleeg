package systems.update.game;

import components.game.GameState;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.commands.SetUpKickOff;
import ecs.eventbus.EventBus;
import ecs.eventbus.events.KickOffLinedUp;
import ecs.eventbus.events.KickOffTaken;
import ecs.eventbus.handlers.KickOffLinedUpHandler;
import ecs.pipelines.update.UpdateSystem;
import state.game.GameStates;

public class GameStateSystem implements UpdateSystem {
    private final GameState state;
    private final EventBus eventBus;
    private final CommandBus commandBus;

    public GameStateSystem(World world, EventBus eventBus, CommandBus commandBus) {
        this.state = world.getSingleton(GameState.class);
        this.eventBus = eventBus;
        this.commandBus = commandBus;
    }

    @Override
    public void update(double dt) {
        if (state.hasFlag(GameStates.NEW_GAME)){
            commandBus.issue(new SetUpKickOff(dt, System.nanoTime()));
            state.removeFlag(GameStates.NEW_GAME);
            state.addFlag(GameStates.SETTING_KICK_OFF);
        }
    }

    @Override
    public void registerListeners(CommandBus bus){}


    @Override
    public void registerSubscriptions(EventBus bus){
        bus.subscribe(KickOffLinedUp.class, new KickOffLinedUpHandler(this));
        bus.subscribe(KickOffTaken.class, new KickOffTakenHandler(this));
    }


    public void onKickOffLineUp(KickOffLinedUp event){
        state.removeFlag(GameStates.SETTING_KICK_OFF);
        state.addFlag(GameStates.AIMING_KICKOFF);
    }


    public void onKickOffTaken(KickOffTaken event){
        state.removeFlag(GameStates.AIMING_KICKOFF);
        state.addFlag(GameStates.CHASING_KICK);
    }

}
