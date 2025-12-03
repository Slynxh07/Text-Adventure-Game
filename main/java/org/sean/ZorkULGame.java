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
import java.util.ArrayList;
import java.util.HashMap;

public class ZorkULGame implements Runnable {
    final public static Parser parser = new Parser();

    private Player player;
    private Potion healthPotion;
    private Potion invisPotion;
    private Sword sword;
    private Lockpick lockpick;
    private GUIController controller;

    final private HashMap<String, Item> items;
    private HashMap<String, Room> rooms;

    public ZorkULGame() {
        System.setProperty("jna.library.path", "src/main/GameLib");
        items = new HashMap<>();
        rooms = new HashMap<>();
        init();
        //parser = new Parser();
    }

    public void setController(GUIController guiController) {
        controller = guiController;
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
        cell = new Room("Dark dreary cell - lock picks 3x", LockStates.UNLOCKED.state, lockpick, lockpick, lockpick);
        hallway = new Room("Cold empty hallway - sewer entrance", LockStates.LOCKED.state);
        diningHall = new Room("Disgusting rodent-infested dining hall - npc with warning", LockStates.UNLOCKED.state, new Nuetral("Litton", "Carful of Garrin in the main hall, he really hits hard. If your hoping to escape I'd try find some way to get around him instead of fighting him. I heard the sewers lead to the artifact room, maybe you could find something useful there? But beware, there are some rumors of some terrifying beast that guards the sewers..."));
        guardOffice = new Room("Eerily quiet guard office - sword and health potion", LockStates.LOCKED.state, sword);
        sewer1 = new Room("Grim grotesque sewer", LockStates.UNLOCKED.state);
        sewer2 = new Room("Grim grotesque sewer - Enemy spider", LockStates.UNLOCKED.state, new Enemy("Spider", 20, Effects.POISON));
        artifactRoom = new Room("Curious looking artifact room - invis potion and health potion", LockStates.UNLOCKED.state, healthPotion);
        mainHall = new Room("Main hall - 3 guards", LockStates.LOCKED.state, new Enemy("Garrin", 60));
        courtyard = new Room("Courtyard - guard", LockStates.UNLOCKED.state, new Enemy("John", 30));
        escape = new Room("Final Room", LockStates.UNLOCKED.state);

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

        guardOffice.setChest(healthPotion);
        artifactRoom.setChest(invisPotion);

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

    private void printWelcome() {
        controller.newLine();
        controller.outputGUI("You've been locked away... Try escape!");
        controller.outputGUI("Type 'help' if you need help");
        controller.newLine();
        controller.outputGUI(player.getCurrentRoom().getLongDescription());
    }

    public void processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            controller.outputGUI("I don't understand your command...");
            return;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                if (!player.inCombat) goRoom(command);
                else controller.outputGUI("Can't leave room while in combat");
                break;
            case "take":
                take(command);
                break;
            case "loot":
                loot(command);
                break;
            case "drop":
                drop(command);
                break;
            case "inventory":
                showInventory(command);
                break;
            case "items":
                controller.outputGUI(player.getCurrentRoom().displayItems());
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
            case "unlock":
                unlock(command);
                break;
            case "speak":
                speak(command);
                break;
            case "lockpick":
                picklock(command);
                break;
            case "nullis":
                nullis(command);
                break;
            case "inspect":
                inspect(command);
                break;
            case "save":
                save(command);
                break;
            case "load":
                load(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    controller.outputGUI("Quit what?");
                    return;
                } else {
                    ZorkUL.quit();
                    return;
                }
            default:
                controller.outputGUI("I don't know what you mean...");
                break;
        }
    }

    private void printHelp() {
        controller.outputGUI("You are lost. You are alone. You navigate the chilling prison.");
        controller.outputGUI("Your command words are: ");
        controller.outputGUI(parser.showCommands());
    }

    protected void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            controller.outputGUI("There is no door!");
        } else if (nextRoom.isLocked()) {
            controller.outputGUI("Door is locked...");
        } else {
            player.setCurrentRoom(nextRoom);
            if (checkWin()) {
                ZorkUL.win();
                controller.outputGUI("You win");
            }
            controller.outputGUI(player.getCurrentRoom().getLongDescription());
        }
    }

    private boolean checkWin() { return player.getCurrentRoom().equals(rooms.get("escape")); }

    private void inspect(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Inspect what? ");
            return;
        }

        switch (command.getSecondWord()) {
            case "room":
                controller.outputGUI(player.getCurrentRoom().getLongDescription());
                break;
            case "chest":
                if (player.getCurrentRoom().hasChest()) {
                    Chest<Storable> chest = player.getCurrentRoom().getChest();
                    controller.outputGUI("There is a small golden chest in the corner");
                    if (chest.isEmpty()) {
                        controller.outputGUI("It is empty");
                    } else {
                        controller.outputGUI("It contains " + chest.getItemName());
                        controller.outputGUI(chest.getItemInfo());
                    }
                } else {
                    controller.outputGUI("There is no chest here...");
                }
                break;
            default:
                String itemName = command.getSecondWord();
                Item item = items.get(itemName);

                if (player.checkItems(item)) {
                    controller.outputGUI(item.getDescription());
                } else {
                    controller.outputGUI("There is no " + itemName + " in your inventory.");
                }
        }
    }

    private void speak(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Speak to who? ");
            return;
        }

        String name = command.getSecondWord();
        Character npc = player.getCurrentRoom().getCharacter();

        if (npc == null) {
            controller.outputGUI("There's no one in this room...");
            return;
        }

        if (name.equals(npc.getName())) {
            if (npc instanceof Interactable) {
                controller.outputGUI("You spoke to " + name);
                controller.outputGUI("He says ");
                controller.outputGUI(((Interactable) npc).getDialog());
            } else {
                controller.outputGUI(name + " has nothing to say to you...");
            }
        } else {
            controller.outputGUI("There is no one named " + name + " here...");
        }
    }

    private void nullis(Command command) {
        if (!player.checkItems(invisPotion)) {
            controller.outputGUI("You don't have an invisibility potion, maybe you can find one...");
            return;
        }
        if (command.hasSecondWord()) {
            controller.outputGUI("You should only use nullis on yourself...");
            return;
        }
        invisPotion.use(player);
    }

    private void loot(Command command) {
        if (command.hasSecondWord() && command.getSecondWord().equals("chest")) {
            if (player.getCurrentRoom().hasChest()) {
                if (player.getCurrentRoom().getChest().getLoot() instanceof Item) {
                    player.addItem((Item) player.getCurrentRoom().getChest().getLoot());
                    controller.outputGUI("Looted " + player.getCurrentRoom().getChest().getItemName() + " from the chest");
                    player.getCurrentRoom().getChest().emptyChest();
                } else {
                    controller.outputGUI("The chest is empty...");
                }
            } else {
                controller.outputGUI("There's no chest here...");
            }
            return;
        }
        controller.outputGUI("Loot what?");
    }

    private void attack(Command command) {
        if (!player.checkItems(sword)) {
            controller.outputGUI("Sword is not in inventory");
            return;
        }

        if (!command.hasSecondWord()) {
            controller.outputGUI("Use sword on who?");
            return;
        }

        String target = command.getSecondWord();
        if (player.getCurrentRoom().getCharacter() != null && target.equals(player.getCurrentRoom().getCharacter().getName())) {
            sword.use(player.getCurrentRoom().getCharacter());
            controller.outputGUI("Used sword on " + target);
            if (player.getCurrentRoom().getCharacter().isAlive()) {
                controller.outputGUI(target + " health: " + player.getCurrentRoom().getCharacter().getHealth());
                if (player.getCurrentRoom().getCharacter() instanceof Enemy) {
                    ((Enemy) player.getCurrentRoom().getCharacter()).attack(player);
                    controller.outputGUI(target + " attacked you. You have " + player.getHealth() + " health remaining...");
                    if (player.isPoisoned()) controller.outputGUI("You've been inflicted with poison");
                }
            } else {
                controller.outputGUI("You killed " + target);
                player.getCurrentRoom().setCharacter(null);
            }
        } else {
            controller.outputGUI("Target not found in this room...");
        }
    }

    private void cheat(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI(ZorkUL.isCheats() ? "Cheats enabled" : "Cheats disabled");
            return;
        }

        String setter = command.getSecondWord();

        switch (setter) {
            case "enable":
                ZorkUL.setCheats(true);
                parser.enableCheats();
                controller.outputGUI("Enabled Cheats");
                break;
            case "disable":
                ZorkUL.setCheats(false);
                parser.disableCheats();
                controller.outputGUI("Disabled Cheats");
                break;
            default:
                controller.outputGUI("I don't understand what you want...");
                break;
        }
    }

    private void kill(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Kill who?");
        }

        String target = command.getSecondWord();

        if (target.equals(player.getCurrentRoom().getCharacter().getName())) {
            player.getCurrentRoom().getCharacter().die();
            controller.outputGUI("Killed " + target);
            return;
        }
        controller.outputGUI(target + " is not in this room...");
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Take what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item item = items.get(itemName);

        if (player.getCurrentRoom().removeItem(item)) {
            player.addItem(item);
        } else {
            controller.outputGUI("There is no " + itemName + " here");
            return;
        }
        controller.outputGUI("You picked up an item");
    }

    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item item = items.get(itemName);

        if (player.removeItem(item)) {
            player.getCurrentRoom().addItem(item);
        } else {
            controller.outputGUI("There is no " + itemName + " in your inventory");
            controller.outputGUI("See your inventory with: \"inventory\"");
            return;
        }
        controller.outputGUI("Item has been dropped!");
    }

    private void showInventory(Command command) {
        if (command.hasSecondWord()) {
            controller.outputGUI("Just type inventory man...");
            return;
        }
        controller.outputGUI(player.displayInventory());
    }

    private void heal(Command command) {
        if (!player.checkItems(healthPotion)) {
            controller.outputGUI("You don't have a health potion, maybe you can find one...");
            return;
        }
        if (command.hasSecondWord()) {
            controller.outputGUI("You shouldn't heal anyone else bucko...");
            return;
        }
        if (player.getHealth() >= 100) {
            controller.outputGUI("Lock in unc, you're full health");
        }
        healthPotion.use(player);
    }

    private void picklock(Command command) {
        if (!player.checkItems(lockpick)) {
            controller.outputGUI("You don't have a lockpick in your inventory");
            return;
        }
        if (!command.hasSecondWord()) {
            controller.outputGUI("Pick what?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            controller.outputGUI("There is no door!");
        } else if (!nextRoom.isLocked()) {
            controller.outputGUI("Door is already unlocked...");
        } else if (GameLib.INSTANCE.runGame()) {
            controller.outputGUI("Success! You picked the lock");
            nextRoom.unlock();
        } else {
            controller.outputGUI("Oh no! Your pick broke...");
            player.removeItem(lockpick);
        }
    }

    private void teleport(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Teleport where?");
            return;
        }

        String location = command.getSecondWord();

        Room room = rooms.get(location);

        if (room != null) {
            player.setCurrentRoom(room);
            if (checkWin()) {
                ZorkUL.win();
                controller.outputGUI("You win");
            }
            controller.outputGUI(player.getCurrentRoom().getLongDescription());
            return;
        }
        controller.outputGUI("Room doesn't exist...");
    }

    private void unlock(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Unlock what?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            controller.outputGUI("There is no door!");
        } else if (!nextRoom.isLocked()) {
            controller.outputGUI("Door is already unlocked...");
        } else {
            controller.outputGUI("Door unlocked");
            nextRoom.unlock();
        }
    }

    private void save(Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Save where?");
            return;
        }

        String fileName = command.getSecondWord();
        SaveManager.saveGame(player, rooms, fileName);
        controller.outputGUI("Game saved to " + fileName);
    }

    private void load (Command command) {
        if (!command.hasSecondWord()) {
            controller.outputGUI("Load from where?");
            return;
        }

        String fileName = command.getSecondWord();
        SaveData data = SaveManager.loadGame(fileName);
        if (data == null) {
            controller.outputGUI("Invalid file name");
            return;
        }
        player = data.getPlayer();
        player.setRoomCount(data.getRoomCount());
        player.setInvisLimit(data.getInvisLimit());
        rooms = data.getRooms();
        controller.outputGUI("Loaded game from " + fileName);
        controller.outputGUI(player.getCurrentRoom().getLongDescription());
    }

    @Override
    public void run() {
        printWelcome();

        while (ZorkUL.isRunning()) {
            player.inCombat = player.getCurrentRoom().containsEnemy() && player.isVisable();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        controller.outputGUI("Thank you for playing. Goodbye.");
    }
}