package sherlock;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import javax.swing.*;

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
            final int murderer =
                JOptionPane.showOptionDialog(
                    null,
                    "Wer hat " + Game.VICTIM + " ermordet?",
                    "Mörder",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Arrays.stream(Suspect.values()).map(s -> s.name).toArray(),
                    Suspect.ALICE.name
                );
            final int time =
                JOptionPane.showOptionDialog(
                    null,
                    "Wann wurde " + Game.VICTIM + " ermordet?",
                    "Mordzeit",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    IntStream.range(1, 10).boxed().toArray(),
                    Integer.valueOf(1)
                );
            final int place =
                JOptionPane.showOptionDialog(
                    null,
                    "Und wo wurde " + Game.VICTIM + " ermordet?",
                    "Tatort",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Arrays.stream(Room.values()).limit(Room.values().length - 1).map(r -> r.name).toArray(),
                    Room.LIVING_ROOM.name
                );
            switch (this.game.solve(murderer, time, place)) {
            case 3:
                final int accusations = this.game.getNumberOfAccusations();
                JOptionPane.showMessageDialog(
                    null,
                    String.format(
                        "Herzlichen Glückwunsch, Herr Holmes!\n%s%s hat %s um %s Uhr %s %s ermordet.\n%s%s%s%s",
                        "Sie haben den Fall gelöst!\n\n",
                        Suspect.values()[murderer].name,
                        Game.VICTIM,
                        time + 1,
                        Room.values()[place].prefix,
                        Room.values()[place].name,
                        "Sie haben ",
                        this.game.getNumberOfQuestions(),
                        " Befragungen durchgeführt.",
                        accusations == 1 ?
                            "" :
                                "\nLeider haben Sie "
                                + accusations
                                + " falsche Beschuldigungen erhoben.\n"
                                + "Vielleicht sollten wir uns beim nächsten Mal doch lieber an Scotland Yard wenden."
                    )
                );
                System.exit(0);
            case 2:
                JOptionPane.showMessageDialog(null, "Aber Herr Holmes - das stimmt leider nicht ganz!");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Aber Herr Holmes - Ihr Keks ist wohl feucht!");
            }
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
