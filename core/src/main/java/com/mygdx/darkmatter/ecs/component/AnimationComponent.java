package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<AnimationComponent> MAPPER = ComponentMapper.getFor(AnimationComponent.class);

    private static final float DEFAULT_FRAME_DURATION = 1 / 20f;

    public AnimationType type = AnimationType.NONE;
    public float stateTime = 0f;
    public Animation2D animation = null;

    @Override
    public void reset() {
        type = AnimationType.NONE;
        stateTime = 0f;
    }

    public enum AnimationType {
        NONE(""),
        DARK_MATTER("dark_matter", Animation.PlayMode.LOOP, 1f),
        FIRE("fire");

        AnimationType(final String atlasKey) {
            this.atlasKey = atlasKey;
            this.playMode = Animation.PlayMode.LOOP;
            this.speedRate = 1f;
        }

        AnimationType(final String atlasKey, final Animation.PlayMode playMode, final float speedRate) {
            this.atlasKey = atlasKey;
            this.playMode = playMode;
            this.speedRate = speedRate;
        }

        public final String atlasKey;
        public final Animation.PlayMode playMode;
        public final float speedRate;
    }

    public static class Animation2D extends Animation<TextureRegion> {

        public final AnimationComponent.AnimationType type;

        public Animation2D(final float frameDuration, final Array<? extends TextureRegion> keyFrames,
                           final Animation.PlayMode playMode, final AnimationComponent.AnimationType type) {
            super((DEFAULT_FRAME_DURATION) / frameDuration, keyFrames, playMode);

            this.type = type;
        }
    }

}


