package PointSalad.src.Player.actions;

import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONObject;

import PointSalad.src.Cards.Card;
import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Player.PlayerInterface;

/**
 * This class represents the logic a bot player has during the game. Implements
 * the IPlayerActions interface and has logic for how the bot picks a card from
 * its hand
 */

public class playerBotActions implements IPlayerActions {

    Random random = new Random();

    @Override
    public void cardChoise(PlayerInterface player, MarketInterface market) {
        // randomly chosses between taking two cards or one card
        boolean takeSingleCard = random.nextBoolean();
        if (takeSingleCard) {
            takeOneCard(player, market);
        } else {
            takeTwoCards(player, market);
        }
        
    }

    @Override
    public void takeTwoCards(PlayerInterface player, MarketInterface market) {
        // For Veggie cards the Bot will pick the first one or two available veggies

        int min = 0;
        int max = market.getTableSize();
        
        int firstIndex = 0;
        int secondIndex = 0;
        

        boolean firstCardFound = false;
        for (int i = min; i <= max; i++) {
            CardInterface card = market.getCardFromTable(i);
            if (card != null && !firstCardFound) {              // will take the first available card
                    firstIndex = i;
            }
            if (card != null && firstCardFound || i == max) {   // if nothing is found, the last index will be picked
                secondIndex = i;
                break;
            }
        }

        ArrayList<CardInterface> cards = market.takeTwoCardsFromTable(firstIndex, secondIndex);

        player.addCardToHand(cards.get(0));
        player.addCardToHand(cards.get(1));
     

        

    }

    @Override
    public void takeOneCard(PlayerInterface player, MarketInterface market) {
        // will take a random card from top of any of the piles if available
        int min = 0;
        int max = market.getAmountOfPiles();
        ArrayList<Integer> availablePiles = new ArrayList<Integer>();
        for (int i = min; i < max; i++) {
            if(market.getCardFromPile(i) != null){
                availablePiles.add(i);
            }
        }

        int pileIndex = availablePiles.get(random.nextInt(availablePiles.size()));

        player.addCardToHand(market.getCardFromPile(pileIndex));

    }

    @Override
    public void flipCardConformation(PlayerInterface player) {
        // bots dont need to flip cards
    }

    @Override
    public void flipCard(PlayerInterface player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flipCard'");
    }

    

   

    

}
