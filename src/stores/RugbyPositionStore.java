package stores;

import components.rugby.position.RugbyPosition;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class RugbyPositionStore extends ComponentStore<RugbyPosition> {private final RugbyPosition[] data;

    public RugbyPositionStore(int maxEntities){
        super(maxEntities, RugbyPosition.class);
        data = new RugbyPosition[maxEntities];
    }


    @Override
    public Class<RugbyPosition> getComponentType(){
        return RugbyPosition.class;
    }


    @Override
    public void add(int entity, RugbyPosition component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public RugbyPosition get(int entity) {
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
    public void forEach(BiConsumer<Integer, RugbyPosition> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}