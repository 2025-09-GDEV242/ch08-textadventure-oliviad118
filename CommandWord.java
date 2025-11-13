/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * This enumeration defines all the valid commands that a player
 * can use in the World of Zuul adventure game.
 * 
 * @author Michael Kölling, David J. Barnes, oliviad118
 * @version 2025.11.11
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), LOOK("look"), 
    TAKE("take"), DROP("drop"), INVENTORY("inventory"), BACK("back"), 
    ITEMS("items"), EAT("eat"), UNKNOWN("?");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * Get the command word as a string.
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
