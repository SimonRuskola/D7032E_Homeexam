package OldVersion;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        // Initialize streams
    }

    @Override
    public void run() {
        // Handle client communication
    }
}
