package hr.algebra.azulproject.utils;

import javafx.scene.control.Alert;

public class DialogUtils {

    private static void showInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void printLoadSuccessfull() {
        showInformationDialog("Load successfull!", "You have successfully loaded your game!");
    }

    public static void printSaveSuccessfull() {
        showInformationDialog("Saved successfull!", "You have successfully saved your game!");
    }

    public static void savedDocumentation() {
        showInformationDialog("Saved successfull!", "You have successfully saved documentation!");
    }

    public static void printWinnerMessage(String player){
        showInformationDialog("Winner!!!!", "WINNER, " + player + " WON. Nice job!!!!!!!!");
    }
    public static void printDrawMessage(){
        showInformationDialog("Draw!!!!", "Draw game, not winner");
    }
}
