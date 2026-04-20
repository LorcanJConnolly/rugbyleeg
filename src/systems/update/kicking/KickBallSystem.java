package systems.update.kicking;

import components.game.SingletonEntities;
import components.kinematics.Motion;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.KickBall;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import physics.kinematics.MotionRequestXY;
import physics.kinematics.MotionRequestZ;
import util.vectors.Vector2;

/**
 * A system for setting the ball's velocity data after it has been kicked.
 */
public class KickBallSystem implements UpdateSystem {
    private final Motion ball_motion;
    private final ZAxis ball_z;

    public KickBallSystem(World world) {
        int ball = world.getSingleton(SingletonEntities.class).getBall();
        this.ball_z = world.getEntityComponent(ball, ZAxis.class);
        this.ball_motion = world.getEntityComponent(ball, Motion.class);
    }


    @Override
    public void update(double dt) {}


    @Override
    public void registerListeners(CommandBus bus){
        bus.register(
                ecs.commandbus.commands.KickBall.class,
                command -> {
                    preformKick(command);
                    return CommandResult.success();
                }
        );
    }


    @Override
    public void registerSubscriptions(EventBus bus){}

    /**
     * Creates a request for motion to the XY and Z axis based on the kick command.
     * @param command: The commanded kick.
     */
    public void preformKick(KickBall command){

        System.out.println("KICKING! " + command.velocity + ", " + command.theta_x + ", " + command.theta_z);

        // Calculate v_z and v_xy from v
        double v_z = command.velocity * Math.cos(Math.toRadians(command.theta_z));
        double v_xy = command.velocity * Math.sin(Math.toRadians(command.theta_z));

        Vector2 velocity = new Vector2(
                v_xy * Math.cos(Math.toRadians(command.theta_x)),
                v_xy * Math.sin(Math.toRadians(command.theta_x))
        );

        ball_motion.addRequest(
            new MotionRequestXY(
                velocity,
                new Vector2(0d, 0d),
                new Vector2(0d, 0d),
                0d,
                0d,
                0d
        ));

        ball_z.addRequest(
                new MotionRequestZ(
                        v_z,
                        0d,
                        0d,
                        0d,
                        0d,
                        0d
                )
        );
    }
}
