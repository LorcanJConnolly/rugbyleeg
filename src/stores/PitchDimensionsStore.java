package stores;

import components.pitch.PitchDimensions;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class PitchDimensionsStore extends ComponentStore<PitchDimensions> {private final PitchDimensions[] data;

    public PitchDimensionsStore(int maxEntities){
        super(maxEntities);
        data = new PitchDimensions[maxEntities];
    }


    @Override
    public Class<PitchDimensions> getComponentType(){
        return PitchDimensions.class;
    }


    @Override
    public void add(int entity, PitchDimensions component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public PitchDimensions get(int entity) {
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
    public void forEach(BiConsumer<Integer, PitchDimensions> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}