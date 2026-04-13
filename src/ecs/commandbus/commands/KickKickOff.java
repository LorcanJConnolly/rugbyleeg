package ecs.commandbus.commands;

import ecs.commandbus.Command;
import util.vectors.Vector2;

public class KickKickOff extends Command {
    public double velocity;
    public double theta_x;
    public double theta_z;

    public KickKickOff(double dt, long timestamp, double velocity, double theta_x, double theta_z) {
        super(dt, timestamp);
        this.velocity = velocity;
        this.theta_x = theta_x;
        this.theta_z = theta_z;
    }
}
