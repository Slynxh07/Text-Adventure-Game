package org.sean;

import java.io.Serial;
import java.util.ArrayList;

public class Player extends Character {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Item> inventory;
    private Room currentRoom;
    public boolean inCombat;
    private boolean poisoned;
    private static int roomCount;
    private static int invisLimit;
    private static int turnCount;
    private static int poisonLimit;

    public Player(String name, Room startingRoom) {
        super(name);
        inCombat = false;
        roomCount = 0;
        invisLimit = 0;
        turnCount = 0;
        poisonLimit = 0;
        inventory = new ArrayList<Item>();
        this.currentRoom = startingRoom;
        poisoned = false;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (isPoisoned() && turnCount >= poisonLimit) {
            poisoned = false;
        }
        turnCount++;
        if (health <= 0) {
            die();
        }
    }

    public void poison() {
        health -= 5;
    }

    public int getPoisonLimit() {
        return poisonLimit;
    }

    public void setPoisonLimit(int poisonLimit) {
        Player.poisonLimit = poisonLimit;
    }

    public int getTurnCount() { return turnCount; }

    public void setTurnCount(int turnCount) { Player.turnCount = turnCount; }

    public void setRoomCount(int roomCount) {
        Player.roomCount = roomCount;
    }

    public void setInvisLimit(int invisLimit) {
        Player.invisLimit = invisLimit;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public int getInvisLimit() {
        return invisLimit;
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
                poisoned = false;
                if (health > 100) health = 100;
                break;
            case IVISABILITY:
                visable = false;
                invisLimit = roomCount;
                break;
            case POISON:
                poisoned = true;
                poisonLimit = turnCount + 1;
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