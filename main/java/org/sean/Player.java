package org.sean;

import java.util.ArrayList;

public class Player extends Character {
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> inventory;
    private Room currentRoom;

    public Player(String name, Room startingRoom) {
        super(name);
        inventory = new ArrayList<Item>();
        this.currentRoom = startingRoom;
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