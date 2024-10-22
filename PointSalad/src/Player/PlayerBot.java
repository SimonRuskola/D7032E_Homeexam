package PointSalad.src.Player;


import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;


// PlayerBot is a class that represents a bot player in the game. It extends the Player class and implements the PlayerInterface.


public class PlayerBot extends Player{
   
    
    private MarketInterface market;
    

    public PlayerBot(int playerID, IPlayerActions actions, MarketInterface market, IPlayerCommunication playerCommunication) {
        super(playerID, actions);
        this.market = market;
        setPlayerCommunication(playerCommunication);
       
    }

    
}
