package ecs.eventbus;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * Manages the event queue of the eventbus, responsible for holding created events in the queue and handing them to the
 * eventbus and its subscribers when consumed.
 */
public class EventDispatcher {
    private final Queue<Event> queue = new ArrayDeque<>();
    private final EventBus eventBus;


    public EventDispatcher(EventBus eventBus){
        this.eventBus = eventBus;
    }


    /** Enqueue an event for deferred dispatch (i.e., the very end of the frame execution). */
    public void enqueue(Event event){
        queue.add(event);
    }


    /** Consume and hand all events in the queue to the eventbus to be dispatched. */
    public void flush(){
        while (!queue.isEmpty()){
            eventBus.dispatch(queue.poll());
        }
    }


    /** Clear the queue. */
    public void clear(){
        queue.clear();
    }


    public int pendingCount(){
        return queue.size();
    }
}
