package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class AttachComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<AttachComponent> MAPPER = ComponentMapper.getFor(AttachComponent.class);

    public Entity entity = null;
    public Vector2 offset = new Vector2();

    @Override
    public void reset() {
        entity = null;
        offset.set(0, 0);
    }
}
