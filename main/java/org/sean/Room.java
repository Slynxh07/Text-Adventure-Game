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
    private String name;

    public Room(String description, String name, boolean locked) {
        items = new ArrayList<Item>();
        this.description = description;
        this.locked = locked;
        exits = new HashMap<>();
        chest = null;
        npc = null;
        this.name = name;
    }

    public Room(String description, String name, boolean locked, Character character) {
        this(description, name, locked);
        this.npc = character;
    }

    public Room(String description, String name, boolean locked, Item... items) {
        this(description, name, locked);
        for (Item item : items) {
            addItem(item);
        }
    }

    public Room(String description, String name, boolean locked, Character character, Item... items) {
        this(description, name, locked, character);
        for (Item item : items) {
            addItem(item);
        }
    }

    public String getName() { return name; }

    public Chest<Storable> getChest() { return chest; };

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
        return "You are in " + description + ".\nExits: " + getExitString();
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
