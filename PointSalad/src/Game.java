package PointSalad.src;

import PointSalad.src.Cards.CardFactory;

public class Game {
    public static void main(String[] args) {

        int numberPlayers = 2;

        CardFactory cardFactory = new CardFactory();
        Market market = new Market(cardFactory);
        GameConfig config = new GameConfig("PointSalad\\actions.json");
        GameService gameService = new GameService(market, config);
        gameService.startGame(args);

        //market.setPiles(numberPlayers);
        //market.setCardOnTable();
        //System.out.println(market.printMarket());
    
    }
   
    
}
