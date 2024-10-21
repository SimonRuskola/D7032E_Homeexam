package PointSalad.src.Player;

import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Player.communication.IPlayerCommunication;
import PointSalad.src.Player.actions.IPlayerActions;

public abstract class Player implements PlayerInterface{
    
    private int playerID;
    private ArrayList<CardInterface> hand;
	private boolean online;
	private IPlayerActions actions;
	private IPlayerCommunication playerCommunication;
	private MarketInterface	market;

    public Player(int playerID, IPlayerActions actions) {
		this.actions = actions;
        this.playerID = playerID;
        this.hand = new ArrayList<CardInterface>();
    }

	public IPlayerActions getActions() {
		return this.actions;
	}

	public void setPlayerCommunication(IPlayerCommunication playerCommunication) {
		this.playerCommunication = playerCommunication;
	}

	public IPlayerCommunication getPlayerCommunication() {
		return this.playerCommunication;
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


}
