package ActualProject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayDeque;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Scanner;

public class Market {
    private List<Deque<Card>> pointPiles;
    private List<Card> veggiePiles;
    private int nrPlayers;

    private void initializeMarket(int numPlayers) {
        int cardsPerVegetable;
        this.nrPlayers = numPlayers;
        if (numPlayers < 2 || numPlayers > 6) {
            throw new IllegalArgumentException("Invalid number of players");
        }else{
            cardsPerVegetable = numPlayers * 3;    
        };  

        List<Card> deck = createDeck(cardsPerVegetable);
        Collections.shuffle(deck);

        pointPiles = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            pointPiles.add(new ArrayDeque<>());
        }

        // Distribute cards to point piles
        for (int i = 0; i < deck.size(); i++) {
            pointPiles.get(i % 3).addLast(deck.get(i));
        }

        veggiePiles = new ArrayList<>();
        for (Deque<Card> pile : pointPiles) {
            veggiePiles.add(pile.removeFirst());
            veggiePiles.add(pile.removeFirst());
        }
    }

    private List<Card> createDeck(int cardsPerVegetable) {
        ArrayList<Card> deckPepper = new ArrayList<>();
        ArrayList<Card> deckLettuce = new ArrayList<>();
		ArrayList<Card> deckCarrot = new ArrayList<>();
		ArrayList<Card> deckCabbage = new ArrayList<>();
		ArrayList<Card> deckOnion = new ArrayList<>();
		ArrayList<Card> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("PointSaladManifest.json");
            Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

            // Read the entire JSON file into a String
            String jsonString = scanner.hasNext() ? scanner.next() : "";

            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Get the "cards" array from the JSONObject
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            // Iterate over each card in the array
            for (int i = 0; i < cardsArray.length(); i++) {
                JSONObject cardJson = cardsArray.getJSONObject(i);

                // Get the criteria object from the card JSON
                JSONObject criteriaObj = cardJson.getJSONObject("criteria");

                // Add each vegetable card to the deck with its corresponding criteria
                deckPepper.add(new Card(Card.Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
                deckLettuce.add(new Card(Card.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
                deckCarrot.add(new Card(Card.Vegetable.CARROT, criteriaObj.getString("CARROT")));
                deckCabbage.add(new Card(Card.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
                deckOnion.add(new Card(Card.Vegetable.ONION, criteriaObj.getString("ONION")));
                deckTomato.add(new Card(Card.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
            }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Shuffle each deck
		    shuffleDeck(deckPepper);
		    shuffleDeck(deckLettuce);
		    shuffleDeck(deckCarrot);
		    shuffleDeck(deckCabbage);
		    shuffleDeck(deckOnion);
		    shuffleDeck(deckTomato);

		    int cardsPerVeggie = this.nrPlayers/2 * 6;
        
		    ArrayList<Card> deck = new ArrayList<>();
		    for(int i = 0; i < cardsPerVeggie; i++) {
		    	deck.add(deckPepper.remove(0));
		    	deck.add(deckLettuce.remove(0));
		    	deck.add(deckCarrot.remove(0));
		    	deck.add(deckCabbage.remove(0));
		    	deck.add(deckOnion.remove(0));
		    	deck.add(deckTomato.remove(0));
		    }
		    shuffleDeck(deck);

		    //divide the deck into 3 piles

		    ArrayList<Card> pile1 = new ArrayList<>();
		    ArrayList<Card> pile2 = new ArrayList<>();
		    ArrayList<Card> pile3 = new ArrayList<>();
		    for (int i = 0; i < deck.size(); i++) {
		    	if (i % 3 == 0) {
		    		pile1.add(deck.get(i));
		    	} else if (i % 3 == 1) {
		    		pile2.add(deck.get(i));
		    	} else {
		    		pile3.add(deck.get(i));
		    	}
		    }
		    piles.add(new Pile(pile1));
		    piles.add(new Pile(pile2));
		    piles.add(new Pile(pile3));
        
    }

    public Card drawPointCard(int pileIndex) {
        if (pointPiles.get(pileIndex).isEmpty()) {
            redistributeCards();
        }
        return pointPiles.get(pileIndex).removeFirst();
    }

    public String printMarket () {
        String marketString = "";
        for (int i = 0; i < pointPiles.size(); i++) {
            marketString += "Pile " + i + ": ";
            for (Card card : pointPiles.get(i)) {
                marketString += card.toString() + " ";
            }
            marketString += "\n";
        }
        marketString += "this is a test \n";
        return marketString;
    }

    public List<Card> drawVeggieCards(int count) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (veggiePiles.isEmpty()) {
                break;
            }
            drawnCards.add(veggiePiles.remove(0));
        }
        return drawnCards;
    }

    public void replenishMarket() {
        while (veggiePiles.size() < 6 && !allPilesEmpty()) {
            for (Deque<Card> pile : pointPiles) {
                if (!pile.isEmpty()) {
                    veggiePiles.add(pile.removeFirst());
                    break;
                }
            }
        }
    }

    private boolean allPilesEmpty() {
        return pointPiles.stream().allMatch(Deque::isEmpty);
    }

    private void redistributeCards() {
        Deque<Card> largestPile = pointPiles.stream()
                .max(Comparator.comparingInt(Deque::size))
                .orElseThrow();

        for (Deque<Card> pile : pointPiles) {
            if (pile.isEmpty()) {
                pile.addLast(largestPile.removeLast());
            }
        }
    }
}