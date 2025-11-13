/**
 * This class represents an item in the World of Zuul adventure game.
 * 
 * Items have a description and a weight. They can be found in rooms
 * and picked up by players. Items provide interactive elements to
 * enhance the gaming experience.
 * 
 * @author oliviad118
 * @version 2025.11.11
 */
public class Item
{
    private String description;
    private double weight;

    /**
     * Create an item with a description and weight.
     * 
     * @param description A description of the item
     * @param weight The weight of the item in kilograms
     */
    public Item(String description, double weight)
    {
        this.description = description;
        this.weight = weight;
    }

    /**
     * Get the description of this item.
     * 
     * @return The item's description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get the weight of this item.
     * 
     * @return The item's weight in kilograms
     */
    public double getWeight()
    {
        return weight;
    }
    
    /**
     * Get a string representation of the item including its description and weight.
     * 
     * @return A string representation of the item
     */
    @Override
    public String toString()
    {
        return description + " (weight: " + weight + "kg)";
    }
}