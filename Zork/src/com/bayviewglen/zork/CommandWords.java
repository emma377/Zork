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

    /**
     * Constructor - initialise the command words.
     * @throws FileNotFoundException 
     */
    public CommandWords() throws FileNotFoundException{
        // read all the commands from a text file
    	Scanner in = new Scanner(new File("data/commandWords.dat"));
		List<CommandWord> commands = new ArrayList<CommandWord>();

		while(in.hasNext()){
			// Read the Name
			String commanLine = in.nextLine();
			String commandName = commanLine.split(" ")[0];
			String commandType = commanLine.split(" ")[1];
			CommandWord cw = new CommandWord(commandName, commandType);
			commands.add(cw);
		}
		
		validCommands = new CommandWord[commands.size()];
		for (int i=0;i<validCommands.length; i++) {
			validCommands[i] = commands.get(i);
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
            if(validCommands[i].getCommand().equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /*
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(int i = 0; i < validCommands.length; i++)
        {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println();
    }
}