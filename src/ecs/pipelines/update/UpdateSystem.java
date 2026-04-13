package ecs.pipelines.update;

import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;

public interface UpdateSystem {

    public void update(double dt);

    public void registerListeners(CommandBus bus);

    public void registerSubscriptions(EventBus bus);
}
