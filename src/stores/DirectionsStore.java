package stores;

import components.direction.Directions;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class DirectionsStore extends ComponentStore<Directions> {
    private final Directions[] data;


    public DirectionsStore(int maxEntities){
        super(maxEntities);
        data = new Directions[maxEntities];
    }

    @Override
    public Class<Directions> getComponentType(){
        return Directions.class;
    }

    @Override
    public void add(int entity, Directions component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public Directions get(int entity) {
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
    public void forEach(BiConsumer<Integer, Directions> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}