package systems.kickoff.kick;

import components.player.kinematics.Motion;
import components.player.kinematics.Transform;
import ecs.World;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;

/**
 * Responsible for converting user inputs to kicking the ball at the kick-off.
 */
public class KickOffKickSystem implements UpdateSystem {
    private final Query query;

    public KickOffKickSystem(World world) {
        this.query = world.query(Transform.class, Motion.class);
    }


    /**
     * Receives a KickKickOff command.
     */
    @Override
    public void update(double dt) {
        query.forEach((int ball, Transform transform, Motion motion) -> {
            preformKick(transform, motion);
        });
    }


    public void preformKick(Transform transform, Motion motion){
        return;
    }
}
