/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a 
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting, 
emphasizing exploration and simple command-driven gameplay
*/

import java.util.Scanner;

/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
 */

public class ZorkULGame /*extends Application */ {
    private Parser parser;
    private Player player;

    private Potion healthPotion;
    private Potion invisPotion;

    public ZorkULGame() {
        createRooms();
        parser = new Parser();
    }
/*
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Label("Hello JavaFX!"), 300, 200));
        stage.show();
    }
 */

    private void createRooms() {
        healthPotion = new Potion("Health potion", "heals +30% of max health", "healing");
        invisPotion = new Potion("Invisibility potion", "Makes user invisible to npcs for 1 room", "invisibility");

        Room cell, hall, diningHall, guardOffice, sewer1, sewer2, artifactRoom, mainHall;

        // create rooms
        cell = new Room("Dark dreary cell - lock picks 3x", healthPotion);
        hall = new Room("Cold empty hall - sewer entrance");
        diningHall = new Room("Disgusting rodent-infested dining hall - npc with warning");
        guardOffice = new Room("Eerily quiet guard office - sword and health potion");
        sewer1 = new Room("Grim grotesque sewer");
        sewer2 = new Room("Grim grotesque sewer - Enemy spider");
        artifactRoom = new Room("Curious looking artifact room - invis potion and health potion");
        mainHall = new Room("Main hall - 3 guards");


        // initialise room exits
        cell.setExit("south", hall);

        hall.setExit("south", mainHall);
        hall.setExit("east", guardOffice);
        hall.setExit("west", diningHall);
        hall.setExit("down", sewer1);
        hall.setExit("north", cell);

        diningHall.setExit("east", hall);

        guardOffice.setExit("west", hall);

        sewer1.setExit("up", hall);
        sewer1.setExit("south", sewer2);

        sewer2.setExit("north", sewer1);
        sewer2.setExit("up", artifactRoom);

        artifactRoom.setExit("down", sewer2);
        artifactRoom.setExit("west", mainHall);

        mainHall.setExit("north", hall);
        mainHall.setExit("east", artifactRoom);

        // create the player character and start outside
        player = new Player("player", cell);
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
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
                goRoom(command);
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

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();

        Item item = null;

        switch (itemName) {
            case "health-potion":
                item = healthPotion;
                break;
            default:
                System.out.println("No such item exists!");
                return;
        }

        if (player.getCurrentRoom().removeItem(item)) {
            player.takeItem(item);
        } else {
            System.out.println("There is no " + item.getName() + " here");
            return;
        }
        System.out.println("You picked up an item");
    }

    private void drop (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
        }

        String itemName = command.getSecondWord();

        Item item = null;

        switch (itemName) {
            case "health-potion":
                item = healthPotion;
                break;
            default:
                System.out.println("No such item exists!");
                return;
        }

        if (player.dropItem(item)) {
            player.getCurrentRoom().addItem(item);
        } else {
            System.out.println("There is no " + item.getName() + " in your inventory");
            System.out.println("See your inventory with: \"inventory\"");
            return;
        }
        System.out.println("Item has been dropped!");
    }

    private void showInventory(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Just type inventory man...");
            return;
        }
        player.displayInventory();
    }

    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        //launch();
        game.play();
    }
}