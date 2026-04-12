package ecs.eventbus.events;

import ecs.eventbus.Event;

/**
 * An event describing when a game resets (a kick off happens) due to a point being scored, a new game, half-time,
 * extra time, etc.
 */
public class GameReset extends Event {

    protected GameReset(double dt) {
        super(dt);
    }

}
