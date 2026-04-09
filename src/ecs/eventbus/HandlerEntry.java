package ecs.eventbus;

/**
 * Class for an entry of the eventbus subscribers, consisting of a handler and its optional filter eventbus.
 * @param handler: The EventHandler.
 * @param filter: An optional filter (can be null) for the handler.
 * @param <T>: An event type.
 */
public record HandlerEntry<T extends Event> (
        EventHandler<T>     handler,
        EventFilter<T>      filter
) {}