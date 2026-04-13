package ecs.pipelines.update;

import java.util.ArrayList;
import java.util.List;

public class UpdatePipeline {
    public final List<UpdateSystem> systems = new ArrayList<>();


    public void update(double dt){
        for (UpdateSystem system : systems) {
            system.update(dt);
        }
    }

    public <T extends UpdateSystem> void add(T system) {
        systems.add(system);
    }
}
