package ecs.consumers;

/**
 * A class providing a functional interface for implementing the interface of a consumer (an operation) that accepts
 * the results of a query for an entity in a number of component stores: the entity found and its component(s).
 *
 * <p>The EntityConsumer class provides an interface for a consumer accepting the results of query of type Query2.</p>
 *
 * @param <A> The type of the component being consumed with the entity.
 * @param <B> The type of the component being consumed with the entity.
 */
@FunctionalInterface
public interface BiEntityConsumer<A, B> {

    /** The consumer method, accepting an entity and its component(s).

     @param entity The entity.
     @param componentA The entity's component.
     @param componentB The entity's component.
     */
    void accept(int entity, A componentA, B componentB);
}
