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
import PointSalad.src.GameConfig;

/**
 * This class represents how a remote player can communicate with the game.
 * Implements the IPlayerCommunication interface
 */
public class BotPlayerCommunication implements IPlayerCommunication {

    String message;
    GameConfig config;
    Market market;

    public BotPlayerCommunication(GameConfig config, Market market) {
        this.config = config;
        this.market = market;
      
    }

    /**
     * @param message Takes the message the game has sent to the player
     */
    @Override
    public void sendMessage(String message) {
		System.out.println(message);
        this.message = message;	         
		return;
    }

    /**
     * @return String The string that the player sends for the game to recive as
     *         input
     */
     @Override
    public String readMessage() {
        String choice = "";
        Random random = new Random();

        JSONArray actions = config.getActions();
        for (Object actionObj : actions) {
            JSONObject action = (JSONObject) actionObj;
            String actionMessage = (String) action.get("message");
            if (this.message.contains(actionMessage)) {
                JSONObject rules = (JSONObject) action.get("rules");
                if (rules != null) {
                    if (rules.containsKey("singleCard") && rules.containsKey("twoCards")) {
                        // Bot decides randomly whether to take a single card or two cards
                        boolean takeSingleCard = random.nextBoolean();
                        if (takeSingleCard) {
                            // For point card the Bot will take a random card from the specified range
                            int min = ((Long) ((JSONObject) rules.get("singleCard")).get("min")).intValue();
                            Object maxObj = ((JSONObject) rules.get("singleCard")).get("max");
                            int max = config.parseMaxValue(maxObj, this, market);
                            int pileIndex = random.nextInt(max - min + 1) + min;
                            choice = String.valueOf(pileIndex);
                        } else {
                            // For Veggie cards the Bot will pick the first one or two available veggies
                            int min = ((Long) ((JSONObject) rules.get("twoCards")).get("min")).intValue();
                            Object maxObj = ((JSONObject) rules.get("twoCards")).get("max");
                            int max = config.parseMaxValue(maxObj, this, market);
                            if (max > 1) {
                                int firstIndex = random.nextInt(max - min) + min;
                                int secondIndex;
                                do {
                                    secondIndex = random.nextInt(max - min) + min;
                                } while (secondIndex == firstIndex);
                                choice = String.valueOf(firstIndex) + String.valueOf(secondIndex);
                            } else if (max == 1) {
                                choice = "0";
                            }
                        }
                    } else if (rules.containsKey("yesNo")) {
                        // For yes/no questions, the Bot will say no
                        choice = "N";
                    } else if (rules.containsKey("cardIndex")) {
                        // For card index selection, the Bot will choose a random card from its hand
                        int min = ((Long) ((JSONObject) rules.get("cardIndex")).get("min")).intValue();
                        Object maxObj = ((JSONObject) rules.get("cardIndex")).get("max");
                        int max = config.parseMaxValue(maxObj, this, market);
                        int cardIndex = random.nextInt(max - min) + min;
                        choice = String.valueOf(cardIndex);
                    }
                }
                break;
            }
        }

        return choice;
    }


}
