import java.util.ArrayList;

public class Character {
    private String name;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        inventory = new ArrayList<Item>();
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You moved to: " + currentRoom.getDescription());
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public void inflictEffect(String effect) {
        System.out.println(name + "has been inflicted with " + effect);
    }
}
