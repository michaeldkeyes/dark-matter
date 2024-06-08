package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.darkmatter.ecs.component.*;
import com.mygdx.darkmatter.event.GameEventCollectPowerUp;
import com.mygdx.darkmatter.event.GameEventManager;
import com.mygdx.darkmatter.event.GameEventType;

import static com.mygdx.darkmatter.DarkMatter.WORLD_HEIGHT;
import static com.mygdx.darkmatter.DarkMatter.WORLD_WIDTH;

public class PowerUpSystem extends IteratingSystem {

    private static final String TAG = PowerUpSystem.class.getSimpleName();

    private static final float MAX_SPAWN_INTERVAL = 1.5f;
    private static final float MIN_SPAWN_INTERVAL = 0.9f;
    private static final float POWER_UP_SPEED = -8.75f;
    private static final float BOOST_1_SPEED_GAIN = 3f;
    private static final float BOOST_2_SPEED_GAIN = 3.75f;
    private static final float LIFE_GAIN = 25f;
    private static final float SHIELD_GAIN = 25f;

    private final GameEventManager gameEventManager;
    private final Engine engine;
    private final Rectangle playerBounds;
    private final Rectangle powerUpBounds;
    private final ImmutableArray<Entity> players;

    private float spawnTime = 0f;
    private Array<SpawnPattern> spawnPatterns;
    private Array<PowerUpComponent.PowerUpType> currentPattern;

    private static final Family FAMILY = Family.all(
            PowerUpComponent.class,
            TransformComponent.class
        ).exclude(RemoveComponent.class)
        .get();

    public PowerUpSystem(final Engine engine, final GameEventManager gameEventManager) {
        super(FAMILY);

        this.engine = engine;
        this.gameEventManager = gameEventManager;

        playerBounds = new Rectangle();
        powerUpBounds = new Rectangle();

        spawnPatterns = new Array<>();
        currentPattern = new Array<>();

        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).exclude(RemoveComponent.class).get());

        spawnPatterns.add(new SpawnPattern(
            PowerUpComponent.PowerUpType.SPEED_1,
            PowerUpComponent.PowerUpType.SPEED_2,
            PowerUpComponent.PowerUpType.NONE,
            PowerUpComponent.PowerUpType.NONE,
            PowerUpComponent.PowerUpType.LIFE
        ));

        spawnPatterns.add(new SpawnPattern(
            PowerUpComponent.PowerUpType.NONE,
            PowerUpComponent.PowerUpType.LIFE,
            PowerUpComponent.PowerUpType.SHIELD,
            PowerUpComponent.PowerUpType.SPEED_2,
            PowerUpComponent.PowerUpType.NONE
        ));
    }

    @Override
    public void update(final float deltaTime) {
        super.update(deltaTime);

        spawnTime -= deltaTime;

        if (spawnTime <= 0) {
            spawnTime = MathUtils.random(MIN_SPAWN_INTERVAL, MAX_SPAWN_INTERVAL);

            if (currentPattern.isEmpty()) {
                currentPattern.addAll(spawnPatterns.random().types);
                //Gdx.app.debug(TAG, "New pattern: " + currentPattern);
            }

            final PowerUpComponent.PowerUpType type = currentPattern.removeIndex(0);

            if (type == PowerUpComponent.PowerUpType.NONE) {
                return;
            }

            spawnPowerUp(type, MathUtils.random(0, WORLD_WIDTH - 1));
        }
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);

        if (transformComponent.position.y <= 1) {
            entity.add(engine.createComponent(RemoveComponent.class));
            return;
        }

        players.forEach(player -> {
            final TransformComponent playerTransform = TransformComponent.MAPPER.get(player);
            playerBounds.set(playerTransform.position.x, playerTransform.position.y, playerTransform.size.x, playerTransform.size.y);

            powerUpBounds.set(transformComponent.position.x, transformComponent.position.y, transformComponent.size.x, transformComponent.size.y);

            if (playerBounds.overlaps(powerUpBounds)) {
                collectPowerUp(player, entity);
            }
        });
    }

    private void collectPowerUp(final Entity player, final Entity entity) {
        final PowerUpComponent powerUpComponent = PowerUpComponent.MAPPER.get(entity);

        final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(player);
        final MoveComponent moveComponent = MoveComponent.MAPPER.get(player);

        Gdx.app.debug(TAG, "Collected power up: " + powerUpComponent.type);

        switch (powerUpComponent.type) {
            case SPEED_1:
                moveComponent.speed.y += BOOST_1_SPEED_GAIN;
                break;
            case SPEED_2:
                moveComponent.speed.y += BOOST_2_SPEED_GAIN;
                break;
            case LIFE:
                playerComponent.life = Math.min(playerComponent.life + LIFE_GAIN, playerComponent.maxLife);
                break;
            case SHIELD:
                playerComponent.shield = Math.min(playerComponent.shield + SHIELD_GAIN, playerComponent.maxShield);
                break;
            default:
                Gdx.app.error(TAG, "Unknown power up type: " + powerUpComponent.type);
        }

        gameEventManager.dispatchEvent(
            GameEventType.COLLECT_POWER_UP,
            new GameEventCollectPowerUp(player, powerUpComponent.type)
        );
        entity.add(engine.createComponent(RemoveComponent.class));
    }

    private void spawnPowerUp(final PowerUpComponent.PowerUpType type, final float x) {
        final Entity powerUp = engine.createEntity();

        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setInitialPosition(x, WORLD_HEIGHT, 0);

        final PowerUpComponent powerUpComponent = engine.createComponent(PowerUpComponent.class);
        powerUpComponent.type = type;

        final AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.type = type.getAnimationType();

        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);

        final MoveComponent moveComponent = engine.createComponent(MoveComponent.class);
        moveComponent.speed.y = POWER_UP_SPEED;

        powerUp.add(transformComponent);
        powerUp.add(powerUpComponent);
        powerUp.add(animationComponent);
        powerUp.add(graphicComponent);
        powerUp.add(moveComponent);

        engine.addEntity(powerUp);
    }

    private static class SpawnPattern {

        private final Array<PowerUpComponent.PowerUpType> types = new Array<>();

        public SpawnPattern(
            PowerUpComponent.PowerUpType type1,
            PowerUpComponent.PowerUpType type2,
            PowerUpComponent.PowerUpType type3,
            PowerUpComponent.PowerUpType type4,
            PowerUpComponent.PowerUpType type5
        ) {
            types.add(type1);
            types.add(type2);
            types.add(type3);
            types.add(type4);
            types.add(type5);
        }
    }
}
