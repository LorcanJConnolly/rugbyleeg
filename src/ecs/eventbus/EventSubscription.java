package ecs.eventbus;


/**
 * A class representation of a live subscription to an event.
 *
 * <p></> Used for maintaining a reference to a subscription that allows for the subscription to be cancelled when
 * necessary without the need for a reference to the eventbus itself.<></>
 */
public class EventSubscription {

    private final EventBus bus;
    private final Class<? extends Event> eventType;
    private final HandlerEntry<?> entry;
    private boolean active;


    public EventSubscription(EventBus bus, Class<? extends Event> eventType, HandlerEntry<?> entry) {
        this.bus = bus;
        this.eventType = eventType;
        this.entry = entry;
        this.active = true;
    }


    /**
     * Removes this subscription's handler entry from the bus' subscriptions.
     */
    public void cancel(){
        if (isActive()){
            bus.removeHandler(eventType, entry);
            active = false;
        }
    }


    public boolean isActive(){
        return active;
    }
}
