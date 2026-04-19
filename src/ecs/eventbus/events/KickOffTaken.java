package ecs.eventbus.events;

import ecs.eventbus.Event;

public class KickOffTaken extends Event {

    public KickOffTaken(double dt){
        super(dt);
    }
}
