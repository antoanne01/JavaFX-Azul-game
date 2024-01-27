package hr.algebra.azulproject.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    public List<Tiles> tiles;
    public Map<String, String> storageColorsForCheck;
    public Map<String, String> factoryPaneTilesForSerialization;
    public List<String> playedFactoryTilesForSerialization;
    public Map<String, String> playgroundColorsForSerialization;
    public int index;
    public int turnsCounter;
    public int playerOneScore;
    public int playerTwoScore;
    public int eventCounter;
    private Boolean winner;

    public GameState(List<Tiles> tiles, Map<String, String> storageColorsForCheck, Map<String, String> playgroundColorsForSerialization,
                     Map<String, String> factoryPaneTilesForSerialization, List<String> playedFactoryTilesForSerialization,
                     int index, int turnsCounter, int playerOneScore, int playerTwoScore, int eventCounter) {

        this.tiles = tiles;
        this.storageColorsForCheck = storageColorsForCheck;
        this.factoryPaneTilesForSerialization = factoryPaneTilesForSerialization;
        this.playedFactoryTilesForSerialization = playedFactoryTilesForSerialization;
        this.playgroundColorsForSerialization = playgroundColorsForSerialization;
        this.index = index;
        this.turnsCounter = turnsCounter;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        this.eventCounter = eventCounter;
        this.winner = false;
    }

    public GameState(List<Tiles> tiles, Map<String, String> storageColorsForCheck, Map<String, String> playgroundColorsForSerialization,
                     Map<String, String> factoryPaneTilesForSerialization, List<String> playedFactoryTilesForSerialization,
                     int index, int turnsCounter, int playerOneScore, int playerTwoScore, int eventCounter, Boolean winner) {

        this.tiles = tiles;
        this.storageColorsForCheck = storageColorsForCheck;
        this.factoryPaneTilesForSerialization = factoryPaneTilesForSerialization;
        this.playedFactoryTilesForSerialization = playedFactoryTilesForSerialization;
        this.playgroundColorsForSerialization = playgroundColorsForSerialization;
        this.index = index;
        this.turnsCounter = turnsCounter;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        this.eventCounter = eventCounter;
        this.winner = winner;
    }

    public List<Tiles> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tiles> tiles) {
        this.tiles = tiles;
    }

    public Map<String, String> getStorageColorsForCheck() {
        return storageColorsForCheck;
    }

    public void setStorageColorsForCheck(Map<String, String> storageColorsForCheck) {
        this.storageColorsForCheck = storageColorsForCheck;
    }

    public Map<String, String> getFactoryPaneTilesForSerialization() {
        return factoryPaneTilesForSerialization;
    }

    public void setFactoryPaneTilesForSerialization(Map<String, String> factoryPaneTilesForSerialization) {
        this.factoryPaneTilesForSerialization = factoryPaneTilesForSerialization;
    }

    public List<String> getPlayedFactoryTilesForSerialization() {
        return playedFactoryTilesForSerialization;
    }

    public void setPlayedFactoryTilesForSerialization(List<String> playedFactoryTilesForSerialization) {
        this.playedFactoryTilesForSerialization = playedFactoryTilesForSerialization;
    }

    public Map<String, String> getPlaygroundColorsForSerialization() {
        return playgroundColorsForSerialization;
    }

    public void setPlaygroundColorsForSerialization(Map<String, String> playgroundColorsForSerialization) {
        this.playgroundColorsForSerialization = playgroundColorsForSerialization;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTurnsCounter() {
        return turnsCounter;
    }

    public void setTurnsCounter(int turnsCounter) {
        this.turnsCounter = turnsCounter;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public int getEventCounter() {
        return eventCounter;
    }

    public void setEventCounter(int eventCounter) {
        this.eventCounter = eventCounter;
    }

    public Boolean getWinner() {
        return winner;
    }
}
