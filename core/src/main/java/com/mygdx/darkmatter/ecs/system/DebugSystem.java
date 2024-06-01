package com.mygdx.darkmatter.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.darkmatter.ecs.component.PlayerComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

public class DebugSystem extends IteratingSystem {

    private static final float WINDOW_INFO_UPDATE_RATE = 0.25f;

    private static final Family FAMILY = Family.all(PlayerComponent.class).get();

    public DebugSystem() {
        super(FAMILY);
    }

    @Override
    public void processEntity(final Entity entity, final float deltaTime) {
        final PlayerComponent playerComponent = PlayerComponent.MAPPER.get(entity);
        final TransformComponent transformComponent = TransformComponent.MAPPER.get(entity);

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // kill player
            transformComponent.position.y = 1;
            playerComponent.life = 1;
            playerComponent.shield = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // add shield
            playerComponent.shield = Math.min(playerComponent.maxShield, playerComponent.shield + 25);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // remove shield
            playerComponent.shield = Math.max(0, playerComponent.shield - 25);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            // disable movement
            getEngine().getSystem(MoveSystem.class).setProcessing(false);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            // enable movement
            getEngine().getSystem(MoveSystem.class).setProcessing(true);
        }

        Gdx.graphics.setTitle("Life: " + playerComponent.life + " Shield: " + playerComponent.shield + " Position: " + transformComponent.position);
    }
}
