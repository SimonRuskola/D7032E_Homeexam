
package PointSalad.src.Player;

import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;


public class PlayerHuman extends Player{


    public PlayerHuman(int playerID, IPlayerActions actions, IPlayerCommunication playerCommunication) {
        super(playerID, actions);
		setPlayerCommunication(playerCommunication);
		
    }

}
