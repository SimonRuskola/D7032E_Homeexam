
package PointSalad.src;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PointSalad.src.Cards.CardInterface;


public class Player implements PlayerInterface {

    private int playerID;
    private ArrayList<CardInterface> hand;
	private Socket connection;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private boolean online;
	private Scanner in = new Scanner(System.in);

    public Player(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
		this.connection = connection; 
		this.inFromClient = inFromClient; 
		this.outToClient = outToClient;
        this.hand = new ArrayList<CardInterface>();
		if(connection == null)
				this.online = false;
			else
				this.online = true;
    }

	public Player(int playerID, boolean isBot){
		this.playerID = playerID;
		this.hand = new ArrayList<CardInterface>();
		this.online = false;
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
		if (!online) {
			System.out.println(message);
		}
		else {
			try {
				outToClient.writeObject(message);
			} catch (Exception e) {}
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

	@Override
	public boolean isBot() {
		return false;
	}

}
