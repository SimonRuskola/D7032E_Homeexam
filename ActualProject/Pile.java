package ActualProject;
import java.util.Collections;
import java.util.List;

public class Pile implements PileInterface {
    private List<CardInterface> cards;

    public Pile(List<CardInterface> cards) {
        this.cards = cards;
    }
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public CardInterface drawCard() {
        return this.cards.remove(0);
    }
    
    public int getSize() {
        return this.cards.size();
    }
    
}
