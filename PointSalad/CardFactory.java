package PointSalad;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class CardFactory implements CardFactoryInterface {
    

    public CardFactory() {
        
    }

    @Override
    public ArrayList<PileInterface> createPile(int numberPlayers) {

        ArrayList<PileInterface> piles = new ArrayList<>();

        ArrayList<CardInterface> deckPepper = new ArrayList<>();
        ArrayList<CardInterface> deckLettuce = new ArrayList<>();
		ArrayList<CardInterface> deckCarrot = new ArrayList<>();
		ArrayList<CardInterface> deckCabbage = new ArrayList<>();
		ArrayList<CardInterface> deckOnion = new ArrayList<>();
		ArrayList<CardInterface> deckTomato = new ArrayList<>();

        try (InputStream fInputStream = new FileInputStream("PointSalad\\PointSaladManifest.json");
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
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

            // Shuffle each deck
            Collections.shuffle(deckPepper);
            Collections.shuffle(deckLettuce);
            Collections.shuffle(deckCarrot);
            Collections.shuffle(deckCabbage);
            Collections.shuffle(deckOnion);
            Collections.shuffle(deckTomato);

		    

		    int cardsPerVeggie = numberPlayers/2 * 6;
            //int cardsPerVeggie = 3;

            //System.out.println("cardsPerVeggie: " + cardsPerVeggie);
        
		    ArrayList<CardInterface> deck = new ArrayList<>();
		    for(int i = 0; i < cardsPerVeggie; i++) {
		    	deck.add(deckPepper.remove(0));
		    	deck.add(deckLettuce.remove(0));
		    	deck.add(deckCarrot.remove(0));
		    	deck.add(deckCabbage.remove(0));
		    	deck.add(deckOnion.remove(0));
		    	deck.add(deckTomato.remove(0));
		    }
		    Collections.shuffle(deck);

            Pile pile1 = new Pile(new ArrayList<CardInterface>());
            Pile pile2 = new Pile(new ArrayList<CardInterface>());
            Pile pile3 = new Pile(new ArrayList<CardInterface>());
            

		    //divide the deck into 3 piles
           
		    for (int i = 0; i < deck.size(); i++) {
		    	if (i % 3 == 0) {
                    pile1.addCard(deck.get(i));
		    	} else if (i % 3 == 1) {
		    		pile2.addCard(deck.get(i));
		    	} else {
		    		pile3.addCard(deck.get(i));
		    	}
		    }

           piles.add(pile1);
           piles.add(pile2);
           piles.add(pile3);

           return piles;
		 
    }

  
    
}
