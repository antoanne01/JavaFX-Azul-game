package hr.algebra.azulproject.utils;
import hr.algebra.azulproject.model.GameState;
import hr.algebra.azulproject.model.Tiles;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static final String SAVE_GAME_FILENAME = "AzulGame.ser";

    public static void saveGameToFile(List<Tiles> tiles, Map<String, String> storageColorsForCheck, Map<String, String> playgroundColorsForSerialization,
                                      Map<String, String> factoryPaneTilesForSerialization, List<String> playedFactoryTilesForSerialization,
                                      int index, int turnsCounter, int playerOneScore, int playerTwoScore, int eventCounter, Boolean winner)
    {
        GameState gameState = GameStateUtils.createGameState(tiles, storageColorsForCheck, playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, winner);


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_GAME_FILENAME))) {
            oos.writeObject(gameState);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static GameState loadGameFromFile() {
        GameState recoveredGameState;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileUtils.SAVE_GAME_FILENAME))) {
            recoveredGameState = (GameState) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return recoveredGameState;
    }
}
