package ecs.query;

import ecs.Component;
import ecs.stores.ComponentStore;

import java.util.HashSet;
import java.util.Set;

public class Query {

    private final ComponentStore<? extends Component>[] stores;
    private final Set<Integer> results = new HashSet<>();

    public Query(ComponentStore<? extends Component>... stores){
        this.stores = stores;
        query(); // Collect results
    }


    public Set<Integer> getResults(){
        return results;
    }

    private void query(){
        // Preform search on smallest store first.
        ComponentStore<? extends Component> smallest = stores[0];
        for (ComponentStore<? extends Component> s : stores) {
            if (s.size() < smallest.size()) {
                smallest = s;
            }
        }
        for (int i = 0; i < smallest.size(); i++) {
            int entity = smallest.entityAtDenseIndex(i);
            if (matches(entity)) {
                results.add(entity);
            }
        }
    }


    private boolean matches(int entity){
        for (ComponentStore<? extends Component> store: stores){
            if (!store.has(entity)) return false;
        }
        return true;
    }


    public int size(){
        return results.size();
    }


    public void onComponentAdded(int entity, ComponentStore<? extends Component> store){
        if (!results.contains(entity) && matches(entity)) {
            results.add(entity);
        }
    }

    public void onComponentRemoved(int entity, ComponentStore<? extends Component> store){
        for (ComponentStore<? extends Component> s : stores) {
            if (s == store) {
                results.remove(entity);
                return;
            }
        }
    }

    public void onEntityDestroyed(int entity){
        results.remove(entity);
    }
}