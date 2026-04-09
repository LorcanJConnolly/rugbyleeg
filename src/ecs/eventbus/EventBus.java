package ecs.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The central eventbus.
 *
 * <p></> This is a deferred event bus, meaning that the events added to the event queue of the eventbus are not
 * dispatched until *later*, i.e., if an event is added to the event queue in a system during the execution of a frame,
 * the event will be dispatched at the very end of the frame. <p></>
 */
public class EventBus {

    public EventBus(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    // Owns the subscribers.
    private final Map<Class<?>, List<HandlerEntry<?>>> handlers = new HashMap<>();
    // Owns the event queue.
    private final EventDispatcher dispatcher;


    /**
     * Subscribes a handler to listen to and receive all events of the given type.
     * @return An EventSubscriber object acting as a reference to the live subscription.
     * @param <T> An event of type T.
     */
    public <T extends Event> EventSubscription subscribe(
            Class<T> eventType,
            EventHandler<T> handler
    ) {
        HandlerEntry<T> entry = new HandlerEntry<>(handler, null);
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(entry);
        return new EventSubscription(this, eventType, entry);
    }


    /**
     * Subscribes a handler to listen to and receive all events of the given type that satisfying the given filter.
     * @return An EventSubscriber object acting as a reference to the live subscription.
     * @param <T> An event of type T.
     */
    public <T extends Event> EventSubscription subscribe(
            Class<T> eventType,
            EventHandler<T> handler,
            EventFilter<T> filter
    ) {
        HandlerEntry<T> entry = new HandlerEntry<>(handler, filter);
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(entry);
        return new EventSubscription(this, eventType, entry);
    }

    /** Hands an event to the EventDispatcher to be queued. */
    public <T extends Event> void emit(T event){
        dispatcher.enqueue(event);
    }


    /** Dispatches an event to its handlers. NOTE: also Used to dispatch events that cannot be deferred. */
    public <T extends Event> void dispatch(T event){

    }


    /** Removes a handler from a subscription entry. */
    public <T extends Event> void removeHandler(Class<T> eventType, HandlerEntry<?> entry){
        List<HandlerEntry<?>> entries = handlers.get(eventType);
        if (entries != null) {
            entries.remove(entry);
        }
    }


    /**
     * Removes all subscriptions for a given event type.
     *
     * @param eventType the event type whose subscribers should all be cleared.
     */
    public void clearSubscribers(Class<? extends Event> eventType){
        handlers.remove(eventType);
    };

    /** Removes every subscription across all event types. */
    public void clearAll(){
        handlers.clear();
    };
}
