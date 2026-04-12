package ecs.commandbus.commands;

import ecs.commandbus.Command;


/** A command for setting up the formation for a kick off. */
public class LineUpForKickOff extends Command {

    protected LineUpForKickOff(double dt, long timestamp, int targetEntity) {
        super(dt, timestamp, targetEntity);
    }
}
