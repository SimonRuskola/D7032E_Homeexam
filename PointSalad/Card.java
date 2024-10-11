package PointSalad;

public class Card implements CardInterface {
    private Object frontSide;
    private Object backSide;
    private boolean flipped = false;

    protected enum Vegetable {
        PEPPER, LETTUCE, CARROT, CABBAGE, ONION, TOMATO
    }

  
    public Card(Vegetable vegetable, String criteria) {
		this.backSide = vegetable;
		this.frontSide = criteria;
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
    public Object getFrontSide() {
        return frontSide;
    }

    @Override
    public Object getBackSide() {
        return backSide;
    }

    @Override
    public void setFrontSide(Object frontSide) {
        this.frontSide = frontSide;
    }

    @Override
    public void setBackSide(Object backSide) {
        this.backSide = backSide;
    }

	

	@Override
	public String toString() {
		if(!flipped) {
			return frontSide + " (" + backSide+ ")";
		} else {
			return backSide.toString();
		}
	}
    
    
}
