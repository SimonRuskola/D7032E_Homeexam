package PointSalad.src.Player.communication;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents how a local player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class OnlinePlayerCommunication implements IPlayerCommunication {

	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	

    public OnlinePlayerCommunication(ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        
    }

    /**
     * @param message Takes the message the game has sent to the player
     */
    @Override
    public void sendMessage(String message) {
		try {
			outToClient.writeObject(message);
		} catch (Exception e) {}
		
    }

    /**
     * @return String The string that the player sends for the game to recive as
     *         input
     */
    @Override
    public String readMessage() {
        String word = ""; 
		try{word = (String) inFromClient.readObject();} catch (Exception e){}
		return word;
    }

}
