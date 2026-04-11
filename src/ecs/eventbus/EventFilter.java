package ecs.eventbus;

/**
 * A predicate applied to an event before invoking its handler.
 * @param <T> The event type the filter is applied to.
 */
@FunctionalInterface
public interface EventFilter<T extends Event> {


    /** Returns true if the event's handler should be invoked for the given event instance. */
    boolean test(T event);
}
