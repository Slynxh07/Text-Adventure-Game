import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String description;
    private Map<String, Room> exits; // Map direction to neighboring Room
    private ArrayList<Item> items;

    public Room(String description, Item... items) {
        this.description = description;
        exits = new HashMap<>();
        this.items = new ArrayList<Item>();
        for (Item item : items) {
            this.items.add(item);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        int index = items.indexOf(item);
        if (index < 0) {
            return false;
        }
        items.remove(index);
        return true;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }
}
