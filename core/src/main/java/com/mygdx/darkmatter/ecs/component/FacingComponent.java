package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.Pool;

public class FacingComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<FacingComponent> MAPPER = ComponentMapper.getFor(FacingComponent.class);

    public Direction direction = Direction.DEFAULT;

    @Override
    public void reset() {
        direction = Direction.DEFAULT;
    }

    public enum Direction {
        LEFT, DEFAULT, RIGHT
    }
}
