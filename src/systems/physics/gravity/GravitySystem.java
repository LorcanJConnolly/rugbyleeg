package systems.physics.gravity;

import components.kinematics.Motion;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import util.vectors.Vector2;

/**
 * A system for applying "gravity" to entities in a 2.5D manor.
 */
public class GravitySystem implements UpdateSystem {
    private final Query query;
    private final double gravity = 9.81;
    // TODO: Remove entity input when bouncing is implemented in collision system.
    private final World world;

    public GravitySystem(World world) {
        this.query = world.query(ZAxis.class);
        this.world = world;
    }


    @Override
    public void update(double dt) {
        query.forEach((int entity, ZAxis zaxis) -> {
            applyGravity(entity, dt, zaxis);
        });
    }


    @Override
    public void registerListeners(CommandBus bus) {}


    @Override
    public void registerSubscriptions(EventBus bus) {}


    // TODO: Remove entity input when bouncing is implemented in collision system.
    private void applyGravity(int entity, double dt, ZAxis zaxis){
        if (zaxis.position <= 0 && zaxis.velocity <= 0) return;

        zaxis.velocity += -gravity * dt;
        zaxis.position += zaxis.velocity * dt;

        // Entity has hit the ground after falling.
        if (zaxis.position < 0){
            zaxis.velocity = 0d;
            zaxis.position = 0d;

            // TODO: Remove this when bouncing is implemented in collision system.
            Motion motion = world.getEntityComponent(entity, Motion.class);
            motion.velocity = new Vector2(0d, 0d);

        }
    }

}
