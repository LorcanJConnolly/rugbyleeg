package systems.render.kinematic;

import components.kinematics.Transform;
import ecs.World;
import ecs.pipelines.render.RenderSystem;
import ecs.query.Query;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TransformRender implements RenderSystem {
    private final Query query;

    public TransformRender(World world) {
        this.query = world.query(Transform.class);
    }


    @Override
    public void render(Graphics2D g2, double dt){
        query.forEach((int entity,  Transform transform) -> {
            g2.setColor(Color.WHITE);
            drawTransform(g2, transform);
        });
    }


    public void drawTransform(Graphics2D g2, Transform transform){
        double height = 2;
        double width = 2;

        g2.draw(
            new Ellipse2D.Double(
                (transform.position.x + width/2),
                (transform.position.y + height/2),
                width,
                height
            )
        );
    }
}
