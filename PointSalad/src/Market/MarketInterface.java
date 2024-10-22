package PointSalad.src.Market;
import java.util.ArrayList;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Cards.PileInterface;

// MarketInterface is an interface that defines the methods that a Market object must implement.

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
    int getAmountOfPiles();
}
