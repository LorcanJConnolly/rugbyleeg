package ecs.commandbus;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Responsible for holding the commands issued by {@link CommandBus#issueDeferred(Command)} in order and issuing them to
 * their handler at a fixed point in a frame's lifecycle, each frame.
 *
 * <p></> Deferred commands are typically commands which modify the state of the game and so cannot be issued mid-frame.
 * <p></>
 */
public class CommandQueue {
    private final Queue<Command> queue;


    public CommandQueue(){
        queue = new ArrayDeque<>();
    }


    /** Enqueue an event for deferred dispatch (i.e., the very end of the frame execution). */
    public void enqueue(Command command){
        queue.add(command);
    }


    /** Consume and hand all events in the queue to the eventbus to be dispatched. */
    public void flush(CommandBus bus){
        while (!queue.isEmpty()){
            bus.issue(queue.poll());
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
