package ActualProject;


public interface CardInterface {
   // Getters
   String getContent();
   
   // Setters
   void setContent(String content);
   
    // Flip methods
    void flipToFrontSide();
    void flipToBackSide();

    // Utility methods
    boolean isFrontSide();
    boolean isBackSide();
}
