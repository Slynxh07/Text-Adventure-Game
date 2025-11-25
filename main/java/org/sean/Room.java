package org.sean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room implements Serializable {
    private String description;
    private Map<String, Room> exits; // Map direction to neighboring org.sean.zork.Room
    private ArrayList<Item> items;
    private Character npc;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        this.items = new ArrayList<Item>();
    }

    public Room(String description, Character character) {
        this(description);
        this.npc = character;
    }

    public Room(String description, Item... items) {
        this(description);
        for (Item item : items) {
            this.items.add(item);
        }
    }

    public Room(String description, Character character, Item... items) {
        this(description, character);
        for (Item item : items) {
            this.items.add(item);
        }
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

    public String displayItems() {
        if (items == null || items.isEmpty()) {
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
}
