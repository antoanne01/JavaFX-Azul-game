package hr.algebra.azulproject.controller;
import hr.algebra.azulproject.utils.DialogUtils;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class CheckResults {
    public static Boolean checkWinner(Map<String, String> storageColor, GridPane playerOneScoreBoard, GridPane playerTwoScoreBoard) {

        try{
            // Player 1 storage control

            if (storageColor.get("Pane[id=storage00]") != null && storageColor.get("Pane[id=storage00]").equals("Blue")
            && storageColor.get("Pane[id=storage01]") != null && storageColor.get("Pane[id=storage01]").equals("Yellow")
            && storageColor.get("Pane[id=storage02]") != null && storageColor.get("Pane[id=storage02]").equals("Red")
            && storageColor.get("Pane[id=storage03]") != null && storageColor.get("Pane[id=storage03]").equals("Black")
            && storageColor.get("Pane[id=storage04]") != null && storageColor.get("Pane[id=storage04]").equals("LBlue")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=storage10]") != null && storageColor.get("Pane[id=storage10]").equals("LBlue")
//                    && storageColor.get("Pane[id=storage11]") != null && storageColor.get("Pane[id=storage11]").equals("Blue")
//                    && storageColor.get("Pane[id=storage12]") != null && storageColor.get("Pane[id=storage12]").equals("Yellow")
//                    && storageColor.get("Pane[id=storage13]") != null && storageColor.get("Pane[id=storage13]").equals("Red")
//                    && storageColor.get("Pane[id=storage13]") != null && storageColor.get("Pane[id=storage14]").equals("Black")
            ){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=storage20]") != null && storageColor.get("Pane[id=storage20]").equals("Black")
                    && storageColor.get("Pane[id=storage21]") != null && storageColor.get("Pane[id=storage21]").equals("LBlue")
                    && storageColor.get("Pane[id=storage22]") != null && storageColor.get("Pane[id=storage22]").equals("Blue")
                    && storageColor.get("Pane[id=storage23]") != null && storageColor.get("Pane[id=storage23]").equals("Yellow")
                    && storageColor.get("Pane[id=storage24]") != null && storageColor.get("Pane[id=storage24]").equals("Red")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=storage30]") != null && storageColor.get("Pane[id=storage30]").equals("Red")
                    && storageColor.get("Pane[id=storage31]") != null && storageColor.get("Pane[id=storage31]").equals("Black")
                    && storageColor.get("Pane[id=storage32]") != null && storageColor.get("Pane[id=storage32]").equals("LBlue")
                    && storageColor.get("Pane[id=storage33]") != null && storageColor.get("Pane[id=storage33]").equals("Blue")
                    && storageColor.get("Pane[id=storage34]") != null && storageColor.get("Pane[id=storage34]").equals("Yellow")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=storage40]") != null && storageColor.get("Pane[id=storage40]").equals("Yellow")
                    && storageColor.get("Pane[id=storage41]") != null && storageColor.get("Pane[id=storage41]").equals("Red")
                    && storageColor.get("Pane[id=storage42]") != null && storageColor.get("Pane[id=storage42]").equals("Black")
                    && storageColor.get("Pane[id=storage43]") != null && storageColor.get("Pane[id=storage43]").equals("LBlue")
                    && storageColor.get("Pane[id=storage44]") != null && storageColor.get("Pane[id=storage44]").equals("Blue")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            // Player 2 storage control

            else if (storageColor.get("Pane[id=player2Storage00]") != null && storageColor.get("Pane[id=player2Storage00]").equals("Blue")
                    && storageColor.get("Pane[id=player2Storage01]") != null && storageColor.get("Pane[id=player2Storage01]").equals("Yellow")
                    && storageColor.get("Pane[id=player2Storage02]") != null && storageColor.get("Pane[id=player2Storage02]").equals("Red")
                    && storageColor.get("Pane[id=player2Storage03]") != null && storageColor.get("Pane[id=player2Storage03]").equals("Black")
                    && storageColor.get("Pane[id=player2Storage04]") != null && storageColor.get("Pane[id=player2Storage04]").equals("LBlue")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=player2Storage10]") != null && storageColor.get("Pane[id=player2Storage10]").equals("LBlue")
//                    && storageColor.get("Pane[id=player2Storage11]") != null && storageColor.get("Pane[id=player2Storage11]").equals("Blue")
//                    && storageColor.get("Pane[id=player2Storage12]") != null && storageColor.get("Pane[id=player2Storage12]").equals("Yellow")
//                    && storageColor.get("Pane[id=player2Storage13]") != null && storageColor.get("Pane[id=player2Storage13]").equals("Red")
//                    && storageColor.get("Pane[id=player2Storage14]") != null && storageColor.get("Pane[id=player2Storage14]").equals("Black")
            ){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=player2Storage20]") != null && storageColor.get("Pane[id=player2Storage20]").equals("Black")
                    && storageColor.get("Pane[id=player2Storage21]") != null && storageColor.get("Pane[id=player2Storage21]").equals("LBlue")
                    && storageColor.get("Pane[id=player2Storage22]") != null && storageColor.get("Pane[id=player2Storage22]").equals("Blue")
                    && storageColor.get("Pane[id=player2Storage23]") != null && storageColor.get("Pane[id=player2Storage23]").equals("Yellow")
                    && storageColor.get("Pane[id=player2Storage24]") != null && storageColor.get("Pane[id=player2Storage24]").equals("Red")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=player2Storage30]") != null && storageColor.get("Pane[id=player2Storage30]").equals("Red")
                    && storageColor.get("Pane[id=player2Storage31]") != null && storageColor.get("Pane[id=player2Storage31]").equals("Black")
                    && storageColor.get("Pane[id=player2Storage32]") != null && storageColor.get("Pane[id=player2Storage32]").equals("LBlue")
                    && storageColor.get("Pane[id=player2Storage33]") != null && storageColor.get("Pane[id=player2Storage33]").equals("Blue")
                    && storageColor.get("Pane[id=player2Storage34]") != null && storageColor.get("Pane[id=player2Storage34]").equals("Yellow")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
            else if (storageColor.get("Pane[id=player2Storage40]") != null && storageColor.get("Pane[id=player2Storage40]").equals("Yellow")
                    && storageColor.get("Pane[id=player2Storage41]") != null && storageColor.get("Pane[id=player2Storage41]").equals("Red")
                    && storageColor.get("Pane[id=player2Storage42]") != null && storageColor.get("Pane[id=player2Storage42]").equals("Black")
                    && storageColor.get("Pane[id=player2Storage43]") != null && storageColor.get("Pane[id=player2Storage43]").equals("LBlue")
                    && storageColor.get("Pane[id=player2Storage44]") != null && storageColor.get("Pane[id=player2Storage44]").equals("Blue")){
                return countPoints(playerOneScoreBoard, playerTwoScoreBoard);
            }
        }
        catch (Exception e){
            System.out.println("Check winner error: " + e.getMessage());
        }
        return false;
    }

    private static Boolean countPoints(GridPane playerOneScoreBoard, GridPane playerTwoScoreBoard) {
        int playerOne = 0;
        int playerTwo = 0;
        for (Node node : playerOneScoreBoard.getChildren()) {
            if(node instanceof Button button){
                String style = button.getStyle();
                if(style.contains("-fx-background-color: red")){
                    playerOne = Integer.parseInt(button.getText());
                }
            }
        }
        for (Node node : playerTwoScoreBoard.getChildren()) {
            if(node instanceof Button button){
                String style = button.getStyle();
                if(style.contains("-fx-background-color: red")){
                    playerTwo = Integer.parseInt(button.getText());
                }
            }
        }

        if(playerOne > playerTwo){
            DialogUtils.printWinnerMessage("Client");
                Platform.exit();
                return true;
        }
        else if(playerOne < playerTwo){
            DialogUtils.printWinnerMessage("Server");
            Platform.exit();
            return true;
        }
        else{
            DialogUtils.printDrawMessage();
            Platform.exit();
            return true;
        }
    }

    public static void displayP2Score(int playerTwoScore, GridPane playerTwoScoreBoard) {
        for (Node node : playerTwoScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                int buttonNumber = Integer.parseInt(button.getText().split("\n")[0]);

                if(buttonNumber < playerTwoScore){
                    button.setStyle("");
                    if(buttonNumber % 5 == 0){
                        button.setStyle("-fx-background-color: yellow;");
                    }
                }
                else if (buttonNumber == playerTwoScore) {
                    button.setStyle("-fx-background-color: red;");
                }
            }
        }
    }

    public static void displayP1Score(int playerOneScore, GridPane playerOneScoreBoard) {
        for (Node node : playerOneScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                int buttonNumber = Integer.parseInt(button.getText().split("\n")[0]);

                if(buttonNumber < playerOneScore){
                    button.setStyle("");
                    if(buttonNumber % 5 == 0){
                        button.setStyle("-fx-background-color: yellow;");
                    }
                }
                else if (buttonNumber == playerOneScore) {
                    button.setStyle("-fx-background-color: red;");
                }
            }
        }
    }
}