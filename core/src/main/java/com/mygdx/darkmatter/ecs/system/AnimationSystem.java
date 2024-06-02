package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.darkmatter.ecs.component.AnimationComponent;
import com.mygdx.darkmatter.ecs.component.AnimationComponent.Animation2D;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;

import java.util.EnumMap;

public class AnimationSystem extends IteratingSystem implements EntityListener {

    private static final String TAG = AnimationSystem.class.getSimpleName();
    private static final Family FAMILY = Family.all(AnimationComponent.class, GraphicComponent.class).get();

    private final EnumMap<AnimationComponent.AnimationType, Animation2D> animationCache;
    private final TextureAtlas atlas;

    public AnimationSystem(final TextureAtlas atlas) {
        super(FAMILY);

        this.animationCache = new EnumMap<>(AnimationComponent.AnimationType.class);
        this.atlas = atlas;
    }

    @Override
    public void addedToEngine(final Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(FAMILY, this);
    }

    @Override
    public void removedFromEngine(final Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        final AnimationComponent animationComponent = AnimationComponent.MAPPER.get(entity);

        animationComponent.animation = getAnimation(animationComponent.type);

        final TextureRegion frame = animationComponent.animation.getKeyFrame(animationComponent.stateTime);

        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);
        graphicComponent.sprite.setRegion(frame);
    }

    @Override
    public void entityRemoved(Entity entity) {
        // Do nothing
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animationComponent = AnimationComponent.MAPPER.get(entity);
        final GraphicComponent graphicComponent = GraphicComponent.MAPPER.get(entity);

        if (animationComponent.type == AnimationComponent.AnimationType.NONE) {
            Gdx.app.error(TAG, "No type specified for animation component " + animationComponent + " on entity " + entity);
            return;
        }

        if (animationComponent.type == animationComponent.animation.type) {
            animationComponent.stateTime += deltaTime;
        } else {
            animationComponent.stateTime = 0f;
            animationComponent.animation = getAnimation(animationComponent.type);
        }

        final TextureRegion frame = animationComponent.animation.getKeyFrame(animationComponent.stateTime);
        graphicComponent.sprite.setRegion(frame);
    }

    private Animation2D getAnimation(final AnimationComponent.AnimationType type) {
        Animation2D animation = animationCache.get(type);

        if (animation == null) {
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(type.atlasKey);

            if (regions.isEmpty()) {
                Gdx.app.error(TAG, "No regions found for atlas key: " + type.atlasKey);
                regions = atlas.findRegions("error");

                if (regions.isEmpty()) {
                    throw new GdxRuntimeException("There is no error region in the atlas");
                }
            } else {
                Gdx.app.debug(TAG, "Adding animation of type: " + type + " with " + regions.size + " regions");
            }

            animation = new Animation2D(type.speedRate, regions, type.playMode, type);
            animationCache.put(type, animation);
        }

        return animation;
    }
}
