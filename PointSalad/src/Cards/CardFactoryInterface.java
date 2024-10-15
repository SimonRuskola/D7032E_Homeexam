package PointSalad.src.Cards;
import java.util.ArrayList;

public interface CardFactoryInterface {
    public ArrayList<PileInterface> createPile(int playerCount);
    
}
