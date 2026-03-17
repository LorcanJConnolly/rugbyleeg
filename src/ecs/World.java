package ecs;

import ecs.entities.EntityManager;
import ecs.pipelines.RenderPipeline;
import ecs.pipelines.UpdatePipeline;
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
    private final EventPipeline eventPipeline;
    private final CommandPipeline commandPipeline;

    private final EntityManager entityManager;
    private final ComponentStoreManager storeManager = new ComponentStoreManager();
    private final QueryManager queryManager = new QueryManager();


    public World(int maxEntities){
        this.MAX_ENTITES = maxEntities;
        this.entityManager = new EntityManager(MAX_ENTITES);
    }


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


    // add system x2
    public <T extends UpdateSystem> void addSystem(T system) {
        updatePipeline.add(system);
    }


    public <T extends RenderSystem> void addSystem(T system) {
        renderPipeline.add(system);
    }


    public <T extends Component> void registerStore(Class<T> componentType, ComponentStore<T> store) {
        storeManager.register(componentType, store);
    }


    public void update(double dt) {
        updatePipeline.update(dt);
    }


    public void render(Graphics2D g2, double dt) {
        renderPipeline.render(g2, dt);
    }


    // components
    public <T extends Component> void addComponent(int entity, Class<T> componentType, T component) {
        storeManager.addComponent(entity, componentType, component);
        queryManager.onComponentAdded(entity,  storeManager.getStore(componentType));
    }

    public <T extends Component> void removeComponent(int entity, Class<T> componentType) {
        storeManager.removeComponent(entity, componentType);
        queryManager.onComponentRemoved(entity, storeManager.getStore(componentType));
    }


    @SafeVarargs
    public final Query query(ComponentStore<? extends Component>... stores){
        return queryManager.getOrCreateQuery(stores);
    }
}
