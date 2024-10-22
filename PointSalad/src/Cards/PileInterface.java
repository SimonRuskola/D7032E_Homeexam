package PointSalad.src.Cards;
import java.util.ArrayList;

public interface PileInterface {
    public void shuffle();
    public CardInterface drawTopCard();
    public CardInterface drawBottomCard();
    public int getSize();
    public ArrayList<CardInterface> getCards();
    public CardInterface getBottomCard();
    public CardInterface getTopCard();
    public CardInterface getCard(int index);

}
