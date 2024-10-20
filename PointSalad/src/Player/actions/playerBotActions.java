package PointSalad.src.Player.actions;

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
        
        int firstIndex = random.nextInt(max - min) + min;
        int secondIndex;
        secondIndex = random.nextInt(max - min) + min;
        while (secondIndex == firstIndex);
    
        choice = String.valueOf(firstIndex) + String.valueOf(secondIndex);


     


        

        if (card1 != null || card2 != null) {
            if(card1 != null){
                player.addCardToHand(card1);
            }
            if(card2 != null){
                player.addCardToHand(card2);
            }
            market.setCardsOnTable();
            
        }

    }

    @Override
    public void takeOneCard(PlayerInterface player, MarketInterface market) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeOneCards'");
    }

    @Override
    public void flipCardConformation(PlayerInterface player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flipCardConformation'");
    }

    @Override
    public void flipCard(PlayerInterface player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'flipCard'");
    }

    

   

    

}
