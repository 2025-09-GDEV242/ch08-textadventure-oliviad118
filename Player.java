import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents a player in the World of Zuul adventure game.
 * 
 * A player can carry items and navigate through the game world.
 * The player maintains an inventory of items and tracks movement history
 * using a Stack for the back command functionality (Exercise 8.26).
 * The Stack ensures proper LIFO (Last In, First Out) behavior for room navigation.
 * 
 * @author oliviad118
 * @version 2025.11.12
 */
public class Player
{
    private ArrayList<Item> inventory;
    private Stack<Room> roomHistory;  // Using Stack for proper LIFO behavior
    private double maxWeight;
    
    /**
     * Create a new player with an empty inventory and no room history.
     * Sets initial maximum carrying capacity to 5.0 kg.
     */
    public Player()
    {
        inventory = new ArrayList<>();
        roomHistory = new Stack<>();  // Initialize as Stack
        maxWeight = 5.0; // Initial carrying capacity
    }
    
    /**
     * Add an item to the player's inventory if weight allows.
     * @param item The item to add
     * @return true if item was added, false if too heavy
     */
    public boolean addItem(Item item)
    {
        if (getTotalWeight() + item.getWeight() <= maxWeight) {
            inventory.add(item);
            return true;
        }
        return false;
    }
    
    /**
     * Remove an item from the player's inventory.
     * @param item The item to remove
     * @return true if the item was removed, false if it wasn't in inventory
     */
    public boolean removeItem(Item item)
    {
        return inventory.remove(item);
    }
    
    /**
     * Get a copy of the player's inventory.
     * @return An ArrayList of items in the player's inventory
     */
    public ArrayList<Item> getInventory()
    {
        return new ArrayList<>(inventory);
    }
    
    /**
     * Find an item in the player's inventory by description.
     * @param description The description of the item to find
     * @return The item if found, null otherwise
     */
    public Item getItem(String description)
    {
        for(Item item : inventory) {
            if(item.getDescription().equals(description)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Get a string representation of the player's inventory.
     * @return A string listing all items in inventory with weight info
     */
    public String getInventoryString()
    {
        if(inventory.isEmpty()) {
            return "Your inventory is empty.";
        }
        
        String inventoryString = "You are carrying: ";
        for(Item item : inventory) {
            inventoryString += item.toString() + " ";
        }
        inventoryString += "\nTotal weight: " + getTotalWeight() + "kg / " + maxWeight + "kg";
        inventoryString += "\nRemaining capacity: " + getRemainingCapacity() + "kg";
        return inventoryString;
    }
    
    /**
     * Calculate the total weight of items in the player's inventory.
     * @return The total weight of all items
     */
    public double getTotalWeight()
    {
        double totalWeight = 0.0;
        for(Item item : inventory) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    /**
     * Get the maximum weight the player can carry.
     * @return The maximum carrying capacity in kg
     */
    public double getMaxWeight()
    {
        return maxWeight;
    }
    
    /**
     * Set the maximum weight the player can carry.
     * @param newMaxWeight The new maximum carrying capacity in kg
     */
    public void setMaxWeight(double newMaxWeight)
    {
        maxWeight = newMaxWeight;
    }
    
    /**
     * Increase the maximum weight the player can carry.
     * @param increase The amount to increase capacity by in kg
     */
    public void increaseMaxWeight(double increase)
    {
        maxWeight += increase;
    }
    
    /**
     * Get the remaining carrying capacity.
     * @return The remaining weight capacity in kg
     */
    public double getRemainingCapacity()
    {
        return maxWeight - getTotalWeight();
    }
    
    /**
     * Add a room to the player's movement history using Stack.push().
     * @param room The room to add to history
     */
    public void addToHistory(Room room)
    {
        roomHistory.push(room);  // Use Stack's push method
    }
    
    /**
     * Get the previous room from history and remove it using Stack.pop().
     * @return The previous room, or null if no history
     */
    public Room getPreviousRoom()
    {
        if(roomHistory.isEmpty()) {
            return null;
        }
        return roomHistory.pop();  // Use Stack's pop method
    }
    
    /**
     * Clear the room history using Stack.clear().
     */
    public void clearHistory()
    {
        roomHistory.clear();  // Clear the stack
    }
    
    /**
     * Check if there is room history using Stack.isEmpty().
     * @return true if there is history, false otherwise
     */
    public boolean hasHistory()
    {
        return !roomHistory.isEmpty();  // Use Stack's isEmpty method
    }
}