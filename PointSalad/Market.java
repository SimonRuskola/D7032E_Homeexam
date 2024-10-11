package PointSalad;
import java.util.ArrayList;


public class Market implements MarketInterface {
    private ArrayList<PileInterface> piles = new ArrayList<>(3);
    private CardFactoryInterface cardFactory;
    private ArrayList<CardInterface> cardsOnTable = new ArrayList<CardInterface>(6);

    public Market(CardFactoryInterface cardFactory) {
        this.cardFactory = cardFactory;
        

        for (int i = 0; i < 6; i++) {
            cardsOnTable.add(null);
        }

    }

    public void setPiles(int playerCount) {
        this.piles = this.cardFactory.createPile(playerCount);
    }

    public void setCardsOnTable() {
        for (int i = 0; i < cardsOnTable.size(); i++) {
            if (cardsOnTable.get(i) == null) {
                int pileIndex = i / 2;
                PileInterface pile = piles.get(pileIndex);
                CardInterface card = pile.drawTopCard();
                if (card != null) {
                    card.flipCard();
                    cardsOnTable.set(i, card);
                } else {
                    // If the pile is empty, draw from the bottom of the pile with the most cards
                    PileInterface maxPile = getMaxPile();
                    if (maxPile != null) {
                        card = maxPile.drawBottomCard();
                        if (card != null) {
                            card.flipCard();
                            cardsOnTable.set(i, card);
                        }
                    }
                }
            }
        }
    }
    public int getTableSize() {
        return cardsOnTable.size();
    }

    public CardInterface getCardFromTable(int index) {
        CardInterface card = cardsOnTable.get(index);
        if (card != null) {
            cardsOnTable.set(index, null);
        }
        return card;
    }

    public CardInterface copyCardFromTable(int index) {
        CardInterface card = cardsOnTable.get(index);
        return card;  
    }

    public CardInterface getCardFromPile(int index) {
        PileInterface pile = piles.get(index);
        CardInterface card = pile.drawTopCard();
        return card;
    }

    private PileInterface getMaxPile() {
        PileInterface maxPile = null;
        int maxSize = 0;
        for (PileInterface pile : piles) {
            int size = pile.getSize();
            if (size > maxSize) {
                maxSize = size;
                maxPile = pile;
            }
        }
        return maxPile;
    }



    public void printMarketPiles() {
        for (int i = 0; i < piles.size(); i++) {
            System.out.println("Pile " + i + ": ");
            for (CardInterface card : piles.get(i).getCards()) {
                System.out.println(card.toString() + " \n");
            }
        }
    }


    public String printMarket() { //return a string of the market
        StringBuilder marketString = new StringBuilder();
    
        // Append cards on the table
        marketString.append("Cards on the table:\n");
        int cardIndex = 0;
        for (CardInterface card : cardsOnTable) {
            if (card == null) {
                marketString.append(cardIndex + ": Slot is empty\n");
            } else {
                marketString.append(cardIndex + ": " + card.toString()).append("\n");
            }
            cardIndex++;
        }
    
        // Append top cards of the piles
        marketString.append("\nTop cards of the piles:\n");
        cardIndex = 0;
        for (PileInterface pile : piles) {
            CardInterface topCard = pile.getTopCard();
            if (topCard != null) {
                marketString.append(cardIndex + ": " +topCard.toString()).append("\n");
            } else {
                marketString.append(cardIndex + ": Slot is empty\n");
            }
            cardIndex++;
        }
    
        return marketString.toString();
    }

}