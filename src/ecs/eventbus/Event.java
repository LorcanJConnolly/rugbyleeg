package ecs.eventbus;

public abstract class Event {
    private final double dt; // Record dt for system update method inputs.

    protected Event(double dt) {
        this.dt = dt;
    }

    public double getDeltaTime(){
        return dt;
    }
}
