package ecs.commandbus.commands;

import ecs.commandbus.Command;
import util.vectors.Vector2;

public class KickKickOff extends Command {
    public Vector2 target;
    public double angle;

    public KickKickOff(double dt, long timestamp, Vector2 target, double angle) {
        super(dt, timestamp);
        this.target = target;
        this.angle = angle;
    }
}
