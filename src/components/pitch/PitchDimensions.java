package components.pitch;

import ecs.Component;
import util.shapes.AABB;
import util.vectors.Vector2;

public class PitchDimensions implements Component {
    // Entire pitch (including in goal areas)
    public AABB aabb;
    // In play area
    public double leftTouch;
    public double rightTouch;
    public double topTryLine;
    public double bottomTryLine;
    // In goal areas
    public AABB topInGoal;
    public AABB bottomInGoal;
    public double topDeadBall;
    public double bottomDeadBall;
}
