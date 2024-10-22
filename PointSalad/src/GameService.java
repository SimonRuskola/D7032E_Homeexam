package PointSalad.src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Cards.CardType;
import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Network.Client;
import PointSalad.src.Network.Server;
import PointSalad.src.Player.PlayerInterface;




public class GameService{
    
    private MarketInterface market;
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

	public void startGame(String[] args) {

		Scanner in = new Scanner(System.in);
		//System.out.println("enter args[0]");
		//args[0] = in.nextLine();
		//System.out.println("enter args[1]");
		//args[1] = in.nextLine();


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
				endGame();
				return;
			}
		}

		if (checkIfWrongAmountOfPlayers(numberPlayers, numberOfBots)) {
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
		this.currentPlayer = setRandomStartingPlayer(numberPlayers, numberOfBots);
		this.currentPlayerIndex = currentPlayer.getPlayerID();

		while (!isGameOver(this.market)) { // Check if the game is over if all cards on table are null
			playTurn(currentPlayer);

			int score = calculateScore(currentPlayer);

			sendAllPlayersMessage("Player:"+currentPlayer.getPlayerID()+" hand: \n"+displayHand(currentPlayer.getHand()), this.server);

			currentPlayer.getPlayerCommunication().sendMessage("\n\nYour score is: " + score);
			
			currentPlayerIndex++;
			currentPlayerIndex = currentPlayerIndex % server.getPlayers().size();
			currentPlayer = server.getPlayers().get(currentPlayerIndex);


		}

		endGame();

	}

	public PlayerInterface setRandomStartingPlayer(int numberPlayers, int numberOfBots) {
		int startingPlayerIndex = (int) (Math.random() * (numberPlayers+numberOfBots));
		PlayerInterface startingPlayer = server.getPlayer(startingPlayerIndex);
		return startingPlayer;
	}

	public boolean checkIfWrongAmountOfPlayers(int numberPlayers, int numberOfBots) {
		if ((numberPlayers+numberOfBots) < 1 || (numberPlayers+numberOfBots) > 6) {
			return true;
		}
		return false;
	}

	public void endGame() {
    System.out.println("Game over!");

    // Calculate scores for all players
    List<PlayerInterface> players = server.getPlayers();
    Map<PlayerInterface, Integer> playerScores = new HashMap<>();

    for (PlayerInterface player : players) {
        int score = calculateScore(player);
        playerScores.put(player, score);
		player.getPlayerCommunication().sendMessage("\n\nYour final score is: " + score);
    }

    // Determine the winner
    PlayerInterface winner = null;
    int highestScore = Integer.MIN_VALUE;

    for (Map.Entry<PlayerInterface, Integer> entry : playerScores.entrySet()) {
        if (entry.getValue() > highestScore) {
            highestScore = entry.getValue();
            winner = entry.getKey();
        }
    }

    // Display scores and winner
    for (Map.Entry<PlayerInterface, Integer> entry : playerScores.entrySet()) {
        System.out.println("Player " + entry.getKey().getPlayerID() + " scored: " + entry.getValue());
    }

    if (winner != null) {
        System.out.println("The winner is: " + winner.getPlayerID() + " with a score of " + highestScore);
        for (PlayerInterface player : players) {
			player.getPlayerCommunication().sendMessage("The winner is: " + winner.getPlayerID() + " with a score of " + highestScore);
        }
    }

	//System.exit(0);

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
						
							int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
							System.out.println("ID18 MOST/FEWEST: "+aScore + " " );
							totalScore += aScore;
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
							int aScore = missing * addScore;
							System.out.println("ID18 TYPE MISSING: "+aScore + " ");
							totalScore += aScore;
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
							int aScore = totalType * addScore;
							System.out.println("ID18 TYPE >=: "+aScore + " ");
							totalScore += aScore;
						}
					}
					if(criteria.indexOf("SET")>=0) {
						//int addScore = 12;
						String[] expr = criteria.split("=");
						int addScore = Integer.parseInt(expr[1].trim());
						for (CardType vegetable : CardType.values()) {
							int countVeg = countVegetables(hand, vegetable);
							if(countVeg == 0) {
								addScore = 0;
								break;
							}
						}
						System.out.println("ID18 SET: "+addScore + " ");
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
						//System.out.println("ID1/ID2: "+Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim()) + " ");
						int aScore = Integer.parseInt(criteria.substring(criteria.indexOf("=")+1).trim());
						System.err.println("ID1/ID2: "+aScore + " ");
						totalScore += aScore;
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
							System.out.println("ID5/ID11: "+ ((int)countVegetables(hand, CardType.valueOf(vegs[0].trim()))/countSameKind) * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
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
							System.out.println("ID6/ID7/ID12/ID13: "+min * Integer.parseInt(criteria.split("=")[1].trim()) + " ");
							totalScore += min * Integer.parseInt(criteria.split("=")[1].trim());
						}
					}
					else if(parts[0].indexOf("=")>=0) { //ID3
						String veg = parts[0].substring(0, parts[0].indexOf(":"));
						int countVeg = countVegetables(hand, CardType.valueOf(veg));
						System.out.println("ID3: "+((countVeg%2==0)?7:3) + " ");
						totalScore += (countVeg%2==0)?7:3;
					}
					else { //ID4, ID8, ID9, ID10, ID14, ID15, ID16, ID17
						for(int i = 0; i < parts.length; i++) {
							String[] veg = parts[i].split("/");
							System.out.println("ID4/ID8/ID9/ID10/ID14/ID15/ID16/ID17: " + Integer.parseInt(veg[0].trim()) * countVegetables(hand, CardType.valueOf(veg[1].trim())) + " ");
							totalScore += Integer.parseInt(veg[0].trim()) * countVegetables(hand, CardType.valueOf(veg[1].trim()));
						}
					}
				}
		}
		return totalScore;


		
	}

    public void playTurn(PlayerInterface currentPlayer) {
        sendInitialMessages(currentPlayer);

        currentPlayer.getActions().cardChoise(currentPlayer, this.market);

        currentPlayer.getActions().flipCardConformation(currentPlayer);
    }

	private void sendInitialMessages(PlayerInterface currentPlayer) {
		currentPlayer.getPlayerCommunication().sendMessage("\n\n****************************************************************\n" + "It's your turn! Your hand is:" + "\n");
        currentPlayer.getPlayerCommunication().sendMessage(displayHand(currentPlayer.getHand()));
		currentPlayer.getPlayerCommunication().sendMessage(this.market.printMarket());
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

	public void sendAllPlayersMessage(String message, Server server) {
		for (PlayerInterface player : server.getPlayers()) {
			player.getPlayerCommunication().sendMessage(message);
		}
	}

	public boolean isGameOver(MarketInterface market) {

		for (int i = 0; i < market.getTableSize(); i++) {
			if (market.copyCardFromTable(i) != null) {
				return false;
			}
		}

		return true;

		
	}


	// for testing
	public PlayerInterface getCurrentPlayer() { 
		return currentPlayer;
	}

	// for testing
	public void setServer(Server server) {
		this.server = server;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}	
	   

	
       
}

 
