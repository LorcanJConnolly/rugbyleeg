package stores;

import components.rugby.position.PositionRegistry;
import ecs.stores.ComponentStore;

import java.awt.*;
import java.util.function.BiConsumer;

public class PositionRegistryStore extends ComponentStore<PositionRegistry> {
    private final PositionRegistry[] data;


    public PositionRegistryStore(int maxEntities){
        super(maxEntities, PositionRegistry.class);
        data = new PositionRegistry[maxEntities];
    }

    @Override
    public Class<PositionRegistry> getComponentType(){
        return PositionRegistry.class;
    }

    @Override
    public void add(int entity, PositionRegistry component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public PositionRegistry get(int entity) {
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
    public void forEach(BiConsumer<Integer, PositionRegistry> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}
