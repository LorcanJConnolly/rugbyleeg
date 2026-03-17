package ecs.query;

import ecs.Component;
import ecs.stores.ComponentStore;

import java.util.HashMap;
import java.util.Map;

public class QueryManager {

    private final Map<QueryKey, Query> queries = new HashMap<>();

    @SafeVarargs
    public final Query getOrCreateQuery(ComponentStore<? extends Component>... stores){
        QueryKey key = new QueryKey();
        Query query = queries.get(key);
        if (query != null){
            return query;
        } else {
            Query newQuery = new Query(stores);
            queries.put(key, newQuery);
            return newQuery;
        }
    }

    public void onComponentAdded(int entity, ComponentStore<? extends Component> store){
        for (QueryKey query_key : queries.keySet()){
            if (query_key.getStores().contains(store)){
                queries.get(query_key).onComponentAdded(entity, store);
            }
        }
    }

    public void onComponentRemoved(int entity, ComponentStore<? extends Component> store){
        for (QueryKey query_key : queries.keySet()){
            if (query_key.getStores().contains(store)){
                queries.get(query_key).onComponentRemoved(entity, store);
            }
        }
    }

    public void onEntityDestroyed(int entity){
        for (Query query : queries.values()){
            if (query.getResults().contains(entity)){
                query.onEntityDestroyed(entity);
            }
        }
    }
}
