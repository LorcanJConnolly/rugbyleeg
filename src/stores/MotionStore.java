package stores;

import components.kinematics.Motion;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class MotionStore extends ComponentStore<Motion> {
    private final Motion[] data;


    public MotionStore(int maxEntities){
        super(maxEntities, Motion.class);
        data = new Motion[maxEntities];
    }

    @Override
    public Class<Motion> getComponentType(){
        return Motion.class;
    }

    @Override
    public void add(int entity, Motion component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public Motion get(int entity) {
        if (!has(entity)) return null;
        return data[sparse[entity]];
    }


    @Override
    public void remove(int entity) {
        if (!has(entity)) return;

        // Move last element into the slot created by removing the entity.
        int removedIndex = removeEntity(entity);
        data[removedIndex] = data[size];    // Size decreased by super.removeEntity().
        data[size] = null;                  // Release removed component reference.
    }


    @Override
    public void forEach(BiConsumer<Integer, Motion> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}
