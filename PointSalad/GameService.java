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

			currentPlayerIndex++;
			currentPlayer = players.get(currentPlayerIndex % players.size());
			
		}

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




		}

	}

	public String displayHand(ArrayList<CardInterface> hand) {
		String handString = "";
		for (int i = 0; i < hand.size(); i++) {
			handString += i + ": " + hand.get(i).toString() + "\n";
		}
		return handString;
	}
       
}
    
