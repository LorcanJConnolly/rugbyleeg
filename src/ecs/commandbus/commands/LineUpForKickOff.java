package ecs.commandbus.commands;

import ecs.commandbus.Command;


/** A command for setting up the formation for a kick off. */
public class LineUpForKickOff extends Command {

    public LineUpForKickOff(double dt, long timestamp) {
        super(dt, timestamp);
    }
}
