package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem {

    private static final Family FAMILY = Family.all(TransformComponent.class, GraphicComponent.class).get();
    private static final String TAG = RenderSystem.class.getSimpleName();

    private final SpriteBatch batch;
    private final Viewport viewport;

    public RenderSystem(final SpriteBatch batch, final Viewport viewport) {
        super(FAMILY, new ZComparator());

        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void update(final float deltaTime) {
        forceSort();
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);

        if (graphicComponent.sprite == null) {
            Gdx.app.error(TAG, "Entity: " + entity + " has no sprite");
            return;
        }

        graphicComponent.sprite.setRotation(transformComponent.rotation);
        graphicComponent.sprite.setBounds(
            transformComponent.interpolatedPosition.x,
            transformComponent.interpolatedPosition.y,
            transformComponent.size.x,
            transformComponent.size.y
        );
        graphicComponent.sprite.draw(batch);
    }

    private static class ZComparator implements Comparator<Entity> {

        @Override
        public int compare(final Entity entityA, final Entity entityB) {
            final TransformComponent transformComponentA = TransformComponent.MAPPER.get(entityA);
            final TransformComponent transformComponentB = TransformComponent.MAPPER.get(entityB);

            final int zDiff =
                (int) Math.signum(transformComponentA.position.z - transformComponentB.position.z);

            if (zDiff == 0) {
                return (int) Math.signum(transformComponentA.position.y - transformComponentB.position.y);
            } else {
                return zDiff;
            }
        }
    }
}
