package com.bayviewglen.zork;
/*
* Author:  Michael Kolling
* Version: 1.0
* Date:    July 1999
* 
 * This class is part of Zork. Zork is a simple, text based adventure game.
*
* This parser reads user input and tries to interpret it as a "Zork"
* command. Every time it is called it reads a line from the terminal and
* tries to interpret the line as a two word command. It returns the command
* as an object of class Command.
*
* The parser has a set of known command words. It checks user input against
* the known commands, and if the input is not one of the known commands, it
* returns a command object that is marked as an unknown command.
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Parser 
{

    private CommandWords commands;  // holds all valid command words
    
    //private ArrayList<String>
    
    public Parser() 
    {
        try {
                                         commands = new CommandWords();
                           } catch (FileNotFoundException e) {
                                         // TODO Auto-generated catch block
                                         e.printStackTrace();
                           }
    }

    public Command getCommand() 
    {
        String inputLine = "";   // will hold the full input line
        String word1;
        String word2;

        System.out.print("> ");     // print prompt

        BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.out.println ("There was an error during reading: "
                                + exc.getMessage());
        }
        
        //winGame(inputLine);
        
        
        
        
        StringTokenizer tokenizer = new StringTokenizer(inputLine);
        ArrayList<String> tokens = new ArrayList<String>();
        
        while(tokenizer.hasMoreTokens())
              tokens.add(tokenizer.nextToken());
        
        
        
        for(int i=0;i<tokens.size();i++) {
              if(isForbiddenWord(tokens.get(i)))
                            tokens.remove(i);                         
        }
        
        if(tokens.size()>0)
              word1 = tokens.get(0);
        else
              word1 = null;
        if(tokens.size()>1)
              word2 = tokens.get(1);
        else
              word2 = null;
        

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "nil" command (for unknown command).

        if(commands.isCommand(word1))
            return new Command(word1, word2);
        else
            return new Command(null, word2);
            
              
    }
    
    

              private boolean isForbiddenWord(String string) {
              if(string.equalsIgnoreCase("the")||string.equalsIgnoreCase("an")||string.equalsIgnoreCase("on")||string.equalsIgnoreCase("a")||string.equalsIgnoreCase("to"))
                                         return true;
                           else
                                         return false;
              }

              /*
    //determines if you've won the game
    private void winGame(String inputLine) {
              if(inputLine.equals("Computer Science is so much fun! Woo Hoo!")) {
            System.out.println("YOU ARE THE WINNER! WOO HOO! Go buy yourself a McDonalds Happy Meal to celebrate you win.");
        }else {
            System.out.println("mmmm. That's incorrect. Try again.");
        }
                           
              }

              /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
