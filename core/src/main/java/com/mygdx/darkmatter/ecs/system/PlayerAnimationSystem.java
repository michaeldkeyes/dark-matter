package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.darkmatter.ecs.component.FacingComponent;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.PlayerComponent;

public class PlayerAnimationSystem extends IteratingSystem implements EntityListener {

    private static final Family FAMILY = Family.all(PlayerComponent.class, FacingComponent.class, GraphicComponent.class).get();

    private final TextureRegion defaultRegion;
    private final TextureRegion leftRegion;
    private final TextureRegion rightRegion;

    private FacingComponent.Direction lastDirection = FacingComponent.Direction.DEFAULT;

    public PlayerAnimationSystem(final TextureRegion defaultRegion, final TextureRegion leftRegion,
                                 final TextureRegion rightRegion) {
        super(FAMILY);

        this.defaultRegion = defaultRegion;
        this.leftRegion = leftRegion;
        this.rightRegion = rightRegion;
    }

    @Override
    public void addedToEngine (final Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void removedFromEngine (final Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(final Entity entity) {
        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);

        graphicComponent.sprite.setRegion(defaultRegion);
    }

    @Override
    public void entityRemoved(final Entity entity) {
        // Do nothing
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final FacingComponent facingComponent = FacingComponent.MAPPER.get(entity);
        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);

        if (facingComponent.direction == lastDirection && graphicComponent.sprite.getTexture() != null) {
            return;
        }

        lastDirection = facingComponent.direction;

        switch (facingComponent.direction) {
            case LEFT:
                graphicComponent.sprite.setRegion(leftRegion);
                break;
            case RIGHT:
                graphicComponent.sprite.setRegion(rightRegion);
                break;
            default:
                graphicComponent.sprite.setRegion(defaultRegion);
                break;
        }
    }
}
