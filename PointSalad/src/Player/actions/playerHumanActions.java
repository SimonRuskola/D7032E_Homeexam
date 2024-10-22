package PointSalad.src.Player.actions;


import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Market.Market;
import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Market.MarketInterface;

// playerHumanActions is a class that implements the IPlayerActions interface. It defines the actions that a human player can take in the game.

public class playerHumanActions implements IPlayerActions {

    int max;
    int min;


    @Override
    public void cardChoise(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
            player.getPlayerCommunication().sendMessage("do you want to either take two vegetables or one point card? (V/P)");

            String input = player.getPlayerCommunication().readMessage();

            if (input.equals("V")) {
                validInput = true;
                takeTwoCards(player, market);
            
            } else if (input.equals("P")) {
                validInput = true;
                takeOneCard(player, market);
            } else {
                player.getPlayerCommunication().sendMessage("Invalid input, please enter 'V' or 'P'");
            }
        }
    }

    @Override
    public void takeTwoCards(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
            
        player.getPlayerCommunication().sendMessage("Please enter the index of the two cards you would like to take (example \"01\") \n");
        String input = player.getPlayerCommunication().readMessage();

        if(input.length() != 2){
            player.getPlayerCommunication().sendMessage("Invalid input, please enter two indexes");
            continue;
        }
        

        max = market.getTableSize();
        min = 0;


        int firstIndex = Character.getNumericValue(input.charAt(0));
		int secondIndex = Character.getNumericValue(input.charAt(1));

        
		if (firstIndex >= min && firstIndex < max && secondIndex >= min && secondIndex < max && firstIndex != secondIndex) {
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
                validInput = true;
				
			}
		}
        
    
        }
		
    }

    @Override
    public void takeOneCard(PlayerInterface player, MarketInterface market) {
        boolean validInput = false;

        while (!validInput) {
            player.getPlayerCommunication().sendMessage("Please enter the index of the card you would like to take (example \\\"0\\\")");
            String input = player.getPlayerCommunication().readMessage();
     
            int pileIndex = Integer.parseInt(input);

            max = market.getAmountOfPiles();
            min = 0;

            if (pileIndex >= min && pileIndex < max) {
			    CardInterface card = market.getCardFromPile(pileIndex);
			    if (card != null) {
			    	player.addCardToHand(card);
                    validInput = true;
			    }
	    	}

            
        }

        
    }

    @Override
    public void flipCardConformation(PlayerInterface player) {
        boolean validInput = false;

        while (!validInput) {
            player.getPlayerCommunication().sendMessage("Would you like to flip a card? (Y/N)");
            String input = player.getPlayerCommunication().readMessage();

            if (input.equals("Y")) {
                validInput = true;
                flipCard(player);
            } else if (input.equals("N")) {
                player.getPlayerCommunication().sendMessage("You have chosen not to flip a card");
                validInput = true;
            } else {
                player.getPlayerCommunication().sendMessage("Invalid input, please enter 'Y' or 'N'");
            }

       
        }
    }

    @Override
    public void flipCard(PlayerInterface player) {
        
        boolean validInput = false;

        while (!validInput) {
            player.getPlayerCommunication().sendMessage("Please enter the index of the card you would like to flip \n");
            player.getPlayerCommunication().sendMessage("Your hand: \n");
            player.getPlayerCommunication().sendMessage(player.getHand().toString());
            String input = player.getPlayerCommunication().readMessage();
            int cardIndex = Integer.parseInt(input);

            max = player.getHand().size();
            min = 0;

            if (cardIndex >= min && cardIndex <= max && player.getHand().get(cardIndex).isFlipped() == false) {
                CardInterface card = player.getHand().get(cardIndex);
                if (card != null) {
                    card.flipCard();
                    validInput = true;
                }
            }
        }



    }

    
   

   

}