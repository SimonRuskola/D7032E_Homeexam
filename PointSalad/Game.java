package PointSalad;
import java.util.ArrayList;

public class Game {
    public static void main(String[] args) {

        int numberPlayers = 2;


        CardFactory cardFactory = new CardFactory(numberPlayers);
        Market market = new Market(numberPlayers, cardFactory);
        market.printMarketPiles();
        market.printMarket();
    }
   
    
}
