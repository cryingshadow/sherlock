package sherlock;

import java.util.*;
import java.util.function.*;

public class Engine {

    private final List<Consumer<String>> messageListeners;

    private String suspect;

    public Engine(final String initialSuspect) {
        this.messageListeners = new LinkedList<Consumer<String>>();
        this.suspect = initialSuspect;
    }

    public void askedForGeorge() {
        this.notify(String.format("%s, wann haben Sie George zuletzt gesehen?", this.suspect));
    }

    public void registerMessageListener(final Consumer<String> listener) {
        this.messageListeners.add(listener);
    }

    public void roomClicked(final String room) {
        if ("OK".equals(room)) {
            this.notify("Sie haben den Fall gelöst.");
        } else {
            this.notify(String.format("%s, wann waren Sie im Raum %s?", this.suspect, room));
        }
    }

    public void suspectSelected(final String suspect) {
        this.suspect = suspect;
    }

    public void timeClicked(final String time) {
        this.notify(String.format("%s, wo waren Sie um %s Uhr?", this.suspect, time));
    }

    private void notify(final String message) {
        for (final Consumer<String> listener : this.messageListeners) {
            listener.accept(message);
        }
    }

}
