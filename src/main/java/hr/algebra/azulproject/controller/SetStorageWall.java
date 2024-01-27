package hr.algebra.azulproject.controller;

import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class SetStorageWall {
    public static void setImage(Pane pane, String imagePath, double opacity) {
        try {
            Image image = new Image(SetStorageWall.class.getResourceAsStream(imagePath));
            if (image.isError()) {
                System.out.println("Error loading image: " + imagePath);
                System.out.println("Image URL: " + SetStorageWall.class.getResource(imagePath));
            }
            BackgroundImage backgroundImage = new BackgroundImage(
                    image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
            );
            Background background = new Background(backgroundImage);
            pane.setBackground(background);
            pane.setOpacity(opacity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
