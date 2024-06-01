package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.*;

public class MoveSystem extends IteratingSystem {

    private static final Family FAMILY =
        Family.all(MoveComponent.class, TransformComponent.class).exclude(RemoveComponent.class).get();

    private static final float UPDATE_RATE = 1 / 25f;
    private static final float HORIZONTAL_ACCELERATION = 16.5f;
    private static final float VERTICAL_ACCELERATION = 2.25f;
    private static final float MAX_VERTICAL_NEGATIVE_PLAYER_SPEED = 0.75f;
    private static final float MAX_HORIZONTAL_SPEED = 5.5f;
    private static final float MAX_VERTICAL_POSITIVE_SPEED = 5f;

    private float accumulator = 0f;

    public MoveSystem() {
        super(FAMILY);
    }

    @Override
    public void update(final float deltaTime) {
        accumulator += deltaTime;
        while (accumulator >= UPDATE_RATE) {
            accumulator -= UPDATE_RATE;
            super.update(UPDATE_RATE);
        }
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final MoveComponent moveComponent = MoveComponent.MAPPER.get(entity);
        final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);

        if (playerComponent != null) {
            final FacingComponent facingComponent = FacingComponent.MAPPER.get(entity);
            movePlayer(transformComponent, moveComponent, playerComponent, facingComponent, deltaTime);
        } else {
            moveEntity(transformComponent, moveComponent, deltaTime);
        }
    }

    private void movePlayer(final TransformComponent transformComponent, final MoveComponent moveComponent,
                            final PlayerComponent playerComponent, final FacingComponent facingComponent,
                            final float deltaTime) {

        switch (facingComponent.direction) {
            case LEFT:
                moveComponent.speed.x = Math.min(0f, moveComponent.speed.x - HORIZONTAL_ACCELERATION * deltaTime);
                break;
            case RIGHT:
                moveComponent.speed.x = Math.max(0f, moveComponent.speed.x + HORIZONTAL_ACCELERATION * deltaTime);
                break;
            default:
                moveComponent.speed.x = 0;
                break;
        }

        moveComponent.speed.x = MathUtils.clamp(moveComponent.speed.x, -MAX_HORIZONTAL_SPEED, MAX_HORIZONTAL_SPEED);

        moveComponent.speed.y = MathUtils.clamp(
            moveComponent.speed.y - VERTICAL_ACCELERATION * deltaTime,
            -MAX_VERTICAL_NEGATIVE_PLAYER_SPEED,
            MAX_VERTICAL_POSITIVE_SPEED
        );

        moveEntity(transformComponent, moveComponent, deltaTime);
    }

    private void moveEntity(final TransformComponent transformComponent, final MoveComponent moveComponent, final float deltaTime) {
        transformComponent.position.x = MathUtils.clamp(
            transformComponent.position.x + moveComponent.speed.x * deltaTime,
            0,
            DarkMatter.WORLD_WIDTH - transformComponent.size.x
        );
        transformComponent.position.y = MathUtils.clamp(
            transformComponent.position.y + moveComponent.speed.y * deltaTime,
            1,
            DarkMatter.WORLD_HEIGHT + 1 - transformComponent.size.y
        );
    }
}
