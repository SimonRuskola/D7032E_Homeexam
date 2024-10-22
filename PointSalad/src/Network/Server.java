package PointSalad.src.Network;

import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;


import PointSalad.src.Player.PlayerHuman;
import PointSalad.src.Player.PlayerBot;
import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Player.communication.*;
import PointSalad.src.Player.actions.*;

// Server is a class that represents the server in the game. It is responsible for managing the players and their connections.

public class Server {

    private ServerSocket aSocket;
    private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();

    public Server(int numberPlayers, int numberOfBots, MarketInterface market) throws Exception {
    
        this.players.add(new PlayerHuman(0, new playerHumanActions(), new LocalPlayerCommunication())); //add this instance as a player
        //Open for connections if there are online players

        for(int i=0; i<numberOfBots; i++) {
            this.players.add(new PlayerBot(i+1, new playerBotActions() ,market, new BotPlayerCommunication())); //add a bot    
        }
        if(numberPlayers>1)
            aSocket = new ServerSocket(2048);
        for(int i=numberOfBots+1; i<numberPlayers+numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            this.players.add(new PlayerHuman(i, new playerHumanActions(), new OnlinePlayerCommunication(connectionSocket))); //add an online client
            System.out.println("Connected to player " + i);
            //outToClient.writeObject("You connected to the server as player " + i + "\n");
        }    
    }

     public void sendToAllPlayers(String message) {
		for(PlayerInterface player : players) {
			player.getPlayerCommunication().sendMessage(message);
		}
	}

    public PlayerInterface getPlayer(int playerID) {
        return players.get(playerID);
    }

    public ArrayList<PlayerInterface> getPlayers() {
        return players;
    }
    
}
