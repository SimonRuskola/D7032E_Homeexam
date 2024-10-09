package OldVersion;


public interface CardInterface {
   // Getters
   String getFrontSide();
   String getBackSide();
   
   // Setters
   void setFrontSide(String frontSide);
   void setBackSide(String backSide);
   
    // Flip methods
    void flipCard();

    // Utility methods
    boolean isFlipped();
}
