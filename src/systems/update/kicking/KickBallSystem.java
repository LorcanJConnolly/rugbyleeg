package systems.update.kicking;

import components.game.SingletonEntities;
import components.kinematics.Motion;
import components.kinematics.ZAxis;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import util.vectors.Vector2;

/**
 * A system for setting the ball's velocity data after it has been kicked.
 */
public class KickBallSystem implements UpdateSystem {
    private double velocity;
    private double theta_x;
    private double theta_z;

    private final ZAxis ball_z;
    private final Motion ball_motion;

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
     * Sets ball's x, y, and z velocity components based off a kick command.
     * @param command
     */
    public void preformKick(ecs.commandbus.commands.KickBall command){
        this.velocity = command.velocity;
        this.theta_x = command.theta_x;
        this.theta_z = command.theta_z;

        // Calculate v_z and v_xy from v
        double v_z = velocity*Math.cos(Math.toRadians(theta_z));
        double v_xy = velocity*Math.sin(Math.toRadians(theta_z));

        ball_motion.velocity = new Vector2(
                v_xy*Math.cos(Math.toRadians(theta_x)),
                v_xy*Math.sin(Math.toRadians(theta_x))
        );
        ball_z.velocity = v_z;
    }
}
