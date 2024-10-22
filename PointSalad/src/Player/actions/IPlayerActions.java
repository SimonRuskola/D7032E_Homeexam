package PointSalad.src.Player.actions;

import PointSalad.src.Player.PlayerInterface;
import PointSalad.src.Market.MarketInterface;

// IPlayerActions is an interface that defines how players can interact with the game.

public interface IPlayerActions {

    void cardChoise(PlayerInterface player, MarketInterface market);
    void takeTwoCards(PlayerInterface player, MarketInterface market);
    void takeOneCard(PlayerInterface player, MarketInterface market);
    void flipCardConformation(PlayerInterface player);
    void flipCard(PlayerInterface player);
}