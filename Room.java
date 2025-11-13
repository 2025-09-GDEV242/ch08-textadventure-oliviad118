import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game. It is 
 * connected to other rooms via exits. For each existing exit, the room 
 * stores a reference to the neighboring room. Rooms can also contain
 * items that players can interact with.
 * 
 * @author Michael Kölling, David J. Barnes, oliviad118
 * @version 2025.11.11
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;              // stores items in this room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items here: key (weight: 0.1kg) map (weight: 0.05kg)
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + getItemString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Add an item to this room.
     * @param item The item to add to the room
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Remove an item from this room.
     * @param item The item to remove from the room
     * @return true if the item was removed, false if it wasn't in the room
     */
    public boolean removeItem(Item item)
    {
        return items.remove(item);
    }
    
    /**
     * Get a list of all items in this room.
     * @return An ArrayList of items in this room
     */
    public ArrayList<Item> getItems()
    {
        return new ArrayList<>(items); // return a copy to prevent external modification
    }
    
    /**
     * Find an item in this room by description.
     * @param description The description of the item to find
     * @return The item if found, null otherwise
     */
    public Item getItem(String description)
    {
        for(Item item : items) {
            if(item.getDescription().equals(description)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Get a string listing all items in this room.
     * @return A string describing items in the room, or empty string if no items
     */
    public String getItemString()
    {
        if(items.isEmpty()) {
            return "";
        }
        
        String itemString = "\nItems here: ";
        for(Item item : items) {
            itemString += item.toString() + " ";
        }
        return itemString;
    }
}

