package PointSalad.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.MarketInterface;

public class PlayerBot implements PlayerInterface{
    private int playerID;
    private ArrayList<CardInterface> hand;
    private MarketInterface market;
    private String nextChoise = null;
    private String message;

    public PlayerBot(int playerID, MarketInterface market){
        this.playerID = playerID;
        this.market = market;
        this.hand = new ArrayList<CardInterface>();
    }

    @Override
    public int getPlayerID() {
        return this.playerID;
    }

    @Override
    public void addCardToHand(CardInterface card) {
        hand.add(card);
    }

    @Override
    public ArrayList<CardInterface> getHand() {
        return this.hand;
    }

    @Override
    public boolean isBot() {
        return true;
        
    }

    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
        this.message = (String) message;
    }

    @Override
    public String readMessage() {
        String choice = "";
        
        // Bot logic
        if (this.nextChoise != null){
            choice = this.nextChoise;
            this.nextChoise = null;
            return choice;
        }
		
        Random random = new Random();
        boolean takePointCard = random.nextBoolean();

        if (takePointCard) {
            // Find the point card with the highest score
            int pileSize = market.copyPiles().size();
            int randomIndex = random.nextInt(pileSize);
            choice = String.valueOf(randomIndex);
           
            
        } else {
            // Find the first one or two veggie cards
            
            for(int i = market.copyCardsFromTable().size()-1; i >= 0; i--) {
                if(market.copyCardFromTable(i) != null) {
                   choice += String.valueOf(i);
                }
                if(choice.length() == 2) {
                    break;
                }
            }
        }
        this.nextChoise = "N";

        return choice;
    }
    
}
