package PointSalad.src;
import java.util.ArrayList;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Cards.PileInterface;

public interface MarketInterface {
    public String printMarket();
    public void setCardsOnTable();
    public void setPiles(int playerCount);
    CardInterface getCardFromTable(int index);
    CardInterface copyCardFromTable(int index);
    ArrayList<CardInterface> copyCardsFromTable();
    ArrayList<PileInterface> copyPiles();
    CardInterface getCardFromPile(int index);
    int getTableSize();
}
