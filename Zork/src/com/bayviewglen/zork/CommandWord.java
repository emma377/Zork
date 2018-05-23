package com.bayviewglen.zork;

public class CommandWord {
	
	private String command;
	private boolean isNoun;
	private boolean isVerb;
	private boolean isArticle;
	private boolean isPreposistion;
	
	public CommandWord(String commandName, String commandType) {
		super();
		this.command = commandName;
		isVerb = commandType.equals("verb");
		isNoun = commandType.equals("noun");
		isArticle = commandType.equals("article");
		isPreposistion = commandType.equals("isPreposistion");
	}

	public String getCommand() {
		return command;
	}
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

	
}
