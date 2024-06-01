package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<TransformComponent> MAPPER = ComponentMapper.getFor(TransformComponent.class);

    public Vector3 position = new Vector3();
    public Vector3 previousPosition = new Vector3();
    public Vector3 interpolatedPosition = new Vector3();
    public Vector2 size = new Vector2(1, 1);
    public float rotation = 0f;

    @Override
    public void reset() {
        position.set(0, 0, 0);
        previousPosition.set(0, 0, 0);
        interpolatedPosition.set(0, 0, 0);
        size.set(1, 1);
        rotation = 0f;
    }

    public void setInitialPosition(final float x, final float y, final float z) {
        position.set(x, y, z);
        previousPosition.set(x, y, z);
        interpolatedPosition.set(x, y, z);
    }
}
