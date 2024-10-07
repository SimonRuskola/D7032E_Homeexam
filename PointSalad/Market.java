package PointSalad;
import java.util.ArrayList;


public class Market {
    private int nrPlayers;
    private ArrayList<PileInterface> piles = new ArrayList<>(3);
    private CardFactoryInterface cardFactory;
    private ArrayList<CardInterface> cardsOnTable = new ArrayList<CardInterface>(6);

    public Market(int nrPlayers, CardFactoryInterface cardFactory) {
        this.cardFactory = cardFactory;
        this.nrPlayers = nrPlayers;
        if (nrPlayers < 1 || nrPlayers > 6) {
            throw new IllegalArgumentException("Invalid number of players");
        } 

        for (int i = 0; i < 6; i++) {
            cardsOnTable.add(null);
        }
        setPiles();
        setCardOnTable();

    }

    public void setPiles() {
       piles = this.cardFactory.createPile();

    }

    public void setCardOnTable() {
        for (int i = 0; i < cardsOnTable.size(); i++) {
            if (cardsOnTable.get(i) == null) {
                int pileIndex = i / 2;
                PileInterface pile = piles.get(pileIndex);
                CardInterface card = pile.drawTopCard();
                if (card != null) {
                    cardsOnTable.set(i, card);
                } else {
                    // If the pile is empty, draw from the bottom of the pile with the most cards
                    PileInterface maxPile = getMaxPile();
                    if (maxPile != null) {
                        card = maxPile.drawBottomCard();
                        if (card != null) {
                            cardsOnTable.set(i, card);
                        }
                    }
                }
            }
        }
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


    public void printMarket() {
        // Print cards on the table
        System.out.println("Cards on the table:");
        for (CardInterface card : cardsOnTable) {
            System.out.println(card.toString());
        }

    // Print top cards of the piles
        System.out.println("\nTop cards of the piles:");
        for (PileInterface pile : piles) {
            CardInterface topCard = pile.drawTopCard();
            if (topCard != null) {
                System.out.println(topCard.toString());
            } else {
                System.out.println("Pile is empty");}
        }
    }

}