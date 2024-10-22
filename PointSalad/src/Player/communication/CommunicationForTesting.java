package PointSalad.src.Player.communication;


//This class is used for testing the communication between the player and the game

public class CommunicationForTesting implements IPlayerCommunication {

    private String message;
    private String recievedMessage;

    public CommunicationForTesting() {
        this.message = "";
       
    }

    @Override
    public void sendMessage(String message) {
        this.recievedMessage = message;
        System.out.println(message);
    }

    @Override
    public String readMessage() {
        System.out.println(this.message);
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecievedMessage() {
        return this.recievedMessage;
    }

    
    
}
