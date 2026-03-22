package systems.formation.kickoff;

import components.kinematics.Transform;
import components.pitch.PitchDimensions;
import components.rugby.RugbyPosition;
import components.state.game.GameState;
import ecs.World;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import state.game.GameStates;

public class SetUpForKickOffSystem implements UpdateSystem {
    private final Query gameStateQuery;
    private final Query pitchQuery;
    private final Query query;

    private GameState gameState;
    private PitchDimensions pitchDimensions;

    public SetUpForKickOffSystem(World world){
        this.gameStateQuery = world.query(GameState.class);
        this.pitchQuery = world.query(PitchDimensions.class);
        this.query = world.query(Transform.class, RugbyPosition.class);

    }

    @Override
    public void update(double dt) {
        gameStateQuery.forEach((entity, gameState) -> {
            this.gameState = (GameState) gameState;
        });
        if (!gameState.flags.contains(GameStates.KICK_OFF)) return;

        pitchQuery.forEach((entity, pitchDimensions) -> {
            this.pitchDimensions = (PitchDimensions) pitchDimensions;
        });

        query.forEach((entity, transform, position) -> {
            process((Transform) transform, (RugbyPosition) position);
        });
    }

    public void process(Transform transform, RugbyPosition position) {
        // switch case for setting positions?
    }
}
