
package PointSalad.src.Player.communication;

/**
 * This interface handles how each player communicates with for example the
 * game. 
 */
public interface IPlayerCommunication {
    // Send a message to the player
    void sendMessage(String message);

    // Receive input (text) from the player
    String readMessage();

   
}
