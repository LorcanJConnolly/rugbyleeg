package ecs.eventbus;

/**
 * A callback invoked when an event of type {@code T} is dispatched.
 *
 * <p></> Typically implementations will be lightweight lambdas or methods references registered using
 * {@link EventBus#subscribe}}, heavyweight lifting is done by systems.<p></>
 * @param <T> The event type the handler processes.
 */
@FunctionalInterface
public interface EventHandler<T extends Event> {

    void handle(T event);
}
