package ActualProject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

public class GameService implements GameServiceInterface {
    
    public ArrayList<Player> players = new ArrayList<>();
	public ArrayList<Pile> piles = new ArrayList<>();
    public Market market;
    private int currentPlayerIndex = 0; // Assuming this is defined somewhere

    public void playTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        
        if (currentPlayer.isBot()) {
            // Bot logic
			// The Bot will randomly decide to take either one point card or two veggie cards 
			// For point card the Bot will always take the point card with the highest score
			// If there are two point cards with the same score, the bot will take the first one
			// For Veggie cards the Bot will pick the first one or two available veggies
        }else{
            
        }

        // Player chooses to draw point card or veggie cards
       	// boolean drawPointCard = // Get player's choice

		currentPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
		//currentPlayer.sendMessage(displayHand(currentPlayer.getHand()));
		currentPlayer.sendMessage("\nThe piles are: ");
		currentPlayer.sendMessage(market.printMarket());


		// Player chooses to draw point card or veggie cards
		//boolean drawPointCard = // Get player's choice
    
		boolean validChoice = false;
		while(!validChoice) {
			currentPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
			String pileChoice = currentPlayer.readMessage();
			if(pileChoice.matches("\\d")) {
				int pileIndex = Integer.parseInt(pileChoice);
				if(piles.get(pileIndex).drawTopCard() == null) {
					currentPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
					continue;
				} else {
					currentPlayer.getHand().add(piles.get(pileIndex).drawTopCard());
					currentPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
					validChoice = true;
				}
			} else {
				int takenVeggies = 0;
				for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
					if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
						currentPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
						validChoice = false;
						break;
					}
					int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
					int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
					int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
					if(piles.get(pileIndex).veggieCards[veggieIndex] == null) {
						currentPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
						validChoice = false;
						break;
					} else {
						if(takenVeggies == 2) {
							validChoice = true;
							break;
						} else {
							currentPlayer.hand.add(piles.get(pileIndex).buyVeggieCard(veggieIndex));
							takenVeggies++;
							//currentPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
							validChoice = true;
						}
					}
				}

			}
		}

        // Option to flip point card to vegetable side
        // Implement this logic based on player's choice

        market.replenishMarket();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean isGameOver() {
        return market.allPilesEmpty();
    }

    public Player determineWinner() {
        return players.stream()
                .max(Comparator.comparingInt(this::calculateScore))
                .orElseThrow();
    }

    private int calculateScore(Player player) {
        int totalScore = 0;

		for (Card criteriaCard : hand) {
			if (criteriaCard.criteriaSideUp) {
				String criteria = criteriaCard.criteria;
				String[] parts = criteria.split(",");

				//ID 18
				if(criteria.indexOf("TOTAL") >= 0 || criteria.indexOf("TYPE") >= 0 || criteria.indexOf("SET") >= 0) {
					if(criteria.indexOf("TOTAL")>=0) {
						int countVeg = countTotalVegetables(hand);
						int thisHandCount = countVeg;
						for(Player p : players) {
							if(p.playerID != currentPlayer.playerID) {
								int playerVeg = countTotalVegetables(p.hand);
								if((criteria.indexOf("MOST")>=0) && (playerVeg > countVeg)) {
									countVeg = countTotalVegetables(p.hand);
								}
								if((criteria.indexOf("FEWEST")>=0) && (playerVeg < countVeg)) {
									countVeg = countTotalVegetables(p.hand);
								}
							}
						}
						if(countVeg == thisHandCount) {
							//int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
							//System.out.print("ID18 MOST/FEWEST: "+aScore + " " );
							totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
						}
					}
					if(criteria.indexOf("TYPE")>=0) {
						String[] expr = criteria.split("/");
						int addScore = Integer.parseInt(expr[0].trim());
						if(expr[1].indexOf("MISSING")>=0) {
							int missing = 0;
							for (Card.Vegetable vegetable : Card.Vegetable.values()) {
								if(countVegetables(hand, vegetable) == 0) {
									missing++;
								}
							}
							//int aScore = missing * addScore;
							//System.out.print("ID18 TYPE MISSING: "+aScore + " ");
							totalScore += missing * addScore;
						}
						else {
							int atLeastPerVegType = Integer.parseInt(expr[1].substring(expr[1].indexOf(">=")+2).trim());
							int totalType = 0;
							for(Card.Vegetable vegetable : Card.Vegetable.values()) {
								int countVeg = countVegetables(hand, vegetable);
								if(countVeg >= atLeastPerVegType) {
									totalType++;
								}
							}
							//int aScore = totalType * addScore;
							//System.out.print("ID18 TYPE >=: "+aScore + " ");
							totalScore += totalType * addScore;
						}
					}
					if(criteria.indexOf("SET")>=0) {
						int addScore = 12;
						for (Card.Vegetable vegetable : Card.Vegetable.values()) {
							int countVeg = countVegetables(hand, vegetable);
							if(countVeg == 0) {
								addScore = 0;
								break;
							}
						}
						//System.out.print("ID18 SET: "+addScore + " ");
						totalScore += addScore;
					}
				}
				//ID1 and ID2
				else if((criteria.indexOf("MOST")>=0) || (criteria.indexOf("FEWEST")>=0)) { //ID1, ID2
					int vegIndex = criteria.indexOf("MOST")>=0 ? criteria.indexOf("MOST")+5 : criteria.indexOf("FEWEST")+7;
					String veg = criteria.substring(vegIndex, criteria.indexOf("=")).trim();
					int countVeg = countVegetables(hand, Card.Vegetable.valueOf(veg));
					int nrVeg = countVeg;
					for(Player p : players) {
						if(p.playerID != currentPlayer.playerID) {
							int playerVeg = countVegetables(p.hand, Card.Vegetable.valueOf(veg));
							if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
								nrVeg = countVegetables(p.hand, Card.Vegetable.valueOf(veg));
							}
							if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
								nrVeg = countVegetables(p.hand, Card.Vegetable.valueOf(veg));
							}
						}
					}
					if(nrVeg == countVeg) {
						//System.out.print("ID1/ID2: "+Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim()) + " ");
						totalScore += Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
					}
				}
			
				//ID3, ID4, ID5, ID6, ID7, ID8, ID9, ID10, ID11, ID12, ID13, ID14, ID15, ID16, ID17
				else if(parts.length > 1 || criteria.indexOf("+")>=0 || parts[0].indexOf("/")>=0) {
					if(criteria.indexOf("+")>=0) { //ID5, ID6, ID7, ID11, ID12, ID13
						String expr = criteria.split("=")[0].trim();
						String[] vegs = expr.split("\\+");
						int[] nrVeg = new int[vegs.length];
						int countSameKind = 1;
						for(int j = 1; j < vegs.length; j++) {
							if(vegs[0].trim().equals(vegs[j].trim())) {
								countSameKind++;
							}
						}
						if(countSameKind > 1) {
							//System.out.print("ID5/ID11: "+ ((int)countVegetables(hand, Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
							totalScore +=  ((int)countVegetables(hand, Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
						} else {
							for(int i = 0; i < vegs.length; i++) {
								nrVeg[i] = countVegetables(hand, Card.Vegetable.valueOf(vegs[i].trim()));
							}
							//find the lowest number in the nrVeg array
							int min = nrVeg[0];
							for (int x = 1; x < nrVeg.length; x++) {
								if (nrVeg[x] < min) {
									min = nrVeg[x];
								}
							}
							//System.out.print("ID6/ID7/ID12/ID13: "+min * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
							totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
						}
					}
					else if(parts[0].indexOf("=")>=0) { //ID3
						String veg = parts[0].substring(0, parts[0].indexOf(":"));
						int countVeg = countVegetables(hand, Card.Vegetable.valueOf(veg));
						//System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
						totalScore += (countVeg%2==0)?7:3;
					}
					else { //ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
						for(int i = 0; i < parts.length; i++) {
							String[] veg = parts[i].split("/");
							//System.out.print("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, Card.Vegetable.valueOf(veg[1].trim())) + " ");
							totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, Card.Vegetable.valueOf(veg[1].trim()));
						}
					}
				}
			}
		}
		return totalScore;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startGame'");
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'endGame'");
	}
    }
    
