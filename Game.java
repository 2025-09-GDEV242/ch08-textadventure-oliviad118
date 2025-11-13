/**
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a text based adventure game where players navigate
 * through various rooms, collect items, and explore the game world.
 * 
 * To play this game, create an instance of this class and call the "play"
 * method, or run the main method directly.
 * 
 * This main class creates and initializes all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Michael Kölling, David J. Barnes, oliviad118
 * @version 2025.11.11
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    
    /**
     * Main method to run the game outside of BlueJ.
     * Creates a new game instance and starts the game.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     * The game world now contains at least 8 interconnected rooms.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, library, cafeteria, garden, basement;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in the university library");
        cafeteria = new Room("in the student cafeteria");
        garden = new Room("in the university garden");
        basement = new Room("in the basement storage room");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", garden);

        theater.setExit("west", outside);
        theater.setExit("south", library);

        pub.setExit("east", outside);
        pub.setExit("south", cafeteria);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("down", basement);

        office.setExit("west", lab);
        office.setExit("south", library);

        library.setExit("north", theater);
        library.setExit("west", office);
        library.setExit("south", cafeteria);

        cafeteria.setExit("north", pub);
        cafeteria.setExit("east", library);

        garden.setExit("south", outside);

        basement.setExit("up", lab);

        // Add items to rooms
        outside.addItem(new Item("map", 0.05));
        outside.addItem(new Item("backpack", 0.5));
        
        theater.addItem(new Item("notebook", 0.2));
        theater.addItem(new Item("pen", 0.01));
        
        pub.addItem(new Item("key", 0.1));
        
        lab.addItem(new Item("laptop", 2.5));
        lab.addItem(new Item("mouse", 0.15));
        
        office.addItem(new Item("stapler", 0.3));
        
        library.addItem(new Item("book", 0.8));
        library.addItem(new Item("bookmark", 0.005));
        
        cafeteria.addItem(new Item("tray", 0.4));
        
        garden.addItem(new Item("flower", 0.02));
        garden.addItem(new Item("cookie", 0.01)); // Magic cookie!
        
        basement.addItem(new Item("flashlight", 0.25));
        basement.addItem(new Item("toolbox", 3.0));

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look(command);
                break;
                
            case TAKE:
                takeItem(command);
                break;
                
            case DROP:
                dropItem(command);
                break;
                
            case INVENTORY:
                showInventory(command);
                break;
                
            case ITEMS:
                showItems(command);
                break;
                
            case EAT:
                eatItem(command);
                break;
                
            case BACK:
                goBack(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command containing the direction to go
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // Add current room to history before moving
            player.addToHistory(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Look around the current room and display its description.
     * This command shows the long description of the current room.
     * 
     * @param command The look command (second word ignored)
     */
    private void look(Command command)
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Try to take an item from the current room.
     * 
     * @param command The take command containing the item to take
     */
    private void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = currentRoom.getItem(itemName);
        
        if(item == null) {
            System.out.println("There is no " + itemName + " here.");
        }
        else {
            if(player.addItem(item)) {
                currentRoom.removeItem(item);
                System.out.println("You picked up the " + itemName + ".");
            }
            else {
                System.out.println("The " + itemName + " is too heavy to carry.");
                System.out.println("You need " + (player.getTotalWeight() + item.getWeight() - player.getMaxWeight()) 
                                 + "kg more capacity.");
            }
        }
    }
    
    /**
     * Try to drop an item in the current room.
     * 
     * @param command The drop command containing the item to drop
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = player.getItem(itemName);
        
        if(item == null) {
            System.out.println("You don't have a " + itemName + ".");
        }
        else {
            player.removeItem(item);
            currentRoom.addItem(item);
            System.out.println("You dropped the " + itemName + ".");
        }
    }
    
    /**
     * Show the player's inventory.
     * 
     * @param command The inventory command (second word ignored)
     */
    private void showInventory(Command command)
    {
        System.out.println(player.getInventoryString());
    }
    
    /**
     * Go back to the previous room. If the command has a second word,
     * try to go back that many steps.
     * 
     * @param command The back command (optional second word for number of steps)
     */
    private void goBack(Command command)
    {
        int steps = 1; // default to one step
        
        if(command.hasSecondWord()) {
            try {
                steps = Integer.parseInt(command.getSecondWord());
                if(steps <= 0) {
                    System.out.println("Please specify a positive number of steps.");
                    return;
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Please specify a valid number of steps.");
                return;
            }
        }
        
        for(int i = 0; i < steps; i++) {
            Room previousRoom = player.getPreviousRoom();
            
            if(previousRoom == null) {
                if(i == 0) {
                    System.out.println("You can't go back any further.");
                } else {
                    System.out.println("You went back " + i + " step(s), but can't go back any further.");
                }
                return;
            }
            else {
                currentRoom = previousRoom;
            }
        }
        
        if(steps == 1) {
            System.out.println("You went back.");
        } else {
            System.out.println("You went back " + steps + " step(s).");
        }
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Show all items currently carried and their total weight (8.32).
     * 
     * @param command The items command (second word ignored)
     */
    private void showItems(Command command)
    {
        System.out.println(player.getInventoryString());
    }
    
    /**
     * Try to eat an item from the player's inventory.
     * Special handling for magic cookie which increases carrying capacity.
     * 
     * @param command The eat command containing the item to eat
     */
    private void eatItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = player.getItem(itemName);
        
        if(item == null) {
            System.out.println("You don't have a " + itemName + " to eat.");
        }
        else if(itemName.equals("cookie")) {
            // Special magic cookie handling
            player.removeItem(item);
            player.increaseMaxWeight(2.0); // Increase capacity by 2kg
            System.out.println("You ate the magic cookie! You feel stronger!");
            System.out.println("Your carrying capacity has increased by 2kg!");
            System.out.println("New maximum capacity: " + player.getMaxWeight() + "kg");
        }
        else {
            System.out.println("You can't eat the " + itemName + ".");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command The quit command to process
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
