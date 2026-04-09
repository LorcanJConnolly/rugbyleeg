package ecs;

import ecs.entities.EntityManager;
import ecs.pipelines.commands.CommandPipeline;
import ecs.eventbus.EventBus;
import ecs.pipelines.render.RenderPipeline;
import ecs.pipelines.render.RenderSystem;
import ecs.pipelines.update.UpdatePipeline;
import ecs.pipelines.update.UpdateSystem;
import ecs.query.Query;
import ecs.query.QueryManager;
import ecs.stores.ComponentStore;
import ecs.stores.ComponentStoreManager;

import java.awt.*;

public class World {
    private final int MAX_ENTITES;
//    private final int MAX_COMPONENTS;
//    private final int MAX_SYSTEMS;

    private final UpdatePipeline updatePipeline = new UpdatePipeline();
    private final RenderPipeline renderPipeline = new RenderPipeline();
    private final EventBus eventBus = new EventBus();
    private final CommandPipeline commandPipeline = new CommandPipeline();

    private final EntityManager entityManager;
    private final ComponentStoreManager storeManager = new ComponentStoreManager();
    private final QueryManager queryManager = new QueryManager();


    public World(int maxEntities){
        this.MAX_ENTITES = maxEntities;
        this.entityManager = new EntityManager(MAX_ENTITES);
    }


    // Entities
    public int createEntity(){
        return entityManager.getId();
    }

    public void destroyEntity(int entity){
        storeManager.onEntityDestroyed(entity);
        queryManager.onEntityDestroyed(entity);
    }


    // Systems
    public <T extends UpdateSystem> void addSystem(T system) {
        updatePipeline.add(system);
    }


    public <T extends RenderSystem> void addSystem(T system) {
        renderPipeline.add(system);
    }


    // Components
    public <T extends Component> void addComponent(int entity, T component) {
        storeManager.addComponent(entity, component);
        queryManager.onComponentAdded(entity,  storeManager.getStore(component.getClass()));
    }


    public <T extends Component> void removeComponent(int entity, Class<T> componentType) {
        storeManager.removeComponent(entity, componentType);
        queryManager.onComponentRemoved(entity, storeManager.getStore(componentType));
    }


    //Stores
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

    public int getSingletonEntity(Class<? extends Component> componentType){
        ComponentStore<? extends Component> store = storeManager.getStore(componentType);
        if (store.size() != 1){
            throw new IllegalStateException(
                    "Expected exactly one entity in singleton '" + componentType.getSimpleName()
                            + "', found: " + store.size() + "."
            );
        }
        return store.entityAtDenseIndex(0);
    }


    @SafeVarargs
    public final Query query(Class<? extends Component>... componentTypes){
        ComponentStore<? extends Component>[] stores = new ComponentStore[componentTypes.length];
        for (int i = 0; i < componentTypes.length; i++) {
            stores[i] = storeManager.getStore(componentTypes[i]);
        }
        return queryManager.getOrCreateQuery(stores);
    }


    // Pipelines
    public void update(double dt) {updatePipeline.update(dt);}


    public void render(Graphics2D g2, double dt) {
        renderPipeline.render(g2, dt);
    }
}
