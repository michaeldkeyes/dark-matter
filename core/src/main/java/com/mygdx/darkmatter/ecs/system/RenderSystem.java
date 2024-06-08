package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.PowerUpComponent;
import com.mygdx.darkmatter.ecs.component.RemoveComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;
import com.mygdx.darkmatter.event.*;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem implements GameEventListener {

    private static final Family FAMILY =
        Family.all(TransformComponent.class, GraphicComponent.class).exclude(RemoveComponent.class).get();
    private static final String TAG = RenderSystem.class.getSimpleName();

    private final GameEventManager gameEventManager;
    private final SpriteBatch batch;
    private final Sprite background;
    private final Viewport uiViewport;
    private final Viewport viewport;

    private Vector2 backgroundScrollSpeed = new Vector2(0.03f, -0.25f);

    public RenderSystem(final SpriteBatch batch, final Viewport viewport,
                        final Viewport uiViewport, final Texture backgroundTexture, final GameEventManager gameEventManager) {
        super(FAMILY, new ZComparator());

        this.gameEventManager = gameEventManager;
        this.batch = batch;

        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background = new Sprite(backgroundTexture);

        this.viewport = viewport;
        this.uiViewport = uiViewport;
    }

    @Override
    public void addedToEngine(final com.badlogic.ashley.core.Engine engine) {
        super.addedToEngine(engine);
        gameEventManager.addListener(GameEventType.COLLECT_POWER_UP, this);
    }

    @Override
    public void removedFromEngine(final com.badlogic.ashley.core.Engine engine) {
        super.removedFromEngine(engine);
        gameEventManager.removeListener(GameEventType.COLLECT_POWER_UP, this);
    }

    @Override
    public void update(final float deltaTime) {
        ScreenUtils.clear(0, 0, 0, 1);

        uiViewport.apply();
        batch.setProjectionMatrix(uiViewport.getCamera().combined);
        batch.begin();

        backgroundScrollSpeed.y = Math.min(-0.25f, backgroundScrollSpeed.y + deltaTime * (1 / 10f));
        background.scroll(
            backgroundScrollSpeed.x * deltaTime,
            backgroundScrollSpeed.y * deltaTime
        );
        background.draw(batch);

        batch.end();

        forceSort();

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

    @Override
    public void onEvent(final GameEventType eventType, final GameEvent data) {
        if (eventType == GameEventType.COLLECT_POWER_UP) {
            Gdx.app.debug(TAG, "Power up collected: " + data);
            final GameEventCollectPowerUp collectPowerUpEvent = (GameEventCollectPowerUp) data;

            if (collectPowerUpEvent.getPowerUpType().equals(PowerUpComponent.PowerUpType.SPEED_1)) {
                Gdx.app.debug(TAG, "Player collected speed 1 power up");
                backgroundScrollSpeed.y -= -0.25f;

            } else if (collectPowerUpEvent.getPowerUpType().equals(PowerUpComponent.PowerUpType.SPEED_2)) {
                Gdx.app.debug(TAG, "Player collected speed 2 power up");
                backgroundScrollSpeed.y -= -0.5f;
            }
        }
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
