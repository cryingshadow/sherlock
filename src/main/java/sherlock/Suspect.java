package sherlock;

public enum Suspect {

    ALICE("Alice"),

    BOB("Bob"),

    CHARLIE("Charlie"),

    DORIS("Doris"),

    EDWARD("Edward"),

    FIONA("Fiona");

    public static String getName(final int person) {
        final Suspect[] suspects = Suspect.values();
        if (person < suspects.length) {
            return suspects[person].name;
        }
        return Game.VICTIM;
    }

    public final String name;

    private Suspect(final String name) {
        this.name = name;
    }

}
