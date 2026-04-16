package stores;

import components.rugby.team.Member;
import ecs.stores.ComponentStore;

import java.util.function.BiConsumer;

public class MemberStore extends ComponentStore<Member> {
    private final Member[] data;


    public MemberStore(int maxEntities){
        super(maxEntities);
        data = new Member[maxEntities];
    }

    @Override
    public Class<Member> getComponentType(){
        return Member.class;
    }

    @Override
    public void add(int entity, Member component){
        if (has(entity)){
            data[sparse[entity]] = component;
            return;
        }

        int index = addEntity(entity);
        data[index] = component;
    }


    @Override
    public Member get(int entity) {
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
    public void forEach(BiConsumer<Integer, Member> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(dense[i], data[i]); // The two inputs handed to the consumer.
        }
    }
}