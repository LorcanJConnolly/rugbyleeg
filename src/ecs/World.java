package ecs;

import ecs.entities.EntityManager;
import ecs.pipelines.commands.CommandPipeline;
import ecs.pipelines.events.EventPipeline;
import ecs.pipelines.render.RenderPipeline;
import ecs.pipelines.render.RenderSystem;
import ecs.pipelines.update.UpdatePipeline;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import ecs.query.QueryManager;
import ecs.stores.ComponentStore;
import ecs.stores.ComponentStoreManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private final int MAX_ENTITES;
//    private final int MAX_COMPONENTS;
//    private final int MAX_SYSTEMS;

    private final UpdatePipeline updatePipeline = new UpdatePipeline();
    private final RenderPipeline renderPipeline = new RenderPipeline();
    private final EventPipeline eventPipeline = new EventPipeline();
    private final CommandPipeline commandPipeline = new CommandPipeline();

    private final EntityManager entityManager;
    private final ComponentStoreManager storeManager = new ComponentStoreManager();
    private final QueryManager queryManager = new QueryManager();


    public World(int maxEntities){
        this.MAX_ENTITES = maxEntities;
        this.entityManager = new EntityManager(MAX_ENTITES);
    }


    // Builders
    public void createPlayer(){
        entityManager.getId();
    }


    public void createBall(){
        entityManager.getId();
    }


    public void createPitch(){
        entityManager.getId();
    }


    public void createFormation(){
        entityManager.getId();
    }


    public void createMove(){
        entityManager.getId();
    }


    public void destroyEntity(int entity){
        storeManager.onEntityDestroyed(entity);
        queryManager.onEntityDestroyed(entity);
    }


    public <T extends UpdateSystem> void addSystem(T system) {
        updatePipeline.add(system);
    }


    public <T extends RenderSystem> void addSystem(T system) {
        renderPipeline.add(system);
    }


    public <T extends Component> void addComponent(int entity, Class<T> componentType, T component) {
        storeManager.addComponent(entity, componentType, component);
        queryManager.onComponentAdded(entity,  storeManager.getStore(componentType));
    }


    public <T extends Component> void removeComponent(int entity, Class<T> componentType) {
        storeManager.removeComponent(entity, componentType);
        queryManager.onComponentRemoved(entity, storeManager.getStore(componentType));
    }


    public <T extends Component> void registerStore(Class<T> componentType, ComponentStore<T> store) {
        storeManager.register(componentType, store);
    }


    public <T extends Component> T getSingleton(Class<T> componentType){
        ComponentStore<T> store = storeManager.getStore(componentType);
        if (store.size() != 1){
            throw new IllegalStateException(
                    "Expected exactly one entity in singleton '" + componentType.getSimpleName()
                            + "', found: " + store.size() + "."
            );
        }
        return store.get(0);
    }


    @SafeVarargs
    public final Query query(Class<? extends Component>... componentTypes){
        ComponentStore<? extends Component>[] stores = new ComponentStore[componentTypes.length];
        for (int i = 0; i < componentTypes.length; i++) {
            stores[i] = storeManager.getStore(componentTypes[i]);
        }
        return queryManager.getOrCreateQuery(stores);
    }


    public void update(double dt) {updatePipeline.update(dt);}


    public void render(Graphics2D g2, double dt) {
        renderPipeline.render(g2, dt);
    }
}
