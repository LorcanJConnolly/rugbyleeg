package stores;

import components.player.kinematics.Transform;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class TransformStore extends ComponentStore<Transform> {
    private final Transform[] data;


    public TransformStore(int maxEntities){
        super(maxEntities);
        data = new Transform[maxEntities];
    }


    @Override
    public Class<Transform> getComponentType(){
        return Transform.class;
    }


    @Override
    public void add(int entity, Transform component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public Transform get(int entity) {
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
    public void forEach(BiConsumer<Integer, Transform> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}
