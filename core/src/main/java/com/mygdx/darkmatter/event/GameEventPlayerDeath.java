package com.mygdx.darkmatter.event;

public class GameEventPlayerDeath implements GameEvent {

    private final float distance;

    public GameEventPlayerDeath(final float distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameEventPlayerDeath{" +
            "distance=" + distance +
            "}";
    }
}
