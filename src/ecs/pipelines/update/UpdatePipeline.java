package ecs.pipelines.update;

public class UpdatePipeline {

    public void update(double dt){
        // for system in pipeline, update()
        return;
    }

    public <T extends UpdateSystem> void add(T system) {
        return;
    }
}
