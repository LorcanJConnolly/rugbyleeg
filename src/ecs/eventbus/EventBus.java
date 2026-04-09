package ecs.eventbus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The central event bus.
 */
public class EventBus {

    public EventBus(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * Internal class for representing a handler and its optional filter.
     * @param handler: The EventHandler.
     * @param filter: An optional filter (can be null) for the handler.
     * @param <T>: An event type.
     */
    private record HandlerEntry<T extends Event> (
            EventHandler<T>     handler,
            EventFilter<T>      filter
    ) {}

    // Owns the subscribers.
    private final Map<Class<?>, List<HandlerEntry<?>>> handlers = new HashMap<>();
    // Owns the event queue.
    private final EventDispatcher dispatcher;


    /**
     * Subscribes a handler to listen to and receive all events of the given type.
     * @return
     * @param <T>
     */
    public <T extends Event> EventSubscription subscribe(Class<T> eventType, EventHandler<T> handler){

    }


    /**
     * Subscribes a handler to listen to and receive all events of the given type that satisfying the given filter.
     * @return
     * @param <T>
     */
    public <T extends Event> EventSubscription subscribe(){}
}
