package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.ecs.component.FacingComponent;
import com.mygdx.darkmatter.ecs.component.PlayerComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

public class PlayerSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
        PlayerComponent.class,
        FacingComponent.class,
        TransformComponent.class)
        .get();
    private static final float TOUCH_TOLERANCE_DISTANCE = 0.2f;

    private final Viewport viewport;

    private final Vector2 tempVec = new Vector2();

    public PlayerSystem(final Viewport viewport) {
        super(FAMILY);

        this.viewport = viewport;
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);
        final FacingComponent facingComponent = FacingComponent.MAPPER.get(entity);

        tempVec.set(Gdx.input.getX(), 0);

        viewport.unproject(tempVec);

        final float diffX = tempVec.x - transformComponent.position.x - transformComponent.size.x * 0.5f;

        if (diffX > TOUCH_TOLERANCE_DISTANCE) {
            facingComponent.direction = FacingComponent.Direction.RIGHT;
        } else if (diffX < -TOUCH_TOLERANCE_DISTANCE) {
            facingComponent.direction = FacingComponent.Direction.LEFT;
        } else {
            facingComponent.direction = FacingComponent.Direction.DEFAULT;
        }
    }
}
