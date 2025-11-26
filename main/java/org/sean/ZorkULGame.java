package org.sean;

/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a 
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
org.sean.zork.Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
org.sean.zork.Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting, 
emphasizing exploration and simple command-driven gameplay
*/

import java.io.*;
import java.util.HashMap;

public class ZorkULGame implements Runnable {
    final private Parser parser;
    private Player player;

    private Potion healthPotion;
    private Potion invisPotion;
    private Sword sword;
    private Lockpick lockpick;

    final private HashMap<String, Item> items;
    final private HashMap<String, Room> rooms;

    public ZorkULGame() {
        System.setProperty("jna.library.path", "/home/sean/Java projects/CustomLibTest/src/main/gameLib");
        items = new HashMap<>();
        rooms = new HashMap<>();
        init();
        parser = new Parser();
    }

    private void init() {
        healthPotion = new Potion("elixir", "heals +30% of max health, use with \"heal\"", Effects.HEALING);
        invisPotion = new Potion("nullis", "Makes user invisible to npcs for 1 room, use with \"nullis\"", Effects.IVISABILITY);
        sword = new Sword("sword", "Attack enemies with \"sword + enemy name\"");
        lockpick = new Lockpick("lockpick", "Used to pick locks");


        items.put(healthPotion.getName(), healthPotion);
        items.put(invisPotion.getName(), invisPotion);
        items.put(sword.getName(), sword);
        items.put(lockpick.getName(), lockpick);

        Room cell, hallway, diningHall, guardOffice, sewer1, sewer2, artifactRoom, mainHall, courtyard, escape;

        // create rooms
        cell = new Room("Dark dreary cell - lock picks 3x", false, lockpick, lockpick, lockpick);
        hallway = new Room("Cold empty hallway - sewer entrance", true);
        diningHall = new Room("Disgusting rodent-infested dining hall - npc with warning", false);
        guardOffice = new Room("Eerily quiet guard office - sword and health potion", true, healthPotion, sword);
        sewer1 = new Room("Grim grotesque sewer", false);
        sewer2 = new Room("Grim grotesque sewer - Enemy spider", false);
        artifactRoom = new Room("Curious looking artifact room - invis potion and health potion", false, invisPotion, healthPotion);
        mainHall = new Room("Main hall - 3 guards", true, new Enemy("Bob"));
        courtyard = new Room("Courtyard - guard", false, new Enemy("John"));
        escape = new Room("Final Room", false);

        // initialise room exits
        cell.setExit("south", hallway);

        hallway.setExit("south", mainHall);
        hallway.setExit("east", guardOffice);
        hallway.setExit("west", diningHall);
        hallway.setExit("down", sewer1);
        hallway.setExit("north", cell);

        diningHall.setExit("east", hallway);

        guardOffice.setExit("west", hallway);

        sewer1.setExit("up", hallway);
        sewer1.setExit("south", sewer2);

        sewer2.setExit("north", sewer1);
        sewer2.setExit("up", artifactRoom);

        artifactRoom.setExit("down", sewer2);
        artifactRoom.setExit("west", mainHall);

        mainHall.setExit("north", hallway);
        mainHall.setExit("east", artifactRoom);
        mainHall.setExit("west", courtyard);

        courtyard.setExit("east", mainHall);
        courtyard.setExit("south", escape);

        rooms.put("cell", cell);
        rooms.put("hallway", hallway);
        rooms.put("dinning-hall", diningHall);
        rooms.put("office", guardOffice);
        rooms.put("sewer1", sewer1);
        rooms.put("sewer2", sewer2);
        rooms.put("artifact-room", artifactRoom);
        rooms.put("main-hall", mainHall);
        rooms.put("courtyard", courtyard);
        rooms.put("escape", escape);

        // create the player character and start outside
        player = new Player("sean", cell);
    }

    public void play() {
        //deSeralize();
        printWelcome();

        boolean finished = false;
        while (!finished) {
            player.inCombat = player.getCurrentRoom().containsEnemy() && player.isVisable();
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //seralize();
        System.out.println("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("You've been locked away... Try escape!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't understand your command...");
            return false;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                if (!player.inCombat) goRoom(command);
                else System.out.println("Can't leave room while in combat");
                break;
            case "take":
                take(command);
                break;
            case "drop":
                drop(command);
                break;
            case "inventory":
                showInventory(command);
                break;
            case "items":
                System.out.println(player.getCurrentRoom().displayItems());
                break;
            case "sword":
                attack(command);
                break;
            case "heal":
                heal(command);
                break;
            case "cheats":
                cheat(command);
                break;
            case "teleport":
                teleport(command);
                break;
            case "kill":
                kill(command);
                break;
            case "lockpick":
                picklock(command);
                break;
            case "nullis":
                nullis(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                    return false;
                } else {
                    return true; // signal to quit
                }
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You navigate the chilling prison.");
        System.out.print("Your command words are: ");
        parser.showCommands();
    }

    protected void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else if (nextRoom.isLocked()) {
            System.out.println("Door is locked...");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    private void nullis(Command command) {
        if (!player.checkInventory(invisPotion)) {
            System.out.println("You don't have an invisibility potion, maybe you can find one...");
            return;
        }
        if (command.hasSecondWord()) {
            System.out.println("You should only use nullis on yourself...");
            return;
        }
        invisPotion.use(player);
    }

    private void attack(Command command) {
        if (!player.checkInventory(sword)) {
            System.out.println("org.sean.zork.Sword is not in inventory");
            return;
        }

        if (!command.hasSecondWord()) {
            System.out.println("Use sword on who?");
            return;
        }

        String target = command.getSecondWord();
        if (player.getCurrentRoom().getCharacter() != null && target.equals(player.getCurrentRoom().getCharacter().getName())) {
            sword.use(player.getCurrentRoom().getCharacter());
            System.out.println("Used sword on " + target);
            System.out.println(target + " health: " + player.getCurrentRoom().getCharacter().getHealth());
        } else {
            System.out.println("Target not found in this room...");
        }
    }

    private void cheat(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println(ZorkUL.isCheats() ? "Cheats enabled" : "Cheats disabled");
            return;
        }

        String setter = command.getSecondWord();

        switch (setter) {
            case "enable":
                ZorkUL.setCheats(true);
                parser.enableCheats();
                System.out.println("Enabled Cheats");
                break;
            case "disable":
                ZorkUL.setCheats(false);
                parser.disableCheats();
                System.out.println("Disabled Cheats");
                break;
            default:
                System.out.println("I don't understand what you want...");
                break;
        }
    }

    private void kill(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Kill who?");
            return;
        }
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item item = items.get(itemName);

        if (player.getCurrentRoom().removeItem(item)) {
            player.takeItem(item);
        } else {
            System.out.println("There is no " + itemName + " here");
            return;
        }
        System.out.println("You picked up an item");
    }

    private void drop (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
        }

        String itemName = command.getSecondWord();

        Item item = items.get(itemName);

        if (player.dropItem(item)) {
            player.getCurrentRoom().addItem(item);
        } else {
            System.out.println("There is no " + itemName + " in your inventory");
            System.out.println("See your inventory with: \"inventory\"");
            return;
        }
        System.out.println("org.sean.zork.Item has been dropped!");
    }

    private void showInventory(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Just type inventory man...");
            return;
        }
        System.out.println(player.displayInventory());
    }

    private void heal(Command command) {
        if (!player.checkInventory(healthPotion)) {
            System.out.println("You don't have a health potion, maybe you can find one...");
            return;
        }
        if (command.hasSecondWord()) {
            System.out.println("You shouldn't heal anyone else bucko...");
            return;
        }
        if (player.getHealth() >= 100) {
            System.out.println("Lock in unc, you're full health");
        }
        healthPotion.use(player);
    }

    private void picklock(Command command) {
        if (!player.checkInventory(lockpick)) {
            System.out.println("You don't have a lockpick in your inventory");
            return;
        }
        if (!command.hasSecondWord()) {
            System.out.println("Pick what?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else if (!nextRoom.isLocked()) {
            System.out.println("Door is already unlocked...");
        } else if (GameLib.INSTANCE.runGame()) {
            System.out.println("Success! You picked the lock");
            nextRoom.unlock();
        } else {
            System.out.println("Oh no! Your pick broke...");
            player.removeItem(lockpick);
        }
    }

    private void teleport(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Teleport where?");
            return;
        }

        String location = command.getSecondWord();

        Room room = rooms.get(location);

        if (room != null) {
            player.setCurrentRoom(room);
            System.out.println(player.getCurrentRoom().getLongDescription());
            return;
        }
        System.out.println("org.sean.zork.Room doesn't exist...");
    }

    private void seralize() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("player.ser"))) {
            out.writeObject(player);
            System.out.println("Object has been serialized to player.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deSeralize() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("player.ser"))) {
            Player deserializedPlayer = (Player) in.readObject();
            System.out.println("Object has been deserialized:");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        play();
    }
}