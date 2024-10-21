package PointSalad.src.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import PointSalad.src.Cards.CardInterface;
import PointSalad.src.Market.MarketInterface;
import PointSalad.src.Player.actions.IPlayerActions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PlayerBot extends Player{
   
    private MarketInterface market;
    

    public PlayerBot(int playerID, IPlayerActions actions, MarketInterface market){
        super(playerID, actions);
        this.market = market;
       
    }

    
}
