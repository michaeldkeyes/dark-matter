package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.Pool;

public class PowerUpComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<PowerUpComponent> MAPPER = ComponentMapper.getFor(PowerUpComponent.class);

    public PowerUpType type = PowerUpType.NONE;

    @Override
    public void reset() {
        type = PowerUpType.NONE;
    }

    public enum PowerUpType {
        NONE(AnimationComponent.AnimationType.NONE),
        SPEED_1(AnimationComponent.AnimationType.SPEED_1),
        SPEED_2(AnimationComponent.AnimationType.SPEED_2),
        SHIELD(AnimationComponent.AnimationType.SHIELD),
        LIFE(AnimationComponent.AnimationType.LIFE);

        private final AnimationComponent.AnimationType animationType;

        PowerUpType(AnimationComponent.AnimationType animationType) {
            this.animationType = animationType;
        }

        public AnimationComponent.AnimationType getAnimationType() {
            return animationType;
        }
    }
}
