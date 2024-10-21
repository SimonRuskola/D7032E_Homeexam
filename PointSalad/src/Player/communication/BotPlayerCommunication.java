package PointSalad.src.Player.communication;


/**
 * This class represents how a remote player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class BotPlayerCommunication implements IPlayerCommunication {

    @Override
    public void sendMessage(String message) {
        System.out.println("Bot: "+message);
    }

    @Override
    public String readMessage() {
        //System.out.println("Bot is reading message");
        return "Bot read message";
    }

 


}
