package PointSalad;
import java.util.ArrayList;

import PointSalad.Cards.CardInterface;

public interface PileInterface {
    public void shuffle();
    public CardInterface drawTopCard();
    public CardInterface drawBottomCard();
    public int getSize();
    public ArrayList<CardInterface> getCards();
    public CardInterface getBottomCard();
    public CardInterface getTopCard();

}
