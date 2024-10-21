package PointSalad.src;

import PointSalad.src.Cards.CardFactory;
import PointSalad.src.Market.Market;

public class PointSalad {
    public static void main(String[] args) {


        CardFactory cardFactory = new CardFactory(); 
        Market market = new Market(cardFactory);
        GameService gameService = new GameService(market);
        gameService.startGame(args);

    
    }
   
    
}
