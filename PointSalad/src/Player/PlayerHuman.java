
package PointSalad.src.Player;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;


public class PlayerHuman extends Player{

    private int playerID;
    private ArrayList<CardInterface> hand;
	private Socket connection;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private boolean online;
	private Scanner in = new Scanner(System.in);

    public PlayerHuman(int playerID, IPlayerActions actions, IPlayerCommunication playerCommunication) {
        super(playerID, actions);
		setPlayerCommunication(playerCommunication);
		
    }

}
