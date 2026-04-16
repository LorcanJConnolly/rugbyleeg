package systems.update.events;

import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;

public class FlushEventBusSystem implements UpdateSystem {
    private final World world;
    private final EventBus bus;

    public FlushEventBusSystem(World world, EventBus bus) {
        this.world = world;
        this.bus = bus;
    }


    @Override
    public void update(double dt) {
        bus.getDispatcher().flush(bus);
    }

    @Override
    public void registerListeners(CommandBus bus){}


    @Override
    public void registerSubscriptions(EventBus bus){}
}
