package hr.algebra.azulproject.controller;

import java.util.Map;

public class CalculatePoints {
    public static int calculateScoring(Map<String, String> storageColor) {
        int playerOneScore = 0;
        try {
            for (Map.Entry<String, String> entry : storageColor.entrySet()) {
                String currentKey = entry.getKey();
                if (currentKey.startsWith("Pane[id=storage")) {
                    playerOneScore++;
                }
            }
        } catch (Exception e) {
            System.out.println("Check winner error: " + e.getMessage());
        }
        return playerOneScore;
    }

    public static int calculateP2Scoring(Map<String, String> storageColor) {

        int playerTwoScore = 0;
        try {
            for (Map.Entry<String, String> entry : storageColor.entrySet()) {
                String currentKey = entry.getKey();
                if (currentKey.startsWith("Pane[id=player2Storage")) {
                    playerTwoScore++;
                }
            }
        } catch (Exception e) {
            System.out.println("Check winner error: " + e.getMessage());
        }
        return playerTwoScore;
    }
}

