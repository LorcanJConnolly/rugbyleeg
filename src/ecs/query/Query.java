package ecs.query;

import ecs.Component;
import ecs.consumers.BiEntityConsumer;
import ecs.consumers.EntityConsumer;
import ecs.consumers.QuadEntityConsumer;
import ecs.consumers.TriEntityConsumer;
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

    // forEach methods
    @SuppressWarnings("unchecked")
    public <A extends Component> void forEach(EntityConsumer<A> consumer){
        for (int entity: results){
            consumer.accept(entity, (A) stores[0].get(entity));
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends Component, B extends Component> void forEach(BiEntityConsumer<A, B> consumer){
        for (int entity: results){
            consumer.accept(entity, (A) stores[0].get(entity), (B) stores[1].get(entity));
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends Component, B extends Component, C extends Component>
    void forEach(TriEntityConsumer<A, B, C> consumer){
        for (int entity: results){
            consumer.accept(
                    entity,
                    (A) stores[0].get(entity),
                    (B) stores[1].get(entity),
                    (C) stores[2].get(entity)
            );
        }
    }

    @SuppressWarnings("unchecked")
    public <A extends Component, B extends Component, C extends Component, D extends Component>
    void forEach(QuadEntityConsumer<A, B, C, D> consumer){
        for (int entity: results){
            consumer.accept(
                    entity,
                    (A) stores[0].get(entity),
                    (B) stores[1].get(entity),
                    (C) stores[2].get(entity),
                    (D) stores[3].get(entity)
            );
        }
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