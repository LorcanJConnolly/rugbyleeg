package systems.kickoff.setup;

import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.KickKickOff;
import ecs.eventbus.EventBus;
import ecs.eventbus.events.GameReset;
import ecs.pipelines.update.UpdateSystem;

/**
 * Responsible for configuring the variables of a kick off.
 *
 * <p></> Includes: Which team is attacking and which team is defending.
 */
public class KickOffSetupSystem implements UpdateSystem {

    @Override
    public void update(double dt) {
        // System accepts events.
    }


    @Override
    public void registerListeners(CommandBus bus){
        // System accepts events.
    }


    @Override
    public void registerSubscriptions(EventBus bus){
        bus.subscribe(
                GameReset.class,
                event -> update(event.getDeltaTime())
        );
    }


}
