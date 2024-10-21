package PointSalad.src.Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class Client {


    public Client(String ipAddress) throws Exception {
       try{
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

       } catch (Exception e) {
           throw new Exception("Could not connect to server");
       }
        
    }

    private boolean isInputRequired(String message) {
        ArrayList<String> requiredInputs = new ArrayList<String>();
        requiredInputs.add("two vegetables or one point card");
        //requiredInputs.add("Invalid input");
        requiredInputs.add("index of the two cards");
        requiredInputs.add("index of the card");
        requiredInputs.add("like to flip a card");

        for (String input : requiredInputs) {
            if (message.contains(input)) {
                return true;
            }
        }
        return false;
    }
    
}
