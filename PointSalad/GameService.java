package PointSalad;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

public class GameService{
    
    private MarketInterface market;
	private boolean isGameOver = false;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int numberPlayers = 0;
	private int numberOfBots = 0;
	private int currentPlayerIndex;
	private PlayerInterface currentPlayer;

	public GameService(MarketInterface market) {
		this.market = market;
		market.setPiles(2); // should be changed to number of players
        market.setCardsOnTable();

	}

	public void startGame() {

		System.out.println("Please enter the number of players (1-6): ");
		Scanner in = new Scanner(System.in);
		numberPlayers = in.nextInt();
		System.out.println("Please enter the number of bots (0-5): ");
		numberOfBots = in.nextInt();

		if ((numberPlayers+numberOfBots) < 1 || (numberPlayers+numberOfBots) > 6) {
            throw new IllegalArgumentException("Invalid number of players");
        } 

		for (int i = 0; i < numberPlayers; i++) {
			this.players.add(new Player(i, false));
		}

		// Set random starting player
		this.currentPlayerIndex = (int) (Math.random() * (players.size()));
		this.currentPlayer = players.get(currentPlayerIndex);

		while (!isGameOver) {
			playTurn(currentPlayer);

			int score = calculateScore(currentPlayer);

			currentPlayer.sendMessage("\n\nYour score is: " + score);
			
			currentPlayerIndex++;
			currentPlayer = players.get(currentPlayerIndex % players.size());

			// Check if the game is over if all cards on table are null
			isGameOver();
		

		}

		endGame();

	}

	public void endGame() {
		// Calculate scores
		// Display scores
		// Display winner
	}

	public int countTotalVegetables(ArrayList<CardInterface> hand) {
		int total = 0;
		for (CardInterface card : hand) {
			if (card.isFlipped()) {
				total++;
			}
		}
		return total;
	}

	public int countVegetables(ArrayList<CardInterface> hand, Card.Vegetable vegetable) {
		int total = 0;
		for (CardInterface card : hand) {
			if (card.isFlipped() && card.getBackSide() == vegetable) {
				total++;
			}
		}
		return total;
	}

	public int calculateScore(PlayerInterface player) {
		ArrayList<CardInterface> hand = player.getHand();
		int totalScore = 0;
		ArrayList<CardInterface> vegetableCards = new ArrayList<CardInterface>();
		ArrayList<CardInterface> criteriaCards = new ArrayList<CardInterface>();
		for (CardInterface card : hand) {
			if(card.isFlipped()) {
				vegetableCards.add(card);
			}else{
				criteriaCards.add(card);
			}
		}


		for (CardInterface criteriaCard : criteriaCards) {
			
				String criteria = criteriaCard.toString();
			
				String[] parts = criteria.split(",");
				//ID 18
				if(criteria.indexOf("TOTAL") >= 0 || criteria.indexOf("TYPE") >= 0 || criteria.indexOf("SET") >= 0) {
					if(criteria.indexOf("TOTAL")>=0) {
						int countVeg = countTotalVegetables(hand);
						int thisHandCount = countVeg;
						for(Player p : players) {
							if(p.getPlayerID() != player.getPlayerID()) {
								int playerVeg = countTotalVegetables(p.getHand());
								if((criteria.indexOf("MOST")>=0) && (playerVeg > countVeg)) {
									countVeg = countTotalVegetables(p.getHand());
								}
								if((criteria.indexOf("FEWEST")>=0) && (playerVeg < countVeg)) {
									countVeg = countTotalVegetables(p.getHand());
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
					for(Player p : this.players) {
						if(p.getPlayerID() != player.getPlayerID()) {
							int playerVeg = countVegetables(p.getHand(), Card.Vegetable.valueOf(veg));
							if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
								nrVeg = countVegetables(p.getHand(), Card.Vegetable.valueOf(veg));
							}
							if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
								nrVeg = countVegetables(p.getHand(), Card.Vegetable.valueOf(veg));
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
							System.out.print("ID5/ID11: "+ ((int)countVegetables(hand, Card.Vegetable.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
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
		return totalScore;


		
	}

    public void playTurn(PlayerInterface currentPlayer) {
		if (currentPlayer.isBot()) {
			// Bot logic
		}else{
			currentPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
			currentPlayer.sendMessage(displayHand(currentPlayer.getHand()));
			currentPlayer.sendMessage("\nThe piles are: ");
			currentPlayer.sendMessage(market.printMarket());


			boolean validInput = false;
			while(!validInput){
				currentPlayer.sendMessage("\n\nTake either one point card or up to two vegetable cards.\n");
				String input = currentPlayer.readMessage();
				if (input.length() == 1) {
					int pileIndex = Integer.parseInt(input);
					if (pileIndex >= 0 && pileIndex < 3) {
						CardInterface card = market.getCardFromPile(pileIndex);
						if (card != null) {
							currentPlayer.addCardToHand(card);
							validInput = true;
						}
					}
				} else if (input.length() == 2) {
					int firstIndex = Character.getNumericValue(input.charAt(0));
					int secondIndex = Character.getNumericValue(input.charAt(1));
					if (firstIndex >= 0 && firstIndex < market.getTableSize() && secondIndex >= 0 && secondIndex < market.getTableSize() && firstIndex != secondIndex) {
						CardInterface card1 = market.getCardFromTable(firstIndex);
						CardInterface card2 = market.getCardFromTable(secondIndex);
						if (card1 != null && card2 != null) {
							currentPlayer.addCardToHand(card1);
							currentPlayer.addCardToHand(card2);
							market.setCardsOnTable();							
							validInput = true;
						}
					}
				}
			}

			validInput = false;

			while (validInput == false) {
				currentPlayer.sendMessage(displayHand(currentPlayer.getHand()));
				currentPlayer.sendMessage("\nWould you like to turn a card (Y/N)\n");
				String input = currentPlayer.readMessage();
				if (input.equals("Y")) {
					currentPlayer.sendMessage("\nWhich card would you like to turn?\n");
					currentPlayer.sendMessage(displayHand(currentPlayer.getHand()));
					int cardIndex = Integer.parseInt(currentPlayer.readMessage());
					if (!currentPlayer.getHand().get(cardIndex).isFlipped() && cardIndex >= 0 && cardIndex < currentPlayer.getHand().size()) {
						currentPlayer.getHand().get(cardIndex).flipCard();
						validInput = true;
					} else {
						currentPlayer.sendMessage("\n Please choose another card.\n");
					}
				} else if (input.equals("N")) {
					validInput = true;
				} else {
					currentPlayer.sendMessage("\nInvalid input. Please enter Y or N.\n");
				}
			}




		}

	}

	public String displayHand(ArrayList<CardInterface> hand) {
		String handString = "";
		for (int i = 0; i < hand.size(); i++) {
			handString += i + ": " + hand.get(i).toString() + "\n";
		}
		return handString;
	}

	public void isGameOver() {

		for (int i = 0; i < market.getTableSize(); i++) {
			if (market.getCardFromTable(i) != null) {
				return;
			}
		}

		this.isGameOver = true;
		
	}
       
}
    
