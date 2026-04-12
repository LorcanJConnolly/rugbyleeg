package ecs.commandbus;


import ecs.eventbus.HandlerEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The central command bus. Responsible for routing commands to their registered handler and running them through their
 * middleware chain before delivery.
 */
public class CommandBus {
    // Owns the handlers.
    // {command type: handler}
    private final Map<Class<?>, CommandHandler<?>> handlers;
    // Owns command chains and their middleware
    private final List<CommandMiddleware<?>> middlewareChain;
    // Owns the command queue
    private final CommandQueue queue;


    public CommandBus() {
        this.handlers = new HashMap<>();
        this.middlewareChain = new ArrayList<>();
        this.queue = new CommandQueue();
    }


    /**
     * Registers a handler responsible for executing commands of a given type. NOTE: only one handler may be
     * registered to a single command type.
     * @param commandType   The class of the type of command the handler processes.
     * @param handler       The handler to invoke when a command of this type is issued.
     * @param <T>           The command type.
     */
    public <T extends Command> void register(
            Class<T> commandType,
            CommandHandler<T> handler
        ){
        if (handlers.containsKey(commandType)){
            throw new RuntimeException(
                    "The command type '" + commandType + "' already has a handler in the command bus."
            );
        }
        handlers.put(commandType, handler);
    }


    public <T extends Command> void addMiddleware(CommandMiddleware<T> middleware){
        middlewareChain.add(middleware);
    }


    /**
     * Issues a command for immediate execution by its handler.
     * @param command   The command to be issued.
     * @return          A {@link CommandResult} object indicating the result of the command.
     * @param <T>       The command type.
     */
    @SuppressWarnings("unchecked")
    public <T extends Command> CommandResult issue(T command){
        CommandHandler<T> handler = (CommandHandler<T>) handlers.get(command.getClass());
        if (handler == null){
            return CommandResult.failure(
                    "No handler registered for the command '" + command.getClass().getSimpleName() +"'."
            );
        }
        // Construct the command chain
        CommandChain<T> chain = handler::handle;
        for (int i = middlewareChain.size() -1; i >=0; i--) {
            CommandMiddleware<T> middleware = (CommandMiddleware<T>) middlewareChain.get(i);
            CommandChain<T> next = chain;
            // Chain is now a lambda that when called runs middleware.intercept(cmd, next) N times until next = handler.
            chain = cmd -> middleware.intercept(cmd, next);
        }
        return chain.execute(command);
    }


    /**
     * Issues a command to the command queue for deferred execution by its handler.
     * @param command   The command to be issued.
     * @param <T>       The command type.
     */
    public <T extends Command> void issueDeferred(T command){
        queue.enqueue(command);
    }


    /**
     * Removes the handler registered for a given command type. All subsequent commands produce a failed CommandResult.
     * @param commandType   The class of the type of command to remove the handler of.
     * @param <T>           The command type.
     */
    public <T extends Command> void unregister(Class<T> commandType){
        handlers.remove(commandType);
    }
}
