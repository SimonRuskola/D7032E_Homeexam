package PointSalad;
import java.util.ArrayList;

public class Game {
    public static void main(String[] args) {

        int numberPlayers = 2;

        CardFactory cardFactory = new CardFactory();
        Market market = new Market(cardFactory);
        GameService gameService = new GameService(market);

        
        market.printMarketPiles();
        market.printMarket();
    }
   
    
}
