package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

public class GraphicComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<GraphicComponent> MAPPER = ComponentMapper.getFor(GraphicComponent.class);

    public Sprite sprite = new Sprite();

    @Override
    public void reset() {
        sprite.setTexture(null);
        sprite.setColor(1, 1, 1, 1);
    }
}
