package ecs.commandbus;


/**
 * The central command bus. Responsible for routing commands to their registered handler and running them through their
 * middleware chain before delivery.
 */
public class CommandBus {

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
        return;
    }

    /**
     * Issues a command for immediate execution by its handler.
     * @param command   The command to be issued.
     * @return          A {@link CommandResult} object indicating the result of the command.
     * @param <T>       The command type.
     */
    public <T extends Command> CommandResult issue(T command){
        return;
    }


    /**
     * Issues a command to the command queue for deferred execution by its handler.
     * @param command   The command to be issued.
     * @param <T>       The command type.
     */
    public <T extends Command> void issueDeferred(T command){
        return;
    }


    /**
     * Removes the handler registered for a given command type. All subsequent commands produce a failed CommandResult.
     * @param commandType   The class of the type of command to remove the handler of.
     * @param <T>           The command type.
     */
    public <T extends Command> void unregister(Class<T> commandType){
        return;
    }
}
