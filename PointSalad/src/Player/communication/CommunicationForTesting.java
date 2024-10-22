package PointSalad.src.Player.communication;

public class CommunicationForTesting implements IPlayerCommunication {

    private String message;
    private String recievedMessage;

    public CommunicationForTesting() {
        this.message = "";
       
    }

    @Override
    public synchronized void sendMessage(String message) {
        this.recievedMessage = message;
        System.out.println(message);
    }

    @Override
    public synchronized String readMessage() {
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
