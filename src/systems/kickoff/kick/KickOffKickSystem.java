package systems.kickoff.kick;

import components.kinematics.Motion;
import components.kinematics.ZAxis;
import components.game.SingletonEntities;
import ecs.World;
import ecs.commandbus.CommandBus;
import ecs.commandbus.CommandResult;
import ecs.commandbus.commands.KickBall;
import ecs.eventbus.EventBus;
import ecs.pipelines.update.UpdateSystem;
import util.vectors.Vector2;

/**
 * Responsible for converting a "kick the kick off" command to a kick on the ball.
 */
public class KickOffKickSystem implements UpdateSystem {
    private double velocity;
    private double theta_x;
    private double theta_z;

    private final ZAxis ball_z;
    private final Motion ball_motion;

    public KickOffKickSystem(World world) {
        int ball = world.getSingleton(SingletonEntities.class).getBall();
        this.ball_z = world.getEntityComponent(ball, ZAxis.class);
        this.ball_motion = world.getEntityComponent(ball, Motion.class);
    }


    @Override
    public void update(double dt) {
        // System accepts commands.
    }


    @Override
    public void registerListeners(CommandBus bus){
        bus.register(
            KickBall.class,
            command -> {
                preformKick(command);
                return CommandResult.success();
            }
        );
    }


    @Override
    public void registerSubscriptions(EventBus bus){
        // System accepts commands.
    }


    public void preformKick(KickBall command){
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
