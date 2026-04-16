package stores;

import components.game.SingletonEntities;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class SingletonEntitiesStore extends ComponentStore<SingletonEntities> {
    private final SingletonEntities[] data;


    public SingletonEntitiesStore(int maxEntities){
        super(maxEntities);
        data = new SingletonEntities[maxEntities];
    }

    @Override
    public Class<SingletonEntities> getComponentType(){
        return SingletonEntities.class;
    }

    @Override
    public void add(int entity, SingletonEntities component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public SingletonEntities get(int entity) {
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
    public void forEach(BiConsumer<Integer, SingletonEntities> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}