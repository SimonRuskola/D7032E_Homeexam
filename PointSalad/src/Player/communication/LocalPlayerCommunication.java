package PointSalad.src.Player.communication;

import java.util.Scanner;

/**
 * This class represents how a local player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class LocalPlayerCommunication implements IPlayerCommunication {

    private Scanner scanner;

    public LocalPlayerCommunication() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * @param message Takes the message the game has sent to the player
     */
    @Override
    public void sendMessage(String message) {
		System.out.println(message);	         
		return;
    }

    /**
     * @return String The string that the player sends for the game to recive as
     *         input
     */
    @Override
    public String readMessage() {
        String word = ""; 
		try {word=scanner.nextLine();} catch(Exception e){}
		return word;
    }





}
