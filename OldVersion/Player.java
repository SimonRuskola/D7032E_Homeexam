
package OldVersion;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Player implements PlayerInterface {

    private int playerID;
    private boolean isBot;
    private ArrayList<CardInterface> hand;
	public Socket connection;
	public ObjectInputStream inFromClient;
	public ObjectOutputStream outToClient;
	public boolean online;
	Scanner in = new Scanner(System.in);

    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        this.isBot = isBot;
		this.connection = connection; 
		this.inFromClient = inFromClient; 
		this.outToClient = outToClient;
        this.hand = new ArrayList<CardInterface>();
		if(connection == null)
				this.online = false;
			else
				this.online = true;
    }

	@Override
	public boolean isBot() {
		return this.isBot;
	}

	@Override
	public void addCardToHand(CardInterface card) {
		hand.add(card);
	}

	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	@Override
	public ArrayList<CardInterface> getHand() {
		return this.hand;
	}

	public void sendMessage(Object message) {
		if(online) {
			try {outToClient.writeObject(message);} catch (Exception e) {}
		} else if(!isBot){
			System.out.println(message);                
		}
		return;
	}

	public String readMessage() {
		String word = ""; 
		if(online)
			try{word = (String) inFromClient.readObject();} catch (Exception e){}
		else
			try {word=in.nextLine();} catch(Exception e){}
		return word;
	}

}
