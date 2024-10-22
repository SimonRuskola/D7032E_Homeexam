import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import org.junit.Before;
import java.util.ArrayList;

public class PointSaladTest {

    @Test
    public void testCalculateScore_MostVegetables() {
        PointSalad game = new PointSalad(new String[]{});
        PointSalad.Player player1 = game.new Player(1, false, null, null, null);
        PointSalad.Player player2 = game.new Player(2, false, null, null, null);
        game.players.add(player1);
        game.players.add(player2);

        ArrayList<PointSalad.Card> hand = new ArrayList<>();
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "MOST PEPPER=5"));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));

        player1.hand = hand;

        int score = game.calculateScore(player1.hand, player1);
        assertEquals(5, score);
    }

    @Test
    public void testCalculateScore_FewestVegetables() {
        PointSalad game = new PointSalad(new String[]{});
        PointSalad.Player player1 = game.new Player(1, false, null, null, null);
        PointSalad.Player player2 = game.new Player(2, false, null, null, null);
        game.players.add(player1);
        game.players.add(player2);

        ArrayList<PointSalad.Card> hand = new ArrayList<>();
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "FEWEST PEPPER=5"));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));

        player1.hand = hand;

        int score = game.calculateScore(player1.hand, player1);
        assertEquals(5, score);
    }

    @Test
    public void testCalculateScore_TotalVegetables() {
        PointSalad game = new PointSalad(new String[]{});
        PointSalad.Player player1 = game.new Player(1, false, null, null, null);
        PointSalad.Player player2 = game.new Player(2, false, null, null, null);
        game.players.add(player1);
        game.players.add(player2);

        ArrayList<PointSalad.Card> hand = new ArrayList<>();
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "TOTAL MOST=5"));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CARROT, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CABBAGE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.ONION, ""));

        player1.hand = hand;

        int score = game.calculateScore(player1.hand, player1);
        assertEquals(5, score);
    }

    @Test
    public void testCalculateScore_TypeMissing() {
        PointSalad game = new PointSalad(new String[]{});
        PointSalad.Player player1 = game.new Player(1, false, null, null, null);
        PointSalad.Player player2 = game.new Player(2, false, null, null, null);
        game.players.add(player1);
        game.players.add(player2);

        ArrayList<PointSalad.Card> hand = new ArrayList<>();
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "3/MISSING"));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CARROT, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CABBAGE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.ONION, ""));

        player1.hand = hand;

        int score = game.calculateScore(player1.hand, player1);
        assertEquals(3, score);
    }

    @Test
    public void testCalculateScore_Set() {
        PointSalad game = new PointSalad(new String[]{});
        PointSalad.Player player1 = game.new Player(1, false, null, null, null);
        PointSalad.Player player2 = game.new Player(2, false, null, null, null);
        game.players.add(player1);
        game.players.add(player2);

        ArrayList<PointSalad.Card> hand = new ArrayList<>();
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "SET=12"));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CARROT, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.CABBAGE, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.ONION, ""));
        hand.add(new PointSalad.Card(PointSalad.Card.Vegetable.TOMATO, ""));

        player1.hand = hand;

        int score = game.calculateScore(player1.hand, player1);
        assertEquals(12, score);
    }

    @Test
    public void testGetPointCard_FromNonEmptyPile() {
        PointSalad game = new PointSalad(new String[]{});
        ArrayList<PointSalad.Card> cards = new ArrayList<>();
        cards.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "criteria1"));
        cards.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, "criteria2"));
        PointSalad.Pile pile = game.new Pile(cards);

        PointSalad.Card pointCard = pile.getPointCard();
        assertNotNull(pointCard);
        assertEquals(PointSalad.Card.Vegetable.PEPPER, pointCard.vegetable);
    }

    @Test
    public void testGetPointCard_FromEmptyPile() {
        PointSalad game = new PointSalad(new String[]{});
        ArrayList<PointSalad.Card> cards = new ArrayList<>();
        PointSalad.Pile pile = game.new Pile(cards);
        game.piles.add(pile);

        PointSalad.Card pointCard = pile.getPointCard();
        assertNull(pointCard);
    }

    @Test
    public void testGetPointCard_FromEmptyPileWithOtherNonEmptyPiles() {
        PointSalad game = new PointSalad(new String[]{});
        ArrayList<PointSalad.Card> cards1 = new ArrayList<>();
        ArrayList<PointSalad.Card> cards2 = new ArrayList<>();
        cards2.add(new PointSalad.Card(PointSalad.Card.Vegetable.PEPPER, "criteria1"));
        cards2.add(new PointSalad.Card(PointSalad.Card.Vegetable.LETTUCE, "criteria2"));
        PointSalad.Pile emptyPile = game.new Pile(cards1);
        PointSalad.Pile nonEmptyPile = game.new Pile(cards2);
        game.piles.add(emptyPile);
        game.piles.add(nonEmptyPile);

        PointSalad.Card pointCard = emptyPile.getPointCard();
        assertNotNull(pointCard);
        assertEquals(PointSalad.Card.Vegetable.LETTUCE, pointCard.vegetable);
    }

    @Test
    public void testGetPointCard_FromEmptyPileWithOtherEmptyPiles() {
        PointSalad game = new PointSalad(new String[]{});
        ArrayList<PointSalad.Card> cards1 = new ArrayList<>();
        ArrayList<PointSalad.Card> cards2 = new ArrayList<>();
        PointSalad.Pile emptyPile1 = game.new Pile(cards1);
        PointSalad.Pile emptyPile2 = game.new Pile(cards2);
        game.piles.add(emptyPile1);
        game.piles.add(emptyPile2);

        PointSalad.Card pointCard = emptyPile1.getPointCard();
        assertNull(pointCard);
    }
}