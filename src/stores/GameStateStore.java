package stores;

import components.game.GameState;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class GameStateStore extends ComponentStore<GameState> {
    private final GameState[] data;


    public GameStateStore(int maxEntities){
        super(maxEntities, GameState.class);
        data = new GameState[maxEntities];
    }

    @Override
    public Class<GameState> getComponentType(){
        return GameState.class;
    }

    @Override
    public void add(int entity, GameState component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public GameState get(int entity) {
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
    public void forEach(BiConsumer<Integer, GameState> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}