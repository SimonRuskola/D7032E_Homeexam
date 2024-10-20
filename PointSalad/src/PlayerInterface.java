package PointSalad.src;
import java.util.ArrayList;

import PointSalad.src.Cards.CardInterface;

public interface PlayerInterface {

    int getPlayerID();
    void addCardToHand(CardInterface card);
    ArrayList<CardInterface> getHand();
    boolean isBot();
    void sendMessage(Object message);
    String readMessage();

}
