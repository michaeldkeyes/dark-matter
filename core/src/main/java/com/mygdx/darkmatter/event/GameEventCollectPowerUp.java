package com.mygdx.darkmatter.event;

import com.badlogic.ashley.core.Entity;
import com.mygdx.darkmatter.ecs.component.PowerUpComponent;

public class GameEventCollectPowerUp implements GameEvent {

    private final Entity player;
    private final PowerUpComponent.PowerUpType powerUpType;

    public GameEventCollectPowerUp(final Entity player, final PowerUpComponent.PowerUpType powerUpType) {
        this.player = player;
        this.powerUpType = powerUpType;
    }

    public PowerUpComponent.PowerUpType getPowerUpType() {
        return powerUpType;
    }

    @Override
    public String toString() {
        return "GameEventCollectPowerUp{" +
            "player=" + player +
            ", powerUpType=" + powerUpType +
            "}";
    }
}
