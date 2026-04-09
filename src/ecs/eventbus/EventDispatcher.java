package ecs.eventbus;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * Manages the event queue of the eventbus, responsible for maintaining created events and dispatching them to
 * listeners.
 */
public class EventDispatcher {
    private final Queue<Event> queue = new ArrayDeque<>();


    /** Enqueue an event for deferred dispatch (i.e., the very end of the frame execution). */
    public void enqueue(Event event){

    }


    public void flush(){

    }



    public void clear(){

    }



    public int pendingCount(){
        return queue.size();
    }
}
