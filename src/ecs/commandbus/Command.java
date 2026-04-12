package ecs.commandbus;

/**
 * The base class for all commands. Represents the intent to change something in the game - "do this".
 *
 * <p></> Subclasses are immutable objects containing the data the command's handler requires to execute the command.
 */
public abstract class Command {
    private final double dt;                  // Record dt for system update method inputs.
    private final long timestamp;       // In nanoseconds to match dt.
    private final int targetEntity;     // Optional


    protected Command(double dt, long timestamp, int targetEntity) {
        this.dt = dt;
        this.timestamp = timestamp;
        this.targetEntity = targetEntity;
    }

    public double getDeltaTime() {
        return dt;
    }
}
