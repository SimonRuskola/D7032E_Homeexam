package PointSalad.src.Cards;


public interface CardInterface {

    
   // Getters
   Object getFrontSide();
   Object getBackSide();
   
   // Setters
   void setFrontSide(Object frontSide);
   void setBackSide(Object backSide);
   
    // Flip methods
    void flipCard();

    // Utility methods
    boolean isFlipped();

    String toString();

   
}
