package PointSalad.src.Player.actions;


import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Market.Market;
import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Market.MarketInterface;

/**
 * This class represents the standard logic a human player has during the game.
 * Implements the IPlayerActions interface and has logic for allowing the player
 * to choose
 * from its hand. Uses each players abstrasction of communication for
 * interaction with player.
 */
public class playerHumanActions implements IPlayerActions {

    int max;
    int min;


    @Override
    public void cardChoise(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
            player.sendMessage("do you want to either take two vegetables or one point card? (V/P)");
            String input = player.readMessage();

            if (input.equals("V")) {
                validInput = true;
                takeTwoCards(player, market);
            
            } else if (input.equals("P")) {
                validInput = true;
                takeOneCard(player, market);
            } else {
                player.sendMessage("Invalid input, please enter 'V' or 'P'");
            }
        }
    }

    @Override
    public void takeTwoCards(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
            
        
        player.sendMessage("Please enter the index of the two cards you would like to take");
        String input = player.readMessage();

        max = market.getTableSize();
        min = 0;


        int firstIndex = Character.getNumericValue(input.charAt(0));
		int secondIndex = Character.getNumericValue(input.charAt(1));

        
		if (firstIndex >= min && firstIndex <= max && secondIndex >= min && secondIndex <= max && firstIndex != secondIndex) {
			CardInterface card1 = market.getCardFromTable(firstIndex);
			CardInterface card2 = market.getCardFromTable(secondIndex);
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
        validInput = true;
    
        }
		
    }

    @Override
    public void takeOneCard(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
        player.sendMessage("Please enter the index of the card you would like to take");
        String input = player.readMessage();
        int pileIndex = Integer.parseInt(input);

        max = market.getAmountOfPiles();
        min = 0;

        if (pileIndex >= min && pileIndex <= max) {
			CardInterface card = market.getCardFromPile(pileIndex);
			if (card != null) {
				player.addCardToHand(card);
	
			}
		}

        validInput = true;
        }

        
    }

    @Override
    public void flipCardConformation(PlayerInterface player) {
        boolean validInput = false;

        while (!validInput) {
        player.sendMessage("Would you like to flip a card? (Y/N)");
        String input = player.readMessage();

        if (input.equals("Y")) {
            validInput = true;
            flipCard(player);
        } else if (input.equals("N")) {
            player.sendMessage("You have chosen not to flip a card");
            validInput = true;
        } else {
            player.sendMessage("Invalid input, please enter 'Y' or 'N'");
        }

       
        }
    }

    @Override
    public void flipCard(PlayerInterface player) {
        
        boolean validInput = false;

        while (!validInput) {
        player.sendMessage("Please enter the index of the card you would like to flip");
        String input = player.readMessage();
        int cardIndex = Integer.parseInt(input);

        max = player.getHand().size();
        min = 0;

        if (cardIndex >= min && cardIndex <= max) {
            CardInterface card = player.getHand().get(cardIndex);
            if (card != null) {
                card.flipCard();
                validInput = true;
            }
        }
        }



    }

    
   

   

}