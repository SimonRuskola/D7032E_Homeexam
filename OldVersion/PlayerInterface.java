package OldVersion;
import java.util.List;

public interface PlayerInterface {

    int getPlayerID();
    void addCardToHand(CardInterface card);
    List<CardInterface> getHand();
    boolean isBot();
}
