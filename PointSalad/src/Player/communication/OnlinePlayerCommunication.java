package PointSalad.src.Player.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
/**
 * This class represents how a local player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class OnlinePlayerCommunication implements IPlayerCommunication {

	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
  private Socket socket;
	

    public OnlinePlayerCommunication(Socket socket) {

      this.socket = socket;
      try {
        this.inFromClient = new ObjectInputStream(socket.getInputStream());
        this.outToClient = new ObjectOutputStream(socket.getOutputStream());
      } catch (IOException e) {
          e.printStackTrace();
      }
     
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
