package ecs.pipelines.render;

import ecs.pipelines.update.UpdateSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderPipeline {
    public final List<RenderSystem> systems = new ArrayList<>();

    public void render(Graphics2D g2, double dt){
        for (RenderSystem system : systems) {
            system.render(g2, dt);
        }
    };

    public <T extends RenderSystem> void add(T system) {
        systems.add(system);
    }
}
