package sherlock;

import java.util.*;
import java.util.stream.*;

public class Game {

    public static final List<Door> DOORS;

    public static final int TIMES;

    public static final String VICTIM;

    private static final String LAST_PATTERN;

    private static final String WHEN_PATTERN;

    private static final String WHERE_PATTERN;

    static {
        DOORS =
            List.of(
                new Door(Room.LIBRARY, Room.LIVING_ROOM),
                new Door(Room.LIBRARY, Room.HOBBY_ROOM),
                new Door(Room.LIVING_ROOM, Room.DINING_ROOM),
                new Door(Room.DINING_ROOM, Room.KITCHEN),
                new Door(Room.GARAGE, Room.KITCHEN),
                new Door(Room.GARAGE, Room.HOBBY_ROOM)
            );
        TIMES = 9;
        VICTIM = "George";
        LAST_PATTERN = "%s, wann haben Sie %s zuletzt gesehen?";
        WHEN_PATTERN = "%s, wann waren Sie %s %s?";
        WHERE_PATTERN = "%s, wo waren Sie um %s Uhr?";
    }

    public static String jobDescription() {
        final StringBuilder result = new StringBuilder();
        result.append("Sehr geehrter Herr Holmes,\n\n");
        result.append(
            Arrays.stream(Suspect.values())
            .limit(Suspect.values().length - 1)
            .map(s -> s.name)
            .collect(Collectors.joining(", "))
        );
        result.append(" und ");
        result.append(Suspect.values()[Suspect.values().length - 1].name);
        result.append(" waren Gäste auf einer Feier von ");
        result.append(Game.VICTIM);
        result.append(".\nUm 10 Uhr abends wurde ");
        result.append(Game.VICTIM);
        result.append(" tot auf seinem Anwesen aufgefunden.\nMittags hatte er");
        result.append(" noch einem Bankett mit vielen Teilnehmern vorgestanden, bevor er");
        result.append(" sich mit seinen Gästen auf sein Anwesen zurückzog.\n");
        result.append(Game.VICTIM);
        result.append(" muss von einem seiner Gäste zwischen 1 Uhr nachmittags und 9 Uhr abends ermordet worden sein.");
        result.append("\nWir beauftragen Sie damit, herauszufinden, wer ");
        result.append(Game.VICTIM);
        result.append(" wann und in welchem Raum ermordet hat.\nSie können dazu die Gäste befragen. Aber Vorsicht - ");
        result.append("der Mörder lügt bestimmt!\n\n");
        result.append("Übernehmen Sie den Fall?");
        return result.toString();
    }

    private static String toTime(final int time) {
        return String.valueOf(time + 1);
    }

    private int accusations;

    private final Room[][] locations;

    private final Suspect murderer;

    private final int murderTime;

    private int questions;

    private final Random random;

    public Game() {
        this.random = new Random();
        this.questions = 0;
        this.accusations = 0;
        final int numberOfSuspects = Suspect.values().length;
        this.locations = new Room[numberOfSuspects + 1][Game.TIMES];
        final Room[] rooms = Room.values();
        for (int person = 0; person < numberOfSuspects; person++) {
            for (int time = 0; time < Game.TIMES; time++) {
                this.locations[person][time] = rooms[this.random.nextInt(rooms.length - 1)];
            }
        }
        this.murderTime = this.random.nextInt(Game.TIMES);
        for (int time = 0; time < this.murderTime; time++) {
            this.locations[numberOfSuspects][time] = rooms[this.random.nextInt(rooms.length)];
        }
        final int murdererIndex = this.random.nextInt(numberOfSuspects);
        this.murderer = Suspect.values()[murdererIndex];
        this.locations[numberOfSuspects][this.murderTime] = this.locations[murdererIndex][this.murderTime];
    }

    Game(final Room[][] locations, final Suspect murderer, final int murderTime) {
        this.random = new Random(42);
        this.locations = locations;
        this.murderer = murderer;
        this.murderTime = murderTime;
    }

    public int getNumberOfAccusations() {
        return this.accusations;
    }

    public int getNumberOfQuestions() {
        return this.questions;
    }

    public Information last(final Suspect suspect) {
        final int person = suspect.ordinal();
        final int numberOfSuspects = Suspect.values().length;
        final boolean isMurderer = suspect == this.murderer;
        String lastTime = null;
        outer: for (int time = Game.TIMES - 1; time >= 0; time--) {
            final Room victimRoom = this.locations[numberOfSuspects][time];
            final Room personRoom = this.locations[person][time];
            if (victimRoom == personRoom) {
                if (!isMurderer || time != this.murderTime) {
                    lastTime = Game.toTime(time);
                    break outer;
                }
            }
            for (final Door door : Game.DOORS) {
                if (victimRoom != null && victimRoom == door.nextRoom(personRoom)) {
                    if (!isMurderer || time != this.murderTime) {
                        lastTime = Game.toTime(time);
                        break outer;
                    }
                }
            }
        }
        if (lastTime == null) {
            this.questions++;
            return
                new Information(
                    suspect,
                    String.format(Game.LAST_PATTERN, suspect.name, Game.VICTIM),
                    null,
                    null,
                    null,
                    null
                );
        }
        final Information whereInfo = this.where(suspect, lastTime);
        return
            new Information(
                suspect,
                String.format(Game.LAST_PATTERN, suspect.name, Game.VICTIM),
                lastTime,
                whereInfo.room(),
                whereInfo.here(),
                whereInfo.nextDoor()
            );
    }

    public int solve(final int murderer, final int time, final int place) {
        this.accusations++;
        int result = 0;
        if (murderer == this.murderer.ordinal()) {
            result++;
        }
        if (time == this.murderTime) {
            result++;
        }
        if (place == this.locations[this.murderer.ordinal()][this.murderTime].ordinal()) {
            result++;
        }
        return result;
    }

    public Information when(final Suspect suspect, final Room room) {
        this.questions++;
        final List<Integer> times = new ArrayList<Integer>();
        final int person = suspect.ordinal();
        final boolean isMurderer = suspect == this.murderer;
        for (int time = 0; time < Game.TIMES; time++) {
            if (isMurderer && time == this.murderTime) {
                continue;
            }
            if (this.locations[person][time] == room) {
                times.add(time);
            }
        }
        if (times.isEmpty()) {
            return
                new Information(
                    suspect,
                    String.format(Game.WHEN_PATTERN, suspect.name, room.prefix, room.name),
                    null,
                    null,
                    null,
                    null
                );
        }
        final String time = Game.toTime(times.get(this.random.nextInt(times.size())));
        return
            new Information(
                suspect,
                String.format(Game.WHEN_PATTERN, suspect.name, room.prefix, room.name),
                time,
                room,
                this.here(suspect, time, room),
                this.nextDoor(time, room)
            );
    }

    public Information where(final Suspect suspect, final String time) {
        this.questions++;
        final Room room = this.locations[suspect.ordinal()][Integer.parseInt(time) - 1];
        if (suspect == this.murderer && Integer.parseInt(time) - 1 == this.murderTime) {
            final Room[] rooms = Room.values();
            Room otherRoom = room;
            while (room == otherRoom) {
                otherRoom = rooms[this.random.nextInt(rooms.length - 1)];
            }
            return
                new Information(
                    suspect,
                    String.format(Game.WHERE_PATTERN, suspect.name, time),
                    time,
                    otherRoom,
                    Set.of(),
                    Set.of()
                );
        }
        return
            new Information(
                suspect,
                String.format(Game.WHERE_PATTERN, suspect.name, time),
                time,
                room,
                this.here(suspect, time, room),
                this.nextDoor(time, room)
            );
    }

    private Set<String> here(final Suspect suspect, final String time, final Room room) {
        if (room == null) {
            return Set.of();
        }
        final Set<String> result = new TreeSet<String>();
        final int timeIndex = Integer.parseInt(time) - 1;
        final int numberOfPeople = Suspect.values().length + 1;
        for (int person = 0; person < numberOfPeople; person++) {
            if (this.locations[person][timeIndex] == room) {
                result.add(Suspect.getName(person));
            }
        }
        if (suspect != null) {
            result.remove(suspect.name);
        }
        return result;
    }

    private Set<String> nextDoor(final String time, final Room room) {
        final Set<String> result = new TreeSet<String>();
        for (final Door door : Game.DOORS) {
            result.addAll(this.here(null, time, door.nextRoom(room)));
        }
        return result;
    }

}
