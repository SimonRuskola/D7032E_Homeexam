package PointSalad.src.Test;


import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import org.junit.Before;

import PointSalad.src.GameService;
import PointSalad.src.Market.Market;
import PointSalad.src.Player.PlayerHuman;
import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Player.actions.playerHumanActions;
import PointSalad.src.Player.communication.CommunicationForTesting;
import PointSalad.src.Network.*;
import PointSalad.src.Cards.*;

// This class tests the requirements of the Point Salad game

public class pointSaladTests {

    private GameService gameService;
    private Market market;
    private CardFactory cardFactory;

    @Before
    public void setUp() {
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        gameService = new GameService(market);
    }

    public void testPlayerCountOK() { //requirement 1

        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 0));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(0, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 2));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(2, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(3, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 3));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(2, 2));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(2, 3));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(3, 2));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(3, 3));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(4, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 4));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(4, 2));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(2, 4));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(5, 1));
        assertEquals(false, gameService.checkIfWrongAmountOfPlayers(1, 5));
        
    }

    @Test
    public void testPlayerCountNotOK() { // requirement 1
        
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(0, 0));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(7, 7));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(0, 7));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(7, 0));    
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(1, 7));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(7, 1));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(3, 4));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(4, 3));
        assertEquals(true, gameService.checkIfWrongAmountOfPlayers(4, 4));
    }

    @Test
    public void testDeckSize() { // requirement 2

        ArrayList<PileInterface> pileList = cardFactory.createPile(6);

        for (PileInterface pile : pileList) {
            assertEquals(36, pile.getSize());

        }

    }

    @Test
    public void testVegetableAmount() { // requirement 3

        ArrayList<PileInterface> pileList = cardFactory.createPile(6);

        int pepperCount = 0;
        int lettuceCount = 0;
        int carrotCount = 0;
        int cabbageCount = 0;
        int onionCount = 0;
        int tomatoCount = 0;

        for (PileInterface pile : pileList) {
            for (CardInterface card : pile.getCards()) {
                switch (card.getBackSide().toString()) {
                    case "PEPPER":
                        pepperCount++;
                        break;
                    case "LETTUCE":
                        lettuceCount++;
                        break;
                    case "CARROT":
                        carrotCount++;
                        break;
                    case "CABBAGE":
                        cabbageCount++;
                        break;
                    case "ONION":
                        onionCount++;
                        break;
                    case "TOMATO":
                        tomatoCount++;
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + card.getBackSide());
                }
            }
        }

        assertEquals(18, pepperCount);
        assertEquals(18, lettuceCount);
        assertEquals(18, carrotCount);
        assertEquals(18, cabbageCount);
        assertEquals(18, onionCount);
        assertEquals(18, tomatoCount);

    }

    @Test
    public void testDeckFormation() { // requirement 3

        

        for (int i = 2; i <= 6; i++) {
            if (i==3){i++;}
            ArrayList<PileInterface> pileList = cardFactory.createPile(i);
            //System.out.println("piles"+pileList.get(0).getSize());
            for (PileInterface pile : pileList) {
                assertEquals(6*i, pile.getSize());
            }
        }
       
    }

    @Test
    public void testShufflingAndDrawPiles() { // requirement 4

        ArrayList<PileInterface> drawPiles = cardFactory.createPile(4);
        assertEquals(3, drawPiles.size());
        assertTrue(Math.abs(drawPiles.get(0).getSize() - drawPiles.get(1).getSize()) <= 1);
        assertTrue(Math.abs(drawPiles.get(1).getSize() - drawPiles.get(2).getSize()) <= 1);
    }

    @Test
    public void testVegetableMarket() { // requirement 5

        market.setPiles(6);
        market.setCardsOnTable();
        assertEquals(6, market.copyCardsFromTable().size());
        for( CardInterface card : market.copyCardsFromTable()) {
            assertEquals(true, card.isFlipped());
        }
    }

    @Test
    public void testStartPlayer() { // requirement 6
        
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        gameService = new GameService(market);
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        Server server;
        try {
            server = new Server(1, 5, market);
            server.getPlayer(0).setPlayerCommunication(playerCommunication);
            gameService.setServer(server);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerInterface startingPlayer = gameService.setRandomStartingPlayer(1, 3);
        int startingPlayerIndex = gameService.getCurrentPlayerIndex();

        boolean differentStartingPlayer = false;

        for (int i = 0; i < 100; i++) {
            startingPlayer = gameService.setRandomStartingPlayer(1,3 );
            if (startingPlayer.getPlayerID() != 0) {
                differentStartingPlayer = true;
                break;
            }
            //System.out.println("Starting player: " + startingPlayer.getPlayerID());
        }

        assertEquals(true, differentStartingPlayer);    
       
    }

    @Test
    public void testPlayerTurnPointcardsAndFlip() { // requirement 7 and 8
      
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        market.setCardsOnTable();
        gameService = new GameService(market);

        playerHumanActions playerAction = new playerHumanActions();
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        PlayerInterface player = new PlayerHuman(0, playerAction, playerCommunication);

        playerCommunication.setMessage("0");
        playerAction.takeOneCard(player, market);

        assertEquals(1, player.getHand().size());

        player.getHand().get(0).flipCard();

        Set<String> validBackSides = new HashSet<>(Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO"));
        assertTrue(validBackSides.contains(player.getHand().get(0).toString()));
        player.getHand().get(0).flipCard();
        

        playerCommunication.setMessage("0");
        playerAction.flipCard(player);
        assertTrue(validBackSides.contains(player.getHand().get(0).toString()));



    }

     
    @Test
    public void testPlayerTurnVegtable() { // requirement 7
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        market.setCardsOnTable();
        gameService = new GameService(market);

        playerHumanActions playerAction = new playerHumanActions();
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        PlayerInterface player = new PlayerHuman(0, playerAction, playerCommunication);

        playerCommunication.setMessage("01");
        playerAction.takeTwoCards(player, market);

        assertEquals(2, player.getHand().size());

        

        Set<String> validBackSides = new HashSet<>(Arrays.asList("PEPPER", "LETTUCE", "CARROT", "CABBAGE", "ONION", "TOMATO"));
        assertTrue(validBackSides.contains(player.getHand().get(0).toString()));
        assertTrue(validBackSides.contains(player.getHand().get(1).toString()));
    


    }

    @Test
    public void testShowHand() { // requirment 9
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        gameService = new GameService(market);

        CommunicationForTesting playerCommunication = new CommunicationForTesting();

        String recievedMessage = "";
        String messageSent= "";
        Server server;
        try {
			server = new Server(1, 1, market);
            server.getPlayer(0).setPlayerCommunication(playerCommunication);
            messageSent = "test";
            gameService.sendAllPlayersMessage(messageSent, server);

            recievedMessage = playerCommunication.getRecievedMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}

        

        assertEquals(messageSent, recievedMessage);
    }

    @Test
    public void testMarketReplacement() { // requirement 10
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        market.setCardsOnTable();

        playerHumanActions playerAction = new playerHumanActions();
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        PlayerInterface player = new PlayerHuman(0, playerAction, playerCommunication);

        
        for (int i = 0; i < 3; i++) {
            
            ArrayList<PileInterface> piles = market.copyPiles();
            PileInterface pile = piles.get(i);

            CardInterface card1 = pile.getTopCard();
            CardInterface card2 = pile.getCard(1);
    
            switch (i) {
                case 0:
                    playerCommunication.setMessage("01");
                    break;
                case 1:
                    playerCommunication.setMessage("23");
                    break;
                case 2:
                    playerCommunication.setMessage("45");
                    break;
            }
            
            boolean sameCards = false;

            playerAction.takeTwoCards(player, market);
            player.setHand(new ArrayList<CardInterface>());
            playerAction.takeTwoCards(player, market);

            if (card1.toString().equals(player.getHand().get(0).toString()) && card2.toString().equals(player.getHand().get(1).toString())) {
                sameCards = true;
            }

            //System.out.println("Card1: " + card1.toString());
            //System.out.println("Also Card1 "+player.getHand().get(0).toString());

            //System.out.println("Card2: " + card2.toString());
            //System.out.println("Also Card2 "+player.getHand().get(1).toString());

            assertEquals(true, sameCards);

            assertEquals(6, market.copyCardsFromTable().size());
        }
    }

    @Test
    public void testDrawPileDepletion() { // requirment 11 and 12
        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        gameService = new GameService(market);
        market.setCardsOnTable();

        playerHumanActions playerAction = new playerHumanActions();
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        PlayerInterface player = new PlayerHuman(0, playerAction, playerCommunication);

        playerCommunication.setMessage("01");

        for (int i = 0; i < 16; i++) {
            playerAction.takeTwoCards(player, market);
        }
        playerCommunication.setMessage("23");
        playerAction.takeTwoCards(player, market);
        playerCommunication.setMessage("45");
        playerAction.takeTwoCards(player, market);

        assertEquals(true, gameService.isGameOver(market));
   

    }


    @Test
    public void testScoring() { // requirement 13

        cardFactory = new CardFactory(); 
        market = new Market(cardFactory);
        market.setPiles(2);
        gameService = new GameService(market);
        market.setCardsOnTable();
        CommunicationForTesting playerCommunication = new CommunicationForTesting();
        Server server;

        

        ArrayList<CardInterface> hand = new ArrayList<CardInterface>();
        hand.add(new Card(CardType.PEPPER, "MOST LETTUCE = 10"));
        hand.add(new Card(CardType.PEPPER, "FEWEST LETTUCE = 7"));
        hand.add(new Card(CardType.PEPPER, "LETTUCE: EVEN=7, ODD=3"));
        hand.add(new Card(CardType.PEPPER, "2 / LETTUCE"));
        hand.add(new Card(CardType.PEPPER, "LETTUCE + LETTUCE = 5"));
        hand.add(new Card(CardType.PEPPER, "LETTUCE + ONION = 5"));
        hand.add(new Card(CardType.PEPPER, "CABBAGE + TOMATO = 5"));
        hand.add(new Card(CardType.PEPPER, "1 / CABBAGE,  1 / LETTUCE"));
        hand.add(new Card(CardType.PEPPER, "1 / CARROT,  1 / LETTUCE"));
        hand.add(new Card(CardType.PEPPER, "3 / TOMATO,  -2 / LETTUCE"));
        hand.add(new Card(CardType.PEPPER, "LETTUCE + LETTUCE + LETTUCE = 8"));
        hand.add(new Card(CardType.PEPPER, "PEPPER + LETTUCE + CABBAGE = 8"));
        hand.add(new Card(CardType.PEPPER, "LETTUCE + CARROT + ONION = 8"));
        hand.add(new Card(CardType.PEPPER, "2/LETTUCE,  2/CARROT,  -4/ONION"));
        hand.add(new Card(CardType.PEPPER, "3/LETTUCE,  -1/ONION,  -1/PEPPER"));
        hand.add(new Card(CardType.PEPPER, "4/LETTUCE,  -2/TOMATO,  -2/CABBAGE"));
        hand.add(new Card(CardType.PEPPER, "MOST TOTAL VEGETABLE = 10"));
        hand.add(new Card(CardType.PEPPER, "FEWEST TOTAL VEGETABLE = 7"));
        hand.add(new Card(CardType.PEPPER, "5 / VEGETABLE TYPE >=3"));
        hand.add(new Card(CardType.PEPPER, "5 / MISSING VEGETABLE TYPE"));
        hand.add(new Card(CardType.PEPPER, "3 / VEGETABLE TYPE >=2"));
        hand.add(new Card(CardType.PEPPER, "COMPLETE SET = 12"));

        hand.add(new Card(CardType.PEPPER, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.LETTUCE, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.LETTUCE, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.LETTUCE, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.TOMATO, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.CARROT, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.ONION, "COMPLETE SET = 12"));
        hand.add(new Card(CardType.CABBAGE, "COMPLETE SET = 12"));

        int score  = 0;
        try {
			server = new Server(1, 1, market);
            server.getPlayer(0).setPlayerCommunication(playerCommunication);
            server.getPlayer(0).setHand(hand);
            for (int i = 22; i <= 29; i++) {
                CardInterface card = server.getPlayer(0).getHand().get(i);
                card.flipCard();
            }
            //System.out.println(gameService.displayHand(server.getPlayer(0).getHand()));
            gameService.setServer(server);
            score = gameService.calculateScore(server.getPlayer(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

        //System.out.println("Score: " + score);

        assertEquals(112, score);


    }

    @Test
    public void testWinnerAnnouncement() { // requirement 14
            
            cardFactory = new CardFactory(); 
            market = new Market(cardFactory);
            market.setPiles(2);
            gameService = new GameService(market);
            market.setCardsOnTable();
            CommunicationForTesting playerCommunication = new CommunicationForTesting();
            Server server;

            boolean correctMessage = false;

            ArrayList<CardInterface> hand = new ArrayList<CardInterface>();
            hand.add(new Card(CardType.PEPPER, "MOST LETTUCE = 10"));
            hand.add(new Card(CardType.PEPPER, "FEWEST LETTUCE = 7"));

            try {
                server = new Server(1, 1, market);
                server.getPlayer(0).setPlayerCommunication(playerCommunication);
                server.getPlayer(0).setHand(hand);
                
                CardInterface card = server.getPlayer(0).getHand().get(1);
                card.flipCard();
              
                //System.out.println(gameService.displayHand(server.getPlayer(0).getHand()));
                gameService.setServer(server);

                gameService.endGame();

                String endMessage = playerCommunication.getRecievedMessage();


                if (endMessage.contains("The winner is: "+server.getPlayer(0).getPlayerID())) {
                    correctMessage = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            assertEquals(true, correctMessage); 
    
            
    }

    
}
    