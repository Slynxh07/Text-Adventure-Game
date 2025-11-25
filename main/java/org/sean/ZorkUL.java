package org.sean;

public class ZorkUL {
    public static final String WELCOME_MESSAGE = "Welcome to the org.sean.zork.ZorkUL Game!";
    public static final String VERSION = "1.0";

    private static boolean cheats = false;

    public static void setCheats(boolean setter) {
        cheats = setter;
    }

    public static boolean isCheats() {
        return cheats;
    }
}