
package PointSalad.src.Player;

import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;


// PlayerHuman is a class that represents a human player in the game. It extends the Player class and implements the PlayerInterface.


public class PlayerHuman extends Player{


    public PlayerHuman(int playerID, IPlayerActions actions, IPlayerCommunication playerCommunication) {
        super(playerID, actions);
		setPlayerCommunication(playerCommunication);
		
    }

}
