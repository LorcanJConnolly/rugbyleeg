package ecs.commandbus;

/**
 * A callback invoked when an event of type {@code T} is dispatched.
 *
 * <p></> Typically implementations will be lightweight lambdas or methods references registered using
 * {@link CommandBus#register}}, heavyweight lifting is done by systems.<p></>
 * @param <T> The command type the handler processes.
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {

    CommandResult handle(T command);
}