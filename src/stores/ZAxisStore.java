package stores;

import components.kinematics.ZAxis;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class ZAxisStore extends ComponentStore<ZAxis> {
    private final ZAxis[] data;


    public ZAxisStore(int maxEntities){
        super(maxEntities, ZAxis.class);
        data = new ZAxis[maxEntities];
    }

    @Override
    public Class<ZAxis> getComponentType(){
        return ZAxis.class;
    }

    @Override
    public void add(int entity, ZAxis component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public ZAxis get(int entity) {
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
    public void forEach(BiConsumer<Integer, ZAxis> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}