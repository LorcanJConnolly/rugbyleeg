package ecs.commandbus;

/**
 * Represents the remaining steps in a chain of middleware commands which ends at the command's handler. Passed to each
 * {@link CommandMiddleware} so it can choose to continue or stop the chain.
 *
 * <p></> Structure of the command chian is [Command] -> [N CommandMiddleware] -> [Handler]. <p></>
 *
 * @param <T> The type of command issued and flowing through the command chain.
 */
@FunctionalInterface
public interface CommandChain<T extends Command> {


    /**
     * Hands the command to the next step in the command chain.
     *
     * @param command   The command to hand forward.
     * @return          The result of the remainder steps of the chain.
     */
    public CommandResult execute(T command);
}
