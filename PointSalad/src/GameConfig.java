package PointSalad.src;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameConfig {
    private JSONArray actions;

    public GameConfig(String configFilePath) {
        JSONParser parser = new JSONParser();
        try {
            actions = (JSONArray) parser.parse(new FileReader(configFilePath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getAction(int index) {
        return (JSONObject) actions.get(index);
    }
}
