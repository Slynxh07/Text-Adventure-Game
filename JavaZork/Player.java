import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<Item> inventory;

    public Player(String name, Room startingRoom) {
        super(name, startingRoom);
        inventory = new ArrayList<Item>();
    }

    void takeItem(Item item) {
        inventory.add(item);
    }

    boolean dropItem(Item item) {
        int index = inventory.indexOf(item);
        if (index < 0) {
            System.out.println("Item no in inventory...");
            return false;
        }
        inventory.remove(index);
        return true;
    }

    void displayInventory() {
        String str = "Current inventory: ";
        for (Item item : inventory) {
            str += String.format("%s ", item.getName());
        }
        System.out.println(str);
    }
}