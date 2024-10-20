
package PointSalad.src.Player;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PointSalad.src.Cards.CardInterface;


public class PlayerHuman extends Player{

    private int playerID;
    private ArrayList<CardInterface> hand;
	private Socket connection;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private boolean online;
	private Scanner in = new Scanner(System.in);

    public PlayerHuman(int playerID, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(playerID);
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

	

	@Override
	public boolean isBot() {
		return false;
	}

}
