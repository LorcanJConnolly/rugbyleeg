package ecs.commandbus.commands;

import ecs.commandbus.Command;

public class SetUpKickOff extends Command {


    public SetUpKickOff(double dt, long timestamp, int targetEntity) {
        super(dt, timestamp, targetEntity);
    }

    public SetUpKickOff(double dt, long timestamp) {
        super(dt, timestamp);
    }
}
