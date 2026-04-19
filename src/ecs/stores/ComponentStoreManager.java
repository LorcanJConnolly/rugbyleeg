package ecs.stores;
import ecs.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the IDs of component stores in the world.
 */
public class ComponentStoreManager {

    private final Map<Class<? extends Component>, ComponentStore<? extends Component>> stores = new HashMap<>();

    public <T extends Component> void register(ComponentStore<T> store) {
        stores.put(store.getComponentType(), store);
    }

    public <T extends Component> boolean entityHas(int entity, Class<T> componentType) {
        return stores.get(componentType).has(entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> ComponentStore<T> getStore(Class<T> componentType) {
        if (!stores.containsKey(componentType)) {
            throw new RuntimeException("No store exists for '" + componentType + "'.");
        }
        return (ComponentStore<T>) stores.get(componentType);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(int entity, Class<T> componentType) {
        ComponentStore<T> store = (ComponentStore<T>) stores.get(componentType);
        if (store == null) return null;
        return store.get(entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> void addComponent(int entity, T component) {
        ComponentStore<T> store = (ComponentStore<T>) stores.get(component.getClass());
        if (store == null) return;
        store.add(entity, component);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> void removeComponent(int entity, Class<T> componentType) {
        ComponentStore<T> store = (ComponentStore<T>) stores.get(componentType);
        if (store == null) return;
        store.remove(entity);
    }

    public void onEntityDestroyed(int entity){
        for (ComponentStore<? extends Component> store : stores.values()){
            store.remove(entity);
        }
    }
}


