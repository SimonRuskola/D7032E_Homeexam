package PointSalad.src;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;

import PointSalad.src.Cards.Card;
import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Cards.CardType;

import PointSalad.src.Network.Client;
import PointSalad.src.Network.Server;

public class GameService{
    
    private MarketInterface market;
	private boolean isGameOver = false;
	//private ArrayList<Player> players = new ArrayList<Player>();
	private int numberPlayers = 0;
	private int numberOfBots = 0;
	private int currentPlayerIndex;
	private PlayerInterface currentPlayer;
	private Server server;

	public GameService(MarketInterface market) {
		this.market = market;
		market.setPiles(2); // should be changed to number of players
        market.setCardsOnTable();

	}

	public void startGame(String[] args2) {

		Scanner in = new Scanner(System.in);
		String[] args = new String[2];

		System.out.println(args.length);

		System.out.println("enter args[0]");
		args[0] = in.nextLine();
		System.out.println("enter args[1]");
		args[1] = in.nextLine();

		

		


		if(args.length == 0){
				System.out.println("Please enter the number of players (1-6): ");
				numberPlayers = in.nextInt();
				System.out.println("Please enter the number of bots (0-5): ");
				numberOfBots = in.nextInt();
			}else{
			
			//check if args[0] is a String (ip addres1s) or an integer (number of players)
			if(args[0].matches("\\d+")) {
				numberPlayers = Integer.parseInt(args[0]);
				numberOfBots = Integer.parseInt(args[1]);
			}
			else {
				try {
					Client client = new Client(args[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if ((numberPlayers+numberOfBots) < 1 || (numberPlayers+numberOfBots) > 6) {
            throw new IllegalArgumentException("Invalid number of players");
        } 

		try {
			server = new Server(numberPlayers, numberOfBots, market);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		//for (int i = 0; i < numberPlayers; i++) {
		//	server.getPlayers().add(new Player(i, false));
		//}

		// Set random starting player
		this.currentPlayerIndex = (int) (Math.random() * (numberPlayers+numberOfBots));
		this.currentPlayer = server.getPlayer(currentPlayerIndex);

		while (!isGameOver) {
			playTurn(currentPlayer);

			int score = calculateScore(currentPlayer);

			currentPlayer.sendMessage("\n\nYour score is: " + score);
			
			currentPlayerIndex++;
			currentPlayer = server.getPlayers().get(currentPlayerIndex % server.getPlayers().size());

			// Check if the game is over if all cards on table are null
			isGameOver();
		

		}

		endGame();

	}

	public void endGame() {
		System.out.println("Game over!");
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

	public int countVegetables(ArrayList<CardInterface> hand, CardType vegetable) {
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

			    
				String criteriaString = criteriaCard.toString(); 										// example "CABBAGE + CABBAGE + CABBAGE = 8 (CARROT)"
				String criteria = criteriaString.replaceAll("\\s*\\([^\\)]*\\)", ""); // example "CABBAGE + CABBAGE + CABBAGE = 8"     
			
				String[] parts = criteria.split(",");
				//ID 18
				if(criteria.indexOf("TOTAL") >= 0 || criteria.indexOf("TYPE") >= 0 || criteria.indexOf("SET") >= 0) {
					if(criteria.indexOf("TOTAL")>=0) {
						int countVeg = countTotalVegetables(hand);
						int thisHandCount = countVeg;
						for(PlayerInterface p : server.getPlayers()) {
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
							for (CardType vegetable : CardType.values()) {
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
							for(CardType vegetable : CardType.values()) {
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
						for (CardType vegetable : CardType.values()) {
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
					int countVeg = countVegetables(hand, CardType.valueOf(veg));
					int nrVeg = countVeg;
					for(PlayerInterface p : server.getPlayers()) {
						if(p.getPlayerID() != player.getPlayerID()) {
							int playerVeg = countVegetables(p.getHand(), CardType.valueOf(veg));
							if((criteria.indexOf("MOST")>=0) && (playerVeg > nrVeg)) {
								nrVeg = countVegetables(p.getHand(), CardType.valueOf(veg));
							}
							if((criteria.indexOf("FEWEST")>=0) && (playerVeg < nrVeg)) {
								nrVeg = countVegetables(p.getHand(), CardType.valueOf(veg));
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
							//System.out.print("ID5/ID11: "+ ((int)countVegetables(hand, CardType.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
							totalScore +=  ((int)countVegetables(hand, CardType.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim());
						} else {
							for(int i = 0; i < vegs.length; i++) {
								nrVeg[i] = countVegetables(hand, CardType.valueOf(vegs[i].trim()));
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
						int countVeg = countVegetables(hand, CardType.valueOf(veg));
						//System.out.print("ID3: "+((countVeg%2==0)?7:3) + " ");
						totalScore += (countVeg%2==0)?7:3;
					}
					else { //ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
						for(int i = 0; i < parts.length; i++) {
							String[] veg = parts[i].split("/");
							//System.out.print("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, CardType.valueOf(veg[1].trim())) + " ");
							totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, CardType.valueOf(veg[1].trim()));
						}
					}
				}
		}
		return totalScore;


		
	}

    public void playTurn(PlayerInterface currentPlayer) {
			currentPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
			currentPlayer.sendMessage(displayHand(currentPlayer.getHand()));
			//currentPlayer.sendMessage("\nThe piles are: ");
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
						currentPlayer.sendMessage("\n Please choose another card to turn.\n");
					}
				} else if (input.equals("N")) {
					validInput = true;
				} else {
					currentPlayer.sendMessage("\nInvalid input. Please enter Y or N. Would you like to turn a card (Y/N) \n");
				}
			}


	}

	public String displayHand(ArrayList<CardInterface> hand) {
		
		if (hand == null) {
			return "Hand is empty";
		}
		
		String handString = "";

		for (int i = 0; i < hand.size(); i++) {
			handString += i + ": " + hand.get(i).toString() + "\n";
		}
		return handString;
	}

	public void isGameOver() {

		for (int i = 0; i < market.getTableSize(); i++) {
			if (market.copyCardFromTable(i) != null) {
				return;
			}
		}

		this.isGameOver = true;
		
	}
       
}
    
