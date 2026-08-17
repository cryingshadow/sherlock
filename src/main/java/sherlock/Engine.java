package sherlock;

import java.util.*;
import java.util.function.*;

public class Engine {

    private final Game game;

    private final List<Consumer<String>> messageListeners;

    private Suspect suspect;

    public Engine(final Suspect initialSuspect) {
        this.messageListeners = new LinkedList<Consumer<String>>();
        this.suspect = initialSuspect;
        this.game = new Game();
    }

    public void askedForGeorge() {
        this.notify(this.game.last(this.suspect).toString());
    }

    public void registerMessageListener(final Consumer<String> listener) {
        this.messageListeners.add(listener);
    }

    public void roomClicked(final Room room) {
        if (room == Room.OK) {
            this.notify("Sie haben den Fall gelöst.");
        } else {
            this.notify(this.game.when(this.suspect, room).toString());
        }
    }

    public void suspectSelected(final Suspect suspect) {
        this.suspect = suspect;
    }

    public void timeClicked(final String time) {
        this.notify(this.game.where(this.suspect, time).toString());
    }

    private void notify(final String message) {
        for (final Consumer<String> listener : this.messageListeners) {
            listener.accept(message);
        }
    }

}
