package hr.algebra.azulproject.utils;

import hr.algebra.azulproject.model.GameState;
import hr.algebra.azulproject.model.Tiles;

import java.util.List;
import java.util.Map;

public class GameStateUtils {

    public static GameState createGameState(List<Tiles> tiles, Map<String, String> storageColorsForCheck, Map<String, String> playgroundColorsForSerialization,
                                            Map<String, String> factoryPaneTilesForSerialization, List<String> playedFactoryTilesForSerialization,
                                            int index, int turnsCounter, int playerOneScore, int playerTwoScore, int eventCounter, Boolean winner){

        return new GameState(tiles, storageColorsForCheck, playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, winner);
    }
}
