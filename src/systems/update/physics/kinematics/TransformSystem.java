package systems.update.physics.kinematics;

import components.kinematics.Motion;
import components.kinematics.Transform;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import util.vectors.Vector2;

public class TransformSystem implements UpdateSystem {
    private final Query queryTransform;
    private final Query queryZ;


    public TransformSystem(World world){
        this.queryTransform = world.query(Transform.class, Motion.class);
        this.queryZ = world.query(ZAxis.class);
    }


    @Override
    public void update(double dt) {
        queryTransform.forEach((int entity, Transform transform, Motion motion) ->
                calculateTransform(transform, motion, dt));
        queryZ.forEach((int entity, ZAxis zAxis) -> calculateAltitude(zAxis, dt));

    }


    @Override
    public void registerListeners(CommandBus bus){}


    @Override
    public void registerSubscriptions(EventBus bus){}


    public void calculateTransform(Transform transform, Motion motion, double dt){
        transform.position.add(Vector2.scaleTogether(motion.velocity, dt));
    }


    public void calculateAltitude(ZAxis zAxis, double dt){
        zAxis.position += zAxis.velocity * dt;
    }
}
