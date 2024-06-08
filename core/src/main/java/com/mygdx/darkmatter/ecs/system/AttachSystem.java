package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.mygdx.darkmatter.ecs.component.AttachComponent;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.RemoveComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

public class AttachSystem extends IteratingSystem implements EntityListener {

    private static final Family FAMILY = Family.all(
        AttachComponent.class,
        TransformComponent.class,
        GraphicComponent.class)
        .exclude(RemoveComponent.class)
        .get();

    public AttachSystem() {
        super(FAMILY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(final Entity entity) {
        // do nothing
    }

    @Override
    public void entityRemoved(final Entity removedEntity) {
        getEntities().forEach(entity -> {
            final AttachComponent attachComponent = AttachComponent.MAPPER.get(entity);

            if (attachComponent.entity == removedEntity) {
                entity.add(getEngine().createComponent(RemoveComponent.class));
                Gdx.app.debug("AttachSystem", "entityRemoved: " + attachComponent.entity);
            }
        });
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final AttachComponent attachComponent = AttachComponent.MAPPER.get(entity);
        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);

        final TransformComponent attachTransformComponent = TransformComponent.MAPPER.get(attachComponent.entity);

        transformComponent.interpolatedPosition.set(
            attachTransformComponent.interpolatedPosition.x + attachComponent.offset.x,
            attachTransformComponent.interpolatedPosition.y + attachComponent.offset.y,
            transformComponent.position.z
        );

        final GraphicComponent attachGraphicComponent = GraphicComponent.MAPPER.get(attachComponent.entity);

        graphicComponent.sprite.setAlpha(attachGraphicComponent.sprite.getColor().a);

    }
}
