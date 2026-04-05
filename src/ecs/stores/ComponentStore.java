package ecs.stores;


import ecs.Component;

import java.util.function.BiConsumer;

/**
 * TODO
 * @param <T>
 */
public abstract class ComponentStore<T extends Component> {

    protected final int[] sparse;       // entity ID -> dense index
    protected final int[] dense;        // dense index -> entity ID
    protected int size = 0;

    protected ComponentStore(int maxEntities){
        sparse = new int[maxEntities];
        dense = new int[maxEntities];
    }

    public final boolean has(int entity){
        int index = sparse[entity];
        return index < size && dense[index] == entity;
    }


    public final int size(){
        return size;
    }


    public final int entityAtDenseIndex(int index) {
        return dense[index];
    }

    public final int componentAt(int index) {
        return sparse[index];
    }

    protected final int addEntity(int entity) {
        sparse[entity] = size;
        dense[size] = entity;
        return size++;
    }

    protected final int removeEntity(int entity) {
        int index = sparse[entity];
        int lastEntity = dense[size - 1];

        dense[index] = lastEntity;
        sparse[lastEntity] = index;
        size--;
        return index; // index of removed data (gap to be filled in sparse).
    }

    // Component-typed operations implemented by child classes.

    public abstract void add(int entity, T component);


    public abstract T get(int entity);


    public abstract void remove(int entity);


    public abstract void forEach(BiConsumer<Integer, T> consumer);

}

