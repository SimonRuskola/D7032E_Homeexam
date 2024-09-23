
package ActualProject;
import java.util.ArrayList;


public class Player implements PlayerInterface {

    private int playerID;
    private boolean isBot;
    private ArrayList<CardInterface> hand;

    public Player(int playerID, boolean isBot) {
        this.playerID = playerID;
        this.isBot = isBot;
        this.hand = new ArrayList<>();
    }

	@Override
	public boolean isBot() {
		return this.isBot;
	}

	@Override
	public void addCardToHand(CardInterface card) {
		hand.add(card);
	}

	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	@Override
	public ArrayList<CardInterface> getHand() {
		return this.hand;
	}
}
