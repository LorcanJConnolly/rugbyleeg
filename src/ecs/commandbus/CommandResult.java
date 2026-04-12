package ecs.commandbus;

/**
 * The result of an issued command via {@link CommandBus}
 *
 * <p></> Commands may be rejected by middleware or by the handler itself. This class allows the caller to interpret the
 * result and react (retry, emit event, ignore, etc.).<p></>
 */
public class CommandResult {

    private final boolean success;
    private final String rejectionReason; // Human readable description of failure for debugging.


    public CommandResult(boolean success) {
        this.success = success;
        this.rejectionReason = null;
    }

    public CommandResult(boolean success, String rejectionReason) {
        this.success = success;
        this.rejectionReason = rejectionReason;
    }


    /** Returns a successful CommandResult. */
    public static  CommandResult success(){
        return new CommandResult(true);
    }


    /** Returns a failed CommandResult with a reason for debugging. */
    public static  CommandResult failure(String reason){
        return new CommandResult(false, reason);
    }


    public boolean isSuccess(){
        return success;
    }


    public boolean isFailure(){
        return !isSuccess();
    }


    public String getRejectionReason(){
        return rejectionReason;
    }
}
