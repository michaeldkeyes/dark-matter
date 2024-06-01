package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.darkmatter.ecs.component.RemoveComponent;

public class RemoveSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(RemoveComponent.class).get();

    public RemoveSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final RemoveComponent removeComponent = RemoveComponent.MAPPER.get(entity);

        removeComponent.delay -= deltaTime;

        if (removeComponent.delay <= 0) {
            getEngine().removeEntity(entity);
        }
    }
}
