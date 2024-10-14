package PointSalad.Cards;
import java.util.ArrayList;

import PointSalad.PileInterface;

public interface CardFactoryInterface {
    public ArrayList<PileInterface> createPile(int playerCount);
    
}
