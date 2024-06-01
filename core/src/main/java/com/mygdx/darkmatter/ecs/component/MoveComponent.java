package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class MoveComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<MoveComponent> MAPPER = ComponentMapper.getFor(MoveComponent.class);

    public Vector2 speed = new Vector2();

    @Override
    public void reset() {
        speed.set(0, 0);
    }
}
