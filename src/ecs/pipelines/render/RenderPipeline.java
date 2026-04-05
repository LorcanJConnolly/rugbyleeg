package ecs.pipelines.render;

import java.awt.*;

public class RenderPipeline {
    public void render(Graphics2D g2, double dt){
        // for system in pipeline, render()
        return;
    };

    public <T extends RenderSystem> void add(T system) {
        return;
    }
}
