package ecs.eventbus.handlers;

import ecs.eventbus.EventHandler;
import ecs.eventbus.events.KickOffLinedUp;
import systems.update.game.GameStateSystem;

public class KickOffLinedUpHandler implements EventHandler<KickOffLinedUp> {

    private final GameStateSystem gameStateSystem;

    public KickOffLinedUpHandler(GameStateSystem gameStateSystem) {
        this.gameStateSystem = gameStateSystem;
    }

    @Override
    public void handle(KickOffLinedUp event){
        gameStateSystem.onKickOffLineUp(event);
    }
}
