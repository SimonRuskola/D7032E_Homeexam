package OldVersion;
import java.util.Collections;
import java.util.ArrayList;

public class Pile implements PileInterface {
    private ArrayList<CardInterface> cards;
    private ArrayList<CardInterface> cardsOnTable = new ArrayList<CardInterface>();

    public Pile(ArrayList<CardInterface> cards) {
        this.cards = cards;
    }
    
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
    
    public int getSize() {
        return this.cards.size();
    }
    @Override
    public CardInterface drawTopCard() {
        if(cards.isEmpty()) {
			return null;
		}
		else{
			return this.cards.remove(0);
		}
    }
    @Override
    public CardInterface drawBottomCard() {
        if(cards.isEmpty()) {
            return null;
        }else{
            return this.cards.remove(this.cards.size()-1);
        }
    }


    
}
