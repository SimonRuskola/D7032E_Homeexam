package PointSalad;

public interface MarketInterface {
    public String printMarket();
    public void setCardsOnTable();
    public void setPiles(int playerCount);
    CardInterface getCardFromTable(int index);
    CardInterface copyCardFromTable(int index);
    CardInterface getCardFromPile(int index);
    int getTableSize();
}
