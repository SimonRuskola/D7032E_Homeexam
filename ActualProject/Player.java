package ActualProject;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Player {
    private int playerID;
    private boolean online;
    private boolean isBot;
    private Socket connection;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private ArrayList<String> region = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private int score = 0;

    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        this.isBot = isBot;
        this.connection = connection;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        this.online = connection != null;
    }

    // Getters and setters
}
