package com.bayviewglen.zork;
/*
* Author:  Michael Kolling.
* Version: 1.0
* Date:    July 1999
* 
 * This class holds an enumeration of all command words known to the game.
* It is used to recognise commands as they are typed in.
*
* This class is part of the "Zork" game.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CommandWords
{
    // a constant array that holds all valid command words
    private static CommandWord[] validCommands;
    private List<CommandWord> commands = new ArrayList<CommandWord>();
    /**
     * Constructor - initialise the command words.
     * @throws FileNotFoundException 
     */
    public CommandWords() throws FileNotFoundException{
        // read all the commands from a text file
      try {
             Scanner in = new Scanner(new File("data/commandWords.dat"));
             
             
             while(in.hasNext()){
                   // Read the Name
                   String commandName = in.nextLine();
                   //String commandName = commanLine;
                   //String commandType = commanLine.split(" ")[1];
                   CommandWord cw = new CommandWord(commandName);
                   commands.add(cw);
                   
             }
             
             validCommands = new CommandWord[commands.size()];
             for (int i=0;i<validCommands.length; i++) {
                   validCommands[i] = commands.get(i);
             }
             in.close();
      }catch (FileNotFoundException e) {
                   e.printStackTrace();
             }
    }

    /**
     * Check whether a given String is a valid command word. 
     * Return true if it is, false if it isn't.
     **/
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++)
        {
            if(validCommands[i].getCommandWord().equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    /*
    public CommandWord getValidCommand(String aString)
    {
      for(int i = 0; i < validCommands.length; i++)
        {
            if(validCommands[i].getCommandWord().equals(aString))
                return validCommands[i];
        }
        // if we get here, the string was not found in the commands
        return null;
    }
    
    /*
     *Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(int i = 0; i < commands.size(); i++)
        {
            System.out.print(commands.get(i).getCommandWord() + "  ");
        }
        System.out.println();
    }
}
