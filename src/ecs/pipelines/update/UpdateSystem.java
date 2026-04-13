package ecs.pipelines.update;

import ecs.commandbus.Command;
import ecs.eventbus.EventBus;

public interface UpdateSystem {

    public void update(double dt);

    public void registerListeners(Command bus);

    public void registerSubscriptions(EventBus bus);
}
