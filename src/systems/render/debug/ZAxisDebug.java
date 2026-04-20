package systems.render.debug;

import components.kinematics.Transform;
import components.kinematics.ZAxis;
import components.rugby.position.RugbyPosition;
import ecs.World;
import ecs.pipelines.render.RenderSystem;
import ecs.query.Query;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ZAxisDebug implements RenderSystem {
    private final Query query;
    private final World world;

    public ZAxisDebug(World world) {
        this.world = world;
        this.query = world.query(ZAxis.class, Transform.class);
    }


    @Override
    public void render(Graphics2D g2, double dt){
        query.forEach((int entity, ZAxis zAxis, Transform transform) -> {
            g2.setColor(Color.RED);
            writeZAxis(g2, zAxis, transform);
        });
    }


    public void writeZAxis(Graphics2D g2, ZAxis zAxis, Transform transform){
        g2.drawString("Alitude: '" + zAxis.position + "'", (float) transform.position.x, (float) transform.position.y);
    }

}
