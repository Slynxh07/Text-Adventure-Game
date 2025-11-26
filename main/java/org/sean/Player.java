package org.sean;

import java.util.ArrayList;

public class Player extends Character {
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> inventory;
    private Room currentRoom;
    public boolean inCombat;
    static int roomCount;
    static int invisLimit;

    public Player(String name, Room startingRoom) {
        super(name);
        inCombat = false;
        roomCount = 0;
        invisLimit = 0;
        inventory = new ArrayList<Item>();
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        roomCount++;
        this.currentRoom = room;
        if (!isVisable() && roomCount > invisLimit) {
            visable = true;
        }
    }

    public void takeItem(Item item) {
        inventory.add(item);
    }

    public boolean dropItem(Item item) {
        int index = inventory.indexOf(item);
        if (index < 0) {
            System.out.println("org.sean.zork.Item no in inventory...");
            return false;
        }
        inventory.remove(index);
        return true;
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
            case POISION:
                break;
        }
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public String displayInventory() {
        if (inventory == null || inventory.isEmpty()) {
            return "Your inventory is empty, take items with \"take\"";
        }
        String str = "Current inventory: ";
        for (Item item : inventory) {
            str += String.format("%s ", item.getName());
        }
        return str;
    }

    public boolean checkInventory(Item item) {
        return inventory.contains(item);
    }

    @Override
    public void die() {
        alive = false;
    }
}