package hr.algebra.azulproject.utils;

import javafx.scene.Node;

public class ClearPlaygroundResidues {
    public static void clearPlayground(String color, Node... nodes){
        String style = "-fx-background-color: " + color + ";";
        for(Node node : nodes){
            node.setStyle(style);
        }
    }
}
