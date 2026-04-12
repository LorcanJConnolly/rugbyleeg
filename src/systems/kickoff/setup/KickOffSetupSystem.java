package systems.kickoff.setup;

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
        return;
    }

    /** Called during world creation. Subscribers the system to its events. */
    public void registerSubscription(EventBus eventBus){
        eventBus.subscribe(
                GameReset.class,
                event -> update(event.getDt())
        );
    }


}
