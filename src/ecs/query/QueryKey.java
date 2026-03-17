package ecs.query;

import ecs.Component;
import ecs.stores.ComponentStore;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

// Accepts a number of component stores, puts them in a set and implements eq and hash methods.
public class QueryKey {

    private final Set<ComponentStore<? extends Component>> stores;
    private final int hash;

    public Set<ComponentStore<? extends Component>> getStores(){
        return stores;
    }

    @SafeVarargs
    public QueryKey(ComponentStore<? extends Component>... componentStores) {
        this.stores = Set.copyOf(Arrays.asList(componentStores)); // Defensive copy.
        this.hash = Objects.hash(stores);
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof QueryKey other)) return false;
        return Objects.equals(this.stores, other.stores);
    }

    @Override
    public int hashCode(){
        return hash;
    }
}