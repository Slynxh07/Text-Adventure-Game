package org.sean;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("look", "Look around");
        validCommands.put("eat", "Eat something");
        validCommands.put("take", "Place an item into your inventory");
        validCommands.put("drop", "Drop and item from your inventory");
        validCommands.put("inventory", "Display inventory");
        validCommands.put("items", "Display items in the room");
        validCommands.put("sword", "Attack enemy");
        validCommands.put("heal", "Use a health potion");
        validCommands.put("cheats", "Enable/Disable cheats");
        validCommands.put("lockpick", "Use lock pick to pick locks");
        validCommands.put("nullis", "Use invisibility potion");
        validCommands.put("inspect", "Inspect items and rooms");
        validCommands.put("loot", "Loot items from chests");
        validCommands.put("speak", "Speak with NPCs");
    }

    public void enableCheats() {
        validCommands.put("teleport", "Teleport to desired room");
        validCommands.put("kill", "Kill enemies in one shot");
    }

    public void disableCheats() {
        validCommands.remove("teleport");
        validCommands.remove("kill");
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public String showAll() {
        String retStr = "Valid commands are: ";
        for (String command : validCommands.keySet()) {
            retStr += command + " ";
        }
        return retStr;
    }
}
