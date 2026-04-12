package ecs.eventbus;

@FunctionalInterface
public interface EventHandler<T extends Event> {

    void handle(T event);
}
