package org.sean;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String description;
    private final Map<String, Room> exits; // Map direction to neighboring org.sean.zork.Room
    private Character npc;
    private boolean locked;
    private final ArrayList<Item> items;
    private Chest<Storable> chest;

    public Room(String description, boolean locked) {
        items = new ArrayList<Item>();
        this.description = description;
        this.locked = locked;
        exits = new HashMap<>();
        chest = null;
        npc = null;
    }

    public Room(String description, boolean locked, Character character) {
        this(description, locked);
        this.npc = character;
    }

    public Room(String description, boolean locked, Item... items) {
        this(description, locked);
        for (Item item : items) {
            addItem(item);
        }
    }

    public Chest<Storable> getChest() { return chest; };

    public Room(String description, boolean locked, Character character, Item... items) {
        this(description, locked, character);
        for (Item item : items) {
            addItem(item);
        }
    }

    public void setCharacter(Character character) {npc = character; }

    public boolean containsEnemy() {
        return npc != null && npc instanceof Enemy;
    }

    public void unlock() {
        this.locked = false;
    }

    public void setChest(Storable item) {
        chest = new Chest<>();
        chest.storeItem(item);
    }

    public boolean hasChest() {
        return chest != null;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public Character getCharacter() {
        return npc;
    }

    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
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

    public String displayItems() {
        if (items.isEmpty()) {
            return "There are no available items in this room";
        }
        String str = "Items available in this room: ";
        for (Item item : items) {
            str += String.format("%s ", item.getName());
        }
        return str;
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }

    public boolean removeItem(Item item) {
        if (!checkItems(item)) {
            return false;
        }
        items.remove(item);
        return true;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean checkItems(Item item) {
        return items.contains(item);
    }
}
