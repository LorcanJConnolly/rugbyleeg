package ecs.entities;

public class EntityManager {
    private final int MAX_ENTITES;
    private int nextId = 0;

    public EntityManager(int maxEntities){
        this.MAX_ENTITES = maxEntities;
    }

    public int getId(){
        return nextId++;
    }


}
