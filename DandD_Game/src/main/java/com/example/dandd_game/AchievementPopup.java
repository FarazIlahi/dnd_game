package com.example.dandd_game;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;

import javafx.util.Duration;


public class AchievementPopup {
    public static void show(Pane rootPane, String achievementText) {
        HBox popupBox = new HBox(10);
        popupBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); -fx-padding: 16; -fx-background-radius: 10;");
        popupBox.setLayoutY(40);
        popupBox.setOpacity(0);


        popupBox.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double popupWidth = newBounds.getWidth();
            popupBox.setLayoutX(rootPane.getWidth() - popupWidth - 40); // 40px from right edge
        });

        // trophy image
        ImageView trophyImage = new ImageView(new Image(AchievementPopup.class.getResource("/com/example/dandd_game/images/trophy.png").toExternalForm()));
        trophyImage.setFitWidth(32);
        trophyImage.setFitHeight(32);

        Label label = new Label(achievementText);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        popupBox.getChildren().addAll(trophyImage, label);
        popupBox.setOpacity(0);
        rootPane.getChildren().add(popupBox);
        popupBox.toFront();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), popupBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), popupBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> rootPane.getChildren().remove(popupBox));

        new SequentialTransition(fadeIn, stay, fadeOut).play();


    }

}
