package systems.render.debug;

import components.kinematics.Transform;
import components.pitch.PitchDimensions;
import ecs.World;
import ecs.pipelines.render.RenderSystem;
import ecs.query.Query;
import util.quadtree.QuadTree;

import java.awt.*;

public class QuadTreeDebug implements RenderSystem {
    private final Query query;
    private final PitchDimensions pitchDimensions;

    public QuadTreeDebug(World world) {
        this.query = world.query(Transform.class);
        this.pitchDimensions = world.getSingleton(PitchDimensions.class);
    }


    @Override
    public void render(Graphics2D g2, double dt){
        QuadTree qt = new QuadTree(pitchDimensions.aabb, 1);
        query.forEach((int entity, Transform transform) -> {
            qt.insert(transform.position);
        });
        qt.show(g2);
    }
}