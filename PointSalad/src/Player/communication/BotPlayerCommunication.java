package PointSalad.src.Player.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Random;
import PointSalad.src.Market.Market;

/**
 * This class represents how a remote player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class BotPlayerCommunication implements IPlayerCommunication {

    @Override
    public void sendMessage(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

    @Override
    public String readMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readMessage'");
    }

 


}
