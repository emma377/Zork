package com.bayviewglen.zork;

public class CommandWord {
      
      private String command;
      
      private String type;
      /*
      private boolean isNoun;
      private boolean isVerb;
      private boolean isArticle;
      private boolean isPreposistion;
      
      */
      
      public CommandWord(String commandName) {
             super(); 
             this.command = commandName;
             /*
             this.type = commandType;
             isVerb = commandType.equals("verb");
             isNoun = commandType.equals("noun");
             isArticle = commandType.equals("article");
             isPreposistion = commandType.equals("preposistion");
             */
      }
      

      public String getCommandWord() {
             return command;
      }
      public String getCommandType() {
             return type;
      }
      /*
      public boolean isNoun() {
             return isNoun;
      }

      public boolean isVerb() {
             return isVerb;
      }
      public boolean isArticle() {
             return isArticle;
      }
      public boolean isPreposistion() {
             return isPreposistion;
      }
      */
      
    public boolean isUnknown()
    {
        return (command == null);
    }
    
    public boolean isUnknown(String aString) {
      if(aString != command || command == null)
             return true;
      return false;
    }
    
      
      
    
}

