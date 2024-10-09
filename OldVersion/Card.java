package OldVersion;

public class Card implements CardInterface {
    private String frontSide;
    private String backSide;
    private boolean flipped = false;

    public enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }
    

    public Card(String frontSide, String backSide) {
        this.frontSide = frontSide;
        this.backSide = backSide;
    }


    @Override
    public void flipCard() {
        flipped = !flipped;
    }

    @Override
    public boolean isFlipped() {
        return flipped;
    }


    @Override
    public String getFrontSide() {
        return frontSide;
    }

    @Override
    public String getBackSide() {
        return backSide;
    }

    @Override
    public void setFrontSide(String frontSide) {
        this.frontSide = frontSide;
    }

    @Override
    public void setBackSide(String backSide) {
        this.backSide = backSide;
    }
    
    
}
