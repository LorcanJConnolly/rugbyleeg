package systems.render.pitch;

import components.pitch.PitchDimensions;
import ecs.World;
import ecs.pipelines.render.RenderSystem;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PitchRender implements RenderSystem {
    private final PitchDimensions pitchDimensions;

    public PitchRender(World world) {
        this.pitchDimensions = world.getSingleton(PitchDimensions.class);
    }


    @Override
    public void render(Graphics2D g2, double dt){
        g2.setColor(new Color(26f, 77f, 15f));

        g2.draw(
            new Rectangle2D.Double(
                pitchDimensions.aabb.origin.x,
                pitchDimensions.aabb.origin.y,
                pitchDimensions.aabb.width,
                pitchDimensions.aabb.height
            )
        );

        g2.setColor(new Color(49f, 143f, 29f));
        g2.draw(
                new Rectangle2D.Double(
                        pitchDimensions.topInGoal.origin.x,
                        pitchDimensions.topInGoal.origin.y,
                        pitchDimensions.topInGoal.width,
                        pitchDimensions.topInGoal.height
                )
        );

        g2.draw(
                new Rectangle2D.Double(
                        pitchDimensions.bottomInGoal.origin.x,
                        pitchDimensions.bottomInGoal.origin.y,
                        pitchDimensions.bottomInGoal.width,
                        pitchDimensions.bottomInGoal.height
                )
        );

    }
}
