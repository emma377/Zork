package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
* Class Game - the main class of the "Zork" game.
*
* Author:  Michael Kolling
* Version: 1.1
* Date:    March 2000
* 
 *  This class is the main class of the "Zork" application. Zork is a very
*  simple, text based adventure game.  Users can walk around some scenery.
*  That's all. It should really be extended to make it more interesting!
* 
 *  To play this game, create an instance of this class and call the "play"
*  routine.
* 
 *  This main class creates and initialises all the others: it creates all
*  rooms, creates the parser and starts the game.  It also evaluates the
*  commands that the parser returns.
*/

class Game 
{
      private Items item;
    private Parser parser;
    private Room currentRoom;
    private Health health = new Health(); 
    private Inventory i; 
    private Scanner scanner = new Scanner(System.in);
    // This is a MASTER object that contains all of the rooms and is easily accessible.
    // The key will be the name of the room -> no spaces (Use all caps and underscore -> Great Room would have a key of GREAT_ROOM
    // In a hashmap keys are case sensitive.
    // masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great Room (assuming you have one).
    private HashMap<String, Room> masterRoomMap;
    
    private void initRooms(String fileName) throws Exception{
    masterRoomMap = new HashMap<String, Room>();
    Scanner roomScanner;
           try {
                HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();    
                roomScanner = new Scanner(new File(fileName));
                while(roomScanner.hasNext()){
                     Room room = new Room();
                     // Read the Name
                     String roomName = roomScanner.nextLine();
                     room.setRoomName(roomName.split(":")[1].trim());
                     // Read the Description
                     String roomDescription = roomScanner.nextLine();
                     room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
                     // Read the Exits
                     String roomExits = roomScanner.nextLine();
                     //Read the Items
                           //String roomItems = roomScanner.nextLine();
                           //room.setRoomInventory(roomItems.split(":")[1].split(","));
                     // An array of strings in the format E-RoomName
                     String[] rooms = roomExits.split(":")[1].split(",");
                     HashMap<String, String> temp = new HashMap<String, String>(); 
                     for (String s : rooms){
                          temp.put(s.split("-")[0].trim(), s.split("-")[1]);
                     }
                     
                     exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ",  "_"), temp);
                     
                     // This puts the room we created (Without the exits in the masterMap)
                     masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ",  "_"), room);
                     
                     
                     // Now we better set the exits.
                }
                
                for (String key : masterRoomMap.keySet()){
                     Room roomTemp = masterRoomMap.get(key);
                     HashMap<String, String> tempExits = exits.get(key);
                     for (String s : tempExits.keySet()){
                           // s = direction
                           // value is the room.
                           
                           String roomName2 = tempExits.get(s.trim());
                           Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
                         roomTemp.setExit(s.trim().charAt(0), exitRoom);
                           
                     }
                     
                     
                }
    
                roomScanner.close();
           } catch (FileNotFoundException e) {
                e.printStackTrace();
           }
    }    

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        try {
                initRooms("data/Rooms.dat");
                currentRoom = masterRoomMap.get("TEMPLE");
           } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
           }
        parser = new Parser();
    }

    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //System.out.println("Thank you for playing.  Good bye. ");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to our 76th¬ annual Word Challenge.");
        System.out.println("You will find codes in different places. Write them down and remember them.");
        System.out.println("When you have all 9 words (don't forget punctuation), type enter code, and submit your answer, you win the game!");
        System.out.println("You must be in the Temple to submit your final answer.");
        System.out.println();
        System.out.println("Beware! The game makers will not make this easy.");
        System.out.println("You begin on a small pedestal in the middle of the forest. Good luck.");
        health.getHungerBar(); 
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        
        
        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("help")) {
            printHelp();
        }else if (commandWord.equalsIgnoreCase("quit"))
        {
            System.out.println("Quiting is for losers. You're better than that. ");
            return true;
        }else if (commandWord.equalsIgnoreCase("eat")){
             return eat(command);
      }else if (commandWord.equalsIgnoreCase("go")) {
             goRoom(command);
        //}else if (commandWord.equalsIgnoreCase("inventory")) {
             //i.printInventory();
        }else if(commandWord.equalsIgnoreCase("yell")) {
        startYelling(command);
        }else if (commandWord.equalsIgnoreCase("climb")) {
        System.out.println("Sorry, but you are no superhero. You can't climb.");
        }else if (commandWord.equalsIgnoreCase("sing")) {
        System.out.println("Hey now, you're an all-star, get your game on, go play!");
        System.out.println("Oh no! Other players heard you! You better start moving!");
        }else if (commandWord.equalsIgnoreCase("spin")) {
        System.out.println("The world around you is moving at high speeds! You are dizzy now.");
        }else if (commandWord.equalsIgnoreCase("roll")) {
        System.out.println("They see you ROLLIN'! They hatin.");
        }else if (commandWord.equalsIgnoreCase("fight")) {
        System.out.println("Violence is never the answer.");
        }else if (commandWord.equalsIgnoreCase("flip")) {
        System.out.println("You soared high in the sky ... did an amazing flip ... and broke every bone in your body. See you in hell.");
        return gameOver(); 
        }else if (commandWord.equalsIgnoreCase("jump")) {
        System.out.println("You jump up and break you ankle.");
        return gameOver();
        }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Rock Pile")) {
        System.out.println("What can travel around the world while staying in a corner?");
        String a1  = scanner.nextLine();
        if (a1.equalsIgnoreCase("stamp")) {
                   System.out.println("Correct! The first word of the code is 'Computer'");
        }else {
                   System.out.println("Nope, try again! Use your brain this time.");
        }
        
        }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Waterfall")) {
        System.out.println("I am not alive, but I grow. I don't have lungs, but I need oxygen. I don't have a mouth, but water kills me. What am I? The answer is one word");
        String a2  = scanner.nextLine();
        if (a2.equalsIgnoreCase("fire")) {
                   System.out.println("Correct! The second word of the code is 'Science'");
        }else {
                   System.out.println("Nope, try again! Use your brain this time.");
        }
        
        }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Cave")) {
        System.out.println("Which word in the dictionary is spelled incorrectly? The answer is one word.");
        String a3  = scanner.nextLine();
        if (a3.equalsIgnoreCase("incorrectly")) {
                   System.out.println("Correct! The third word of the code is 'is'");
        }else {
                   System.out.println("Nope, try again! Use your brain this time. This might be a trick question. ;)");
        }
        
        }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Campground")) {
        System.out.println("If you have me, you want to share me. If you share me, you haven't got me. What am I? The answer is one word.");
        String a4  = scanner.nextLine();
        if (a4.equalsIgnoreCase("secret")) {
                   System.out.println("Correct! The fourth word of the code is 'the'");
        }else {
                   System.out.println("Nope, try again! Use your brain this time. This might be a trick question. ;)");
        }
        
        }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Airplane")) {
        System.out.println("He has married many women, but has never been married. Who is he? The answer is one word.");
        String a5  = scanner.nextLine();
        if (a5.equalsIgnoreCase("priest")) {
                   System.out.println("Correct! The fifth word of the code is 'best!'");
        }else {
                   System.out.println("Nope, try again! Use your brain this time.");
        
         }
        
       }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Wreckage")) {
             System.out.println("What invention lets you look right through a wall? The answer is one word.");
             String a6  = scanner.nextLine();
             if (a6.equalsIgnoreCase("window")) {
                   System.out.println("Correct! The sixth word of the code is 'WOO'");
             }else {
                   System.out.println("Nope, try again! Use your brain this time.");
             }
             
       }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Cabin")) {
             System.out.println("What is always coming but never arrives? The answer is one word.");
             String a7  = scanner.nextLine();
             if (a7.equalsIgnoreCase("tomorrow")) {
                   System.out.println("The riddle is correct, but this is not part of the code. We tricked you. Keep looking around for code words.");
             }else {
                   System.out.println("Nope, try again! Use your brain this time.");
             } 
             
       }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Birch Forest")) {
             System.out.println("Take off my skin - I won't cry, but you will! What am I? The answer is one word.");
             String a8  = scanner.nextLine();
             if (a8.equalsIgnoreCase("onion")) {
                   System.out.println("The riddle is correct, but this is not part of the code. We tricked you. Keep looking around for code words.");
             }else {
                   System.out.println("Nope, try again! Use your brain this time.");
             } 
             
       }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("Mountain")) {
             System.out.println("What can you catch but not throw? The answer is one word.");
             String a9  = scanner.nextLine();
             if (a9.equalsIgnoreCase("illness")) {
                   System.out.println("Correct! The seventh word of the code is 'HOO!'");
             }else {
                   System.out.println("Nope, try again! Use your brain this time.");
             }
             
          }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("cellar")) {
             System.out.println("I'm tall when I'm young and I'm short when I'm old. What am I? The answer is one word.");
             String a10  = scanner.nextLine();
             if (a10.equalsIgnoreCase("candle")) {
                      System.out.println("The riddle is correct, but this is not part of the code. We tricked you. Keep looking around for code words.");
             }else {
                             System.out.println("Nope, try again! Use your brain this time.");
            }
             
          }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("narrow passage")) {
            System.out.println("What has a head and a tail, but no body? The answer is one word.");
            String a11  = scanner.nextLine();
            if (a11.equalsIgnoreCase("coin")) {
                      System.out.println("The riddle is correct, but this is not part of the code. We tricked you. Keep looking around for code words.");
            }else {
                             System.out.println("Nope, try again! Use your brain this time.");
           }
            
          }else if (commandWord.equalsIgnoreCase("read") && currentRoom.getRoomName().equalsIgnoreCase("temple")) {
            System.out.println("What gets wetter and wetter the more it dries? The answer is one word.");
            String a12  = scanner.nextLine();
            if (a12.equalsIgnoreCase("towel")) {
                      System.out.println("The riddle is correct, but this is not part of the code. We tricked you. Keep looking around for code words.");
            }else {
                             System.out.println("Nope, try again! Use your brain this time.");
            }
         }else if(commandWord.equalsIgnoreCase("enter") && currentRoom.getRoomName().equalsIgnoreCase("temple")){
              winGame();
         }else if(commandWord.equalsIgnoreCase("enter") && !(currentRoom.getRoomName().equalsIgnoreCase("temple"))) {
              System.out.println("You need to be in the temple");
         }
             
       
        return false;
        
    }


    

     

      private boolean eat(Command command) {
             //if()
      if(currentRoom.getRoomName().equalsIgnoreCase("Orange Grove")) {
                   if(!command.hasSecondWord()) {
                         System.out.println("Eat what?");
                          return false;
                   }else if(command.getSecondWord().equals("oranges")) {
                         System.out.println("You eat the oranges, which were poisonous. Oops.");
                     return gameOver();
                   }
             }else{
                   System.out.println("Do you really think you should be eating at a time like this?");
                   return false;
             }
             return false;
              
      }

      private void startYelling(Command command) {
           System.out.println("With your biggest outdoor voice you yell...");
     System.out.println(command.getSecondWord());
           System.out.println("Feel better?");
           
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
        System.out.println("around an endless maze.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    private boolean gameOver() {
    System.out.println("When life gave you lemons...");
    System.out.println("You couldn't make lemonade"); 
    System.out.println("You had one job.");
    System.out.println("And you failed.");
    System.out.println("GAME OVER");
    return true;
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);

        if (nextRoom == null)
            System.out.println("Not that way");
        else 
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.longDescription());
        }
    }
    
    private void winGame() {
          Scanner scanner = new Scanner(System.in);
          System.out.println("Enter the code: ");
          String a  = scanner.nextLine();
          if (a.equals("Computer Science is the best! WOO HOO!"+"stamp fire incorrectly secret priest window tomorrow onion illness")) {
                 System.out.println("YOU ARE THE WINNER! WOO HOO! Go buy yourself a McDonalds Happy Meal to celebrate your win.");
                 
          }else {
                 System.out.println("mmmm. That's incorrect. Do you want to try again?");
                 if(scanner.nextLine().equals("yes")) {
                      winGame();
                 }
          }
    
    }
      
}

