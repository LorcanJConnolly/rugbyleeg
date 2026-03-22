package ecs.consumers;

import ecs.Component;

@FunctionalInterface
public interface QuadEntityConsumer<
        A extends Component,
        B extends Component,
        C extends Component,
        D extends Component> {
    void accept(int entity, A componentA, B componentB, C componentC, D componentD);
}
