package org.sean;

import java.util.ArrayList;

public class Player extends Character {
    //private static final long serialVersionUID = 1L;
    private ArrayList<Item> inventory;
    private Room currentRoom;
    public boolean inCombat;
    private boolean poisoned;
    private static int roomCount;
    private static int invisLimit;

    public Player(String name, Room startingRoom) {
        super(name);
        inCombat = false;
        roomCount = 0;
        invisLimit = 0;
        inventory = new ArrayList<Item>();
        this.currentRoom = startingRoom;
        poisoned = false;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public boolean removeItem(Item item) {
        if (!checkItems(item)) {
            return false;
        }
        inventory.remove(item);
        return true;
    }
    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean checkItems(Item item) {
        return inventory.contains(item);
    }

    public void setCurrentRoom(Room room) {
        roomCount++;
        this.currentRoom = room;
        if (!isVisable() && roomCount > invisLimit) {
            visable = true;
        }
    }

    public void inflictEffect(Effects effect) {
        switch (effect) {
            case HEALING:
                health += 30;
                if (health > 100) health = 100;
                break;
            case IVISABILITY:
                visable = false;
                invisLimit = roomCount;
                break;
            case POISON:
                poisoned = true;
                break;
        }
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public String displayInventory() {
        if (inventory.isEmpty()) {
            return "Your inventory is empty, take items with \"take\"";
        }
        String str = "Current inventory: ";
        for (Item item : inventory) {
            str += String.format("%s ", item.getName());
        }
        return str;
    }

    @Override
    public void die() {
        alive = false;
    }
}