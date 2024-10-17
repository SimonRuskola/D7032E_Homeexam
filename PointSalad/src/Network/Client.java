package PointSalad.src.Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import PointSalad.src.GameConfig;
import org.json.simple.JSONObject;

public class Client {

    private GameConfig config;

    public Client(String ipAddress, GameConfig config) throws Exception {
        this.config = config;
        //Connect to server
        Socket aSocket = new Socket(ipAddress, 2048);
        ObjectOutputStream outToServer = new ObjectOutputStream(aSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(aSocket.getInputStream());
        String nextMessage = "";
        while (!nextMessage.contains("winner")) {
            nextMessage = (String) inFromServer.readObject();
            System.out.println(nextMessage);
            if (isInputRequired(nextMessage)) {
                Scanner in = new Scanner(System.in);
                outToServer.writeObject(in.nextLine());
            }
        }
    }

    private boolean isInputRequired(String message) {
        for (int i = 0; i < config.getActions().size(); i++) {
            JSONObject action = config.getAction(i);
            String actionMessage = (String) action.get("message");
            if (message.contains(actionMessage)) {
                return true;
            }
        }
        return false;
    }
    
}
