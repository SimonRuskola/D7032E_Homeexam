package PointSalad.src.Player;
import java.util.ArrayList;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Player.actions.IPlayerActions;
import PointSalad.src.Player.communication.IPlayerCommunication;

// PlayerInterface is an interface that defines the methods that a Player object must implement.

public interface PlayerInterface {

    int getPlayerID();
    void addCardToHand(CardInterface card);
    ArrayList<CardInterface> getHand();
    void setHand(ArrayList<CardInterface> hand);
    IPlayerCommunication getPlayerCommunication();
    void setPlayerCommunication(IPlayerCommunication playerCommunication);
    IPlayerActions getActions();


}
