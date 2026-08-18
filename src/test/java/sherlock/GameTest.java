package sherlock;

import java.util.*;

import org.testng.*;
import org.testng.annotations.*;

public class GameTest {

    @Test
    public void lastTest() {
        final Game game =
            new Game(
                new Room[][] {
                    {
                        Room.LIVING_ROOM,
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.GARAGE,
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIBRARY,
                        Room.HOBBY_ROOM,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.GARAGE,
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIVING_ROOM,
                        Room.LIBRARY,
                        Room.HOBBY_ROOM,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.LIVING_ROOM,
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIBRARY,
                        Room.HOBBY_ROOM,
                        Room.GARAGE,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIBRARY,
                        Room.LIVING_ROOM,
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.GARAGE,
                        Room.HOBBY_ROOM,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.LIVING_ROOM,
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.DINING_ROOM,
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIBRARY,
                        Room.HOBBY_ROOM,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.LIBRARY,
                        Room.HOBBY_ROOM,
                        Room.LIVING_ROOM,
                        Room.DINING_ROOM,
                        Room.KITCHEN,
                        Room.GARAGE,
                        Room.HOBBY_ROOM,
                        Room.LIBRARY,
                        Room.LIVING_ROOM
                    },
                    {
                        Room.GARAGE,
                        Room.HOBBY_ROOM,
                        Room.KITCHEN,
                        Room.DINING_ROOM,
                        null,
                        null,
                        null,
                        null,
                        null
                    }
                },
                Suspect.EDWARD,
                3
            );
        Assert.assertEquals(
            game.last(Suspect.ALICE),
            new Information(
                Suspect.ALICE,
                "Alice, wann haben Sie George zuletzt gesehen?",
                "3",
                Room.KITCHEN,
                Set.of("Charlie", "Edward", "George"),
                Set.of("Bob")
            )
        );
    }

}
