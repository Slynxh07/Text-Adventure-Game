package org.sean;

public class ZorkUL {
    public static final String WELCOME_MESSAGE = "Welcome to the ZorkUL Game!";
    public static final String VERSION = "1.0";

    private static boolean cheats = false;

    private static volatile boolean running = true;

    private static boolean playerWon = false;

    public static void quit() {
         running = false;
    }

    public static void win() {
        playerWon = true;
        running = false;
    }

    public static boolean won() { return playerWon; }

    public static boolean isRunning() {
        return running;
    }

    public static void setCheats(boolean setter) {
        cheats = setter;
    }

    public static boolean isCheats() {
        return cheats;
    }
}