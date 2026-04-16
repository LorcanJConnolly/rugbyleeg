package systems.render.kinematic;

import components.kinematics.Transform;
import components.rugby.position.RugbyPosition;
import ecs.World;
import ecs.pipelines.render.RenderSystem;
import ecs.query.Query;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TransformRender implements RenderSystem {
    private final Query query;
    private final World world;

    public TransformRender(World world) {
        this.world = world;
        this.query = world.query(Transform.class);
    }


    @Override
    public void render(Graphics2D g2, double dt){
        query.forEach((int entity,  Transform transform) -> {
            g2.setColor(Color.WHITE);
            drawTransform(g2,entity, transform);
        });
    }


    public void drawTransform(Graphics2D g2, int entity, Transform transform){
        double height = 4;
        double width = 4;

        g2.draw(
            new Ellipse2D.Double(
                (transform.position.x + width/2),
                (transform.position.y + height/2),
                width,
                height
            )
        );

        RugbyPosition rugbyPosition = world.getEntityComponent(entity, RugbyPosition.class);
        if (rugbyPosition == null) return;
        g2.drawString("" + rugbyPosition.getNumber(), (float) transform.position.x, (float) transform.position.y);
    }
}
