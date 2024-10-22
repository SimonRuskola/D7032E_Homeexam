package PointSalad.src.Player.communication;


//This class handles how the bot communicates with the game
// used for testing purposes

public class BotPlayerCommunication implements IPlayerCommunication {

    @Override
    public void sendMessage(String message) {
        //System.out.println("Bot: "+message);
    }

    @Override
    public String readMessage() {
        //System.out.println("Bot is reading message");
        return "Bot read message";
    }


 


}
