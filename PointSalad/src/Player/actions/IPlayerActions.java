package PointSalad.src.Player.actions;

import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Market.MarketInterface;
import org.json.simple.JSONObject;
/**
 * This interface represents the actioon of how different players can execute
 * the pick card action during the game. Each player is injected with a class
 * that implements this interface
 */
public interface IPlayerActions {

    void cardChoise(PlayerInterface player, MarketInterface market);
    void takeTwoCards(PlayerInterface player, MarketInterface market);
    void takeOneCard(PlayerInterface player, MarketInterface market);
    void flipCardConformation(PlayerInterface player);
    void flipCard(PlayerInterface player);
}