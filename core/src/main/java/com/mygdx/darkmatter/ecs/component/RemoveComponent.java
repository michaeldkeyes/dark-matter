package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.Pool;

public class RemoveComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<RemoveComponent> MAPPER = ComponentMapper.getFor(RemoveComponent.class);

    public float delay = 0f;

    @Override
    public void reset() {
        delay = 0f;
    }
}
