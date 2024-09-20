package ActualProject;

public class GameService implements GameServiceInterface {
    private Game game;

    public GameService(Game game) {
        this.game = game;
    }

    @Override
    public void startGame() {
        // Implement game start logic
    }

    @Override
    public void endGame() {
        // Implement game end logic
    }

}
