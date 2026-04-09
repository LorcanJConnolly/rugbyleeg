package ecs.eventbus;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventDispatcher {
    private final Queue<Event> queue = new ArrayDeque<>();
}
