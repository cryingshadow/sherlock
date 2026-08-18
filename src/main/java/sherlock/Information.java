package sherlock;

import java.util.*;
import java.util.stream.*;

public record Information(
    Suspect suspect,
    String question,
    String time,
    Room room,
    Set<String> here,
    Set<String> nextDoor
) {

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.question());
        result.append("\n");
        result.append(this.suspect().name);
        result.append(": ");
        if (this.room == null) {
            result.append("Am Nachmittag leider gar nicht mehr.");
            return result.toString();
        }
        result.append("Um ");
        result.append(this.time);
        result.append(" Uhr war ich ");
        switch (this.room) {
        case DINING_ROOM:
            result.append("im Esszimmer");
            break;
        case GARAGE:
            result.append("in der Garage");
            break;
        case HOBBY_ROOM:
            result.append("im Hobbyraum");
            break;
        case KITCHEN:
            result.append("in der Küche");
            break;
        case LIBRARY:
            result.append("in der Bibliothek");
            break;
        case LIVING_ROOM:
            result.append("im Wohnzimmer");
            break;
        default:
            throw new IllegalStateException(this.room.name);
        }
        result.append(".");
        if (!this.here.isEmpty()) {
            result.append(" ");
            if (this.here.size() == 1) {
                result.append(this.here.iterator().next());
                result.append(" war");
            } else {
                result.append(this.here.stream().limit(this.here.size() - 1).collect(Collectors.joining(", ")));
                result.append(" und ");
                result.append(this.here.stream().skip(this.here.size() - 1).findFirst().get());
                result.append(" waren");
            }
            result.append(" auch dort.");
        }
        if (!this.nextDoor.isEmpty()) {
            result.append(" ");
            if (this.nextDoor.size() == 1) {
                result.append(this.nextDoor.iterator().next());
                result.append(" war");
            } else {
                result.append(this.nextDoor.stream().limit(this.nextDoor.size() - 1).collect(Collectors.joining(", ")));
                result.append(" und ");
                result.append(this.nextDoor.stream().skip(this.nextDoor.size() - 1).findFirst().get());
                result.append(" waren");
            }
            result.append(" nebenan.");
        }
        return result.toString();
    }

}
