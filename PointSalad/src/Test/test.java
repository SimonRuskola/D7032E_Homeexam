package PointSalad.src.Test;


import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import org.junit.Before;

import PointSalad.src.GameService;
import PointSalad.src.Cards.CardFactory;
import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Cards.PileInterface;
import PointSalad.src.Market.Market;
import PointSalad.src.Player.Player;


public class test {

    private GameService gameService;
    private Market market;
    private CardFactory cardFactory;

    @Before
    public void setUp() {
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        gameService = new GameService(market);
    }

    @Test
    public void testPlayerCountOK() {

        assertEquals(true, gameService.checkNumberOfPlayers(1, 1));
        assertEquals(true, gameService.checkNumberOfPlayers(1, 2));
        assertEquals(true, gameService.checkNumberOfPlayers(2, 1));
        assertEquals(true, gameService.checkNumberOfPlayers(3, 1));
        assertEquals(true, gameService.checkNumberOfPlayers(1, 3));
        assertEquals(true, gameService.checkNumberOfPlayers(2, 2));
        assertEquals(true, gameService.checkNumberOfPlayers(2, 3));
        assertEquals(true, gameService.checkNumberOfPlayers(3, 2));
        assertEquals(true, gameService.checkNumberOfPlayers(3, 3));
        assertEquals(true, gameService.checkNumberOfPlayers(4, 1));
        assertEquals(true, gameService.checkNumberOfPlayers(1, 4));
        assertEquals(true, gameService.checkNumberOfPlayers(4, 2));
        assertEquals(true, gameService.checkNumberOfPlayers(2, 4));
        assertEquals(true, gameService.checkNumberOfPlayers(5, 1));
        assertEquals(true, gameService.checkNumberOfPlayers(1, 5));
        
    }

    @Test
    public void testPlayerCountNotOK() {
        
        assertEquals(false, gameService.checkNumberOfPlayers(0, 0));
        assertEquals(false, gameService.checkNumberOfPlayers(7, 7));
        assertEquals(false, gameService.checkNumberOfPlayers(0, 7));
        assertEquals(false, gameService.checkNumberOfPlayers(7, 0));
        assertEquals(false, gameService.checkNumberOfPlayers(0, 1));
        assertEquals(false, gameService.checkNumberOfPlayers(1, 0));
        assertEquals(false, gameService.checkNumberOfPlayers(1, 7));
        assertEquals(false, gameService.checkNumberOfPlayers(7, 1));
        assertEquals(false, gameService.checkNumberOfPlayers(3, 4));
        assertEquals(false, gameService.checkNumberOfPlayers(4, 3));
        assertEquals(false, gameService.checkNumberOfPlayers(4, 4));
    }

    @Test
    public void testDeckSize() {

        ArrayList<PileInterface> pileList = cardFactory.createPile(6);



        assertEquals(36, pileList.get(0).getSize());
        assertEquals(36, pileList.get(1).getSize());
        assertEquals(36, pileList.get(2).getSize());


    }

    @Test
    public void testDeckFormation() {
        game.setPlayerCount(2);
        assertEquals(36, game.getDeck().size());
        game.setPlayerCount(3);
        assertEquals(54, game.getDeck().size());
        game.setPlayerCount(4);
        assertEquals(72, game.getDeck().size());
        game.setPlayerCount(5);
        assertEquals(90, game.getDeck().size());
        game.setPlayerCount(6);
        assertEquals(108, game.getDeck().size());
    }

    @Test
    public void testShufflingAndDrawPiles() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        List<List<Card>> drawPiles = game.getDrawPiles();
        assertEquals(3, drawPiles.size());
        assertTrue(Math.abs(drawPiles.get(0).size() - drawPiles.get(1).size()) <= 1);
        assertTrue(Math.abs(drawPiles.get(1).size() - drawPiles.get(2).size()) <= 1);
    }

    @Test
    public void testVegetableMarket() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        assertEquals(6, game.getVegetableMarket().size());
    }

    @Test
    public void testStartPlayer() {
        game.setPlayerCount(4);
        game.chooseStartPlayer();
        assertNotNull(game.getStartPlayer());
    }

    @Test
    public void testPlayerTurn() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        Player player = game.getCurrentPlayer();
        game.playerDraftPointCard(player, 0);
        assertEquals(1, player.getHand().size());
        game.playerDraftVeggieCards(player, 0, 1);
        assertEquals(3, player.getHand().size());
        game.turnPointCardToVeggie(player, 0);
        assertEquals("Veggie", player.getHand().get(0).getType());
    }

    @Test
    public void testMarketReplacement() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        game.playerDraftVeggieCards(game.getCurrentPlayer(), 0, 1);
        game.replaceMarket();
        assertEquals(6, game.getVegetableMarket().size());
    }

    @Test
    public void testDrawPileDepletion() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        while (game.getDrawPiles().get(0).size() > 0) {
            game.playerDraftPointCard(game.getCurrentPlayer(), 0);
        }
        assertTrue(game.getDrawPiles().get(0).size() > 0);
    }

    @Test
    public void testGameContinuation() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        while (!game.isGameOver()) {
            game.nextTurn();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testScoring() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        while (!game.isGameOver()) {
            game.nextTurn();
        }
        game.calculateScores();
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getScore());
        }
    }

    @Test
    public void testWinnerAnnouncement() {
        game.setPlayerCount(4);
        game.shuffleAndCreateDrawPiles();
        game.createVegetableMarket();
        while (!game.isGameOver()) {
            game.nextTurn();
        }
        game.calculateScores();
        assertNotNull(game.getWinner());
    }
}