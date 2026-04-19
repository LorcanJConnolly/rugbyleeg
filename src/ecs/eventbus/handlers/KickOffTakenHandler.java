package ecs.eventbus.handlers;

import ecs.eventbus.EventHandler;
import ecs.eventbus.events.KickOffLinedUp;
import ecs.eventbus.events.KickOffTaken;
import systems.update.game.GameStateSystem;

public class KickOffTakenHandler implements EventHandler<KickOffTaken> {
    private final GameStateSystem gameStateSystem;

    public KickOffLinedUpHandler(GameStateSystem gameStateSystem) {
        this.gameStateSystem = gameStateSystem;
    }

    @Override
    public void handle(KickOffTaken event){
        gameStateSystem.onKickOffTaken(event);
    }
}
