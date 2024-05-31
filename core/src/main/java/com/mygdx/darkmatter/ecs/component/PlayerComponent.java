package com.mygdx.darkmatter.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    public static final ComponentMapper<PlayerComponent> MAPPER = ComponentMapper.getFor(PlayerComponent.class);

    private static final int MAX_LIFE = 100;
    private static final int MAX_SHIELD = 100;

    public int life = MAX_LIFE;
    public int maxLife = MAX_LIFE;
    public int shield = 0;
    public int maxShield = MAX_SHIELD;
    public float distance = 0f;

    @Override
    public void reset() {
        life = MAX_LIFE;
        maxLife = MAX_LIFE;
        shield = 0;
        maxShield = MAX_SHIELD;
        distance = 0f;
    }
}
