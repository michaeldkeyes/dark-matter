package com.mygdx.darkmatter.event;

public interface GameEventListener {

    void onEvent(final GameEventType eventType, final GameEvent data);
}
