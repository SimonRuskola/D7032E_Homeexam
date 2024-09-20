package ActualProject;

public class Card implements CardInterface {
    private String name;
    private int value;
    private String description;

    @Override
    public String getBackside() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFrontside() {
        // TODO Auto-generated method stub
        return null;
    }

    public Card(String name, String region, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    // Getters and setters
}
