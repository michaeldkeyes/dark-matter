package com.mygdx.darkmatter.event;

import com.badlogic.gdx.Gdx;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class GameEventManager {

    private static final String TAG = GameEventManager.class.getSimpleName();
    private static GameEventManager instance = null;

    private final EnumMap<GameEventType, Set<GameEventListener>> listeners = new EnumMap<>(GameEventType.class);

    private GameEventManager() {
    }

    public static GameEventManager getInstance() {

        if (instance == null) {
            instance = new GameEventManager();
        }

        return instance;
    }

    public void addListener(final GameEventType type, final GameEventListener listener) {
        Set<GameEventListener> eventListeners = listeners.get(type);

        if (eventListeners == null) {
            eventListeners = new HashSet<>();
            eventListeners.add(listener);
            listeners.put(type, eventListeners);
        } else {
            eventListeners.add(listener);
        }
    }

    public void removeListener(final GameEventType type, final GameEventListener listener) {
        final Set<GameEventListener> eventListeners = listeners.get(type);

        if (eventListeners != null) {
            eventListeners.remove(listener);
            Gdx.app.debug(TAG, "Removed listener for event type: " + type);
            return;
        }

        Gdx.app.error(TAG, "Attempting to remove listener for event type that is not registered: " + type);
    }

    public void removeListener(final GameEventListener listener) {
        for (Set<GameEventListener> eventListeners : listeners.values()) {
            eventListeners.remove(listener);
        }
    }

    public void dispatchEvent(final GameEventType type, final GameEvent data) {
        Gdx.app.debug(TAG, "Dispatching event: " + type);
        final Set<GameEventListener> listenersSet = this.listeners.get(type);
        Gdx.app.debug(TAG, "Listeners: " + listenersSet);
        if (listenersSet != null) {
            for (final GameEventListener listener : listenersSet) {
                listener.onEvent(type, data);
            }
        }
    }
}
