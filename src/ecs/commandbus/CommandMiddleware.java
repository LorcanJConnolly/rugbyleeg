package ecs.commandbus;

/**
 * An interceptor in the command pipeline. Middleware runs before a command is processed by its handler and is able to
 * inspect, modify, log, or reject the command.
 *
 * <p ></>Typically used for logging all commands for replay/debugging, validating that the issuing entity has
 * permission to issue a command, rate-limiting commands of a given type, or recording commands for an undo stack.<p></>
 *
 * @param <T> the command type this middleware applies to.
 */
public interface CommandMiddleware<T extends Command> {

    /**
     * Intercepts a command before it reaches its handler.
     *
     * @param command   The command being issued.
     * @param next      The next step in the middleware chain. Must be called to continue execution towards the
     *                  command's handler.
     * @return          The result of the next step in the middleware, or this middleware if it has short-circuited (not
     *                  executed the next step when expected to).
     */
    CommandResult intercept(T command, CommandChain<T> next);

}
