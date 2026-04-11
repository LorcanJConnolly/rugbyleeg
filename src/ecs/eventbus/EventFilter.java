package ecs.eventbus;

/**
 * A predicate applied to an event before invoking its handler.
 *
 * <p></> Provides a function interface (suitable for use with lambda functions) for implementing EventFilter classes.
 * <p></>
 *
 * @param <T> The event type the filter is applied to.
 */
@FunctionalInterface
public interface EventFilter<T extends Event> {


    /** Returns true if the event's handler should be invoked for the given event instance. */
    boolean test(T event);
}
