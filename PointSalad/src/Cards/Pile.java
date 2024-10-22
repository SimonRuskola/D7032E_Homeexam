package PointSalad.src.Cards;
import java.util.Collections;
import java.util.ArrayList;


public class Pile implements PileInterface {
    private ArrayList<CardInterface> cards;

    public Pile(ArrayList<CardInterface> arrayList) {
        this.cards = arrayList;
    }
    
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public void addCard(CardInterface card){
        this.cards.add(card);
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


    @Override
    public ArrayList<CardInterface> getCards(){
        return this.cards;
    }

    @Override
    public CardInterface getBottomCard() {
        if(cards.isEmpty()) {
            return null;
        }else{
            return this.cards.get(this.cards.size()-1);
        }
        
    }

    @Override
    public CardInterface getTopCard() {
        if(cards.isEmpty()) {
            return null;
        }else{
            return this.cards.get(0);
        }
        
    }

    @Override
    public CardInterface getCard(int index) {
        if(cards.isEmpty()) {
            return null;
        }else{
            return this.cards.get(index);
        }
    }


    
}
