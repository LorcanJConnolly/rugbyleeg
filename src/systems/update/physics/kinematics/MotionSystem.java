package systems.update.physics.kinematics;

import components.kinematics.Motion;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import physics.kinematics.MotionRequestXY;
import physics.kinematics.MotionRequestZ;

public class MotionSystem implements UpdateSystem {

    private final Query queryXY;
    private final Query queryZ;

    public MotionSystem(World world){
        this.queryXY = world.query(Motion.class);
        this.queryZ = world.query(ZAxis.class);
    }

    @Override
    public void update(double dt) {
        queryXY.forEach((int entity, Motion motion) -> calculateMotionXY(motion));
        queryZ.forEach((int entity, ZAxis zAxis) -> calculateMotionZ(zAxis));

    }


    @Override
    public void registerListeners(CommandBus bus){}


    @Override
    public void registerSubscriptions(EventBus bus){}

    // TODO: review methods.

    private void calculateMotionXY(Motion motion){
        for (MotionRequestXY request : motion.getRequests()){
            // TODO: apply other parts of the request
            motion.velocity.add(request.impulse());
        }
        // TODO max values via stats.
        motion.clearRequests();

        //
    }


    private void calculateMotionZ(ZAxis zAxis){
        for (MotionRequestZ request : zAxis.getRequests()){
            // TODO: apply other parts of the request
            zAxis.velocity += request.impulse();
        }
        zAxis.clearRequests();
    }
}
