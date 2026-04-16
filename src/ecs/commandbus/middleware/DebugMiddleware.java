package ecs.commandbus.middleware;

import ecs.commandbus.Command;
import ecs.commandbus.CommandChain;
import ecs.commandbus.CommandMiddleware;
import ecs.commandbus.CommandResult;

public class DebugMiddleware implements CommandMiddleware<Command> {

    @Override
    public CommandResult intercept(Command command, CommandChain<Command> next) {
        System.out.println("Issuing: " + command.getClass().getSimpleName());
        CommandResult result = next.execute(command);   // let the chain run
        System.out.println("Result: " + result.isSuccess());
        return result;   // pass the result back up
    }

}
