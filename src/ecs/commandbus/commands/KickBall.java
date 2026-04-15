package ecs.commandbus.commands;

import ecs.commandbus.Command;

public class KickBall extends Command {
    public double velocity;
    public double theta_x;
    public double theta_z;

    public KickBall(double dt, long timestamp, double velocity, double theta_x, double theta_z) {
        super(dt, timestamp);
        this.velocity = velocity;
        this.theta_x = theta_x;
        this.theta_z = theta_z;
    }
}
