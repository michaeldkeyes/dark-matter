package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.mygdx.darkmatter.ecs.component.PlayerComponent;
import com.mygdx.darkmatter.ecs.component.RemoveComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;
import com.mygdx.darkmatter.event.GameEventManager;
import com.mygdx.darkmatter.event.GameEventPlayerDeath;
import com.mygdx.darkmatter.event.GameEventType;

public class DamageSystem extends IteratingSystem {

    private static final String TAG = DamageSystem.class.getSimpleName();

    public static final float DAMAGE_AREA_HEIGHT = 2f;
    private static final float DAMAGE_PER_SECOND = 25f;
    private static final float DEATH_EXPLOSION_DURATION = 0.9f;

    private static final Family FAMILY = Family.all(PlayerComponent.class, TransformComponent.class).exclude(RemoveComponent.class).get();

    private final GameEventManager gameEventManager;

    public DamageSystem(final GameEventManager gameEventManager) {
        super(FAMILY);

        this.gameEventManager = gameEventManager;
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);

        if (transformComponent.position.y < DAMAGE_AREA_HEIGHT) {
            float damage = DAMAGE_PER_SECOND * deltaTime;

            if (playerComponent.shield > 0) {
                final float blockAmount = playerComponent.shield;
                playerComponent.shield = Math.max(0, playerComponent.shield - damage);
                damage -= blockAmount;

                if (damage < 0) {
                    return;
                }
            }

            playerComponent.life -= damage;

            if (playerComponent.life <= 0) {
                Gdx.app.debug(TAG, "Player died");
                gameEventManager.dispatchEvent(GameEventType.PLAYER_DEATH, new GameEventPlayerDeath(playerComponent.distance));

                final RemoveComponent removeComponent = getEngine().createComponent(RemoveComponent.class);
                removeComponent.delay = DEATH_EXPLOSION_DURATION;
                entity.add(removeComponent);
            }
        }
    }
}
