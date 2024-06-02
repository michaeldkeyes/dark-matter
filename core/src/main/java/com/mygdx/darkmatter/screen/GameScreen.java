package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.*;

import static com.mygdx.darkmatter.DarkMatter.UNIT_SCALE;
import static com.mygdx.darkmatter.DarkMatter.WORLD_WIDTH;
import static com.mygdx.darkmatter.ecs.system.DamageSystem.DAMAGE_AREA_HEIGHT;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    private static final float MAX_DELTA_TIME = 1 / 20f;

    public GameScreen(final DarkMatter game) {
        super(game);

        final Entity playerShip = engine.createEntity();

        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setInitialPosition(4.5f, 8f, 1f);

        final MoveComponent moveComponent = engine.createComponent(MoveComponent.class);

        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);

        final PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        final FacingComponent facingComponent = engine.createComponent(FacingComponent.class);

        playerShip.add(transformComponent);
        playerShip.add(moveComponent);
        playerShip.add(graphicComponent);
        playerShip.add(playerComponent);
        playerShip.add(facingComponent);

        engine.addEntity(playerShip);

        final Entity fire = engine.createEntity();
        final TransformComponent fireTransform = engine.createComponent(TransformComponent.class);
        final AttachComponent attachComponent = engine.createComponent(AttachComponent.class);
        attachComponent.entity = playerShip;
        attachComponent.offset.set(1 * UNIT_SCALE, -6 * UNIT_SCALE);
        final GraphicComponent fireGraphic = engine.createComponent(GraphicComponent.class);
        final AnimationComponent fireAnimation = engine.createComponent(AnimationComponent.class);
        fireAnimation.type = AnimationComponent.AnimationType.FIRE;

        fire.add(fireTransform);
        fire.add(attachComponent);
        fire.add(fireGraphic);
        fire.add(fireAnimation);

        engine.addEntity(fire);

        final Entity darkMatter = engine.createEntity();
        final TransformComponent darkMatterTransform = engine.createComponent(TransformComponent.class);
        darkMatterTransform.size.set(WORLD_WIDTH, DAMAGE_AREA_HEIGHT);

        final AnimationComponent darkMatterAnimation = engine.createComponent(AnimationComponent.class);
        darkMatterAnimation.type = AnimationComponent.AnimationType.DARK_MATTER;

        final GraphicComponent darkMatterGraphic = engine.createComponent(GraphicComponent.class);

        darkMatter.add(darkMatterTransform);
        darkMatter.add(darkMatterAnimation);
        darkMatter.add(darkMatterGraphic);

        engine.addEntity(darkMatter);

    }

    @Override
    public void render(final float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        engine.update(Math.min(MAX_DELTA_TIME, delta));

        // uiViewport.apply();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
    }
}
