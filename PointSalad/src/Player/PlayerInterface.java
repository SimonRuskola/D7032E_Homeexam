package PointSalad.src.Player;
import java.util.ArrayList;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;

public interface PlayerInterface {

    int getPlayerID();
    void addCardToHand(CardInterface card);
    ArrayList<CardInterface> getHand();
    IPlayerCommunication getPlayerCommunication();
    void setPlayerCommunication(IPlayerCommunication playerCommunication);
    IPlayerActions getActions();
    

}
