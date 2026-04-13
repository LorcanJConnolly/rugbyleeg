package ecs.commandbus.commands;

import ecs.commandbus.Command;

public class SetUpKickOff extends Command {


    protected SetUpKickOff(double dt, long timestamp, int targetEntity) {
        super(dt, timestamp, targetEntity);
    }
}
