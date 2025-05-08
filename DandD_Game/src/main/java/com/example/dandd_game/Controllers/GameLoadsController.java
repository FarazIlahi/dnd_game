package com.example.dandd_game.Controllers;

import com.example.dandd_game.*;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.application.Platform;
import com.google.api.core.ApiFuture;

public class GameLoadsController extends BaseController implements GameMechanics {

    @FXML
    Button nvmButton;
    @FXML
    Button load1;
    @FXML
    Button load2;
    @FXML
    Button load3;
    @FXML
    Button load4;
    @FXML
    Pane rootPane;
    @FXML private Label load1Label;
    @FXML private Label load2Label;
    @FXML private Label load3Label;
    @FXML private Label load4Label;

    private boolean creatingFile;
    private int selectedSlot = -1;
    @FXML
    private void initialize() {
        super.init(rootPane);
        gameState.resetInstance();
        creatingFile = false;
        super.stopMusic();
        super.setMusic("DandD_Game/src/main/resources/com/example/dandd_game/sounds/chapter1.wav");
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
        checkSaveFileLabels();
    }
    private void checkSaveFileLabels() {
        Firestore db = FirestoreClient.getFirestore();
        String email = GameStateManager.getInstance().getCurrentUserEmail();

        for (int i = 1; i <= 4; i++) {
            final int slot = i;
            ApiFuture<DocumentSnapshot> future = db.collection("saves")
                    .document(email)
                    .collection("slots")
                    .document("slot" + slot)
                    .get();
            try {
                DocumentSnapshot document = future.get(); // blocking call
                if (document.exists()) {
                    Platform.runLater(() -> setSaveLabel(slot, "Save File has Data"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setSaveLabel(int slot, String text) {
        switch (slot) {
            case 1: load1Label.setText(text); break;
            case 2: load2Label.setText(text); break;
            case 3: load3Label.setText(text); break;
            case 4: load4Label.setText(text); break;
        }
    }
    @FXML
    public void createNewFile(ActionEvent event) throws IOException{
        rootPane.setEffect(new Glow(.25));
        creatingFile = true;
        nvmButton.setDisable(false);
        nvmButton.setVisible(true);
    }
    @FXML
    public void dontCreateNewFile(ActionEvent event){
        rootPane.setEffect(null);
        creatingFile = false;
        nvmButton.setDisable(true);
        nvmButton.setVisible(false);
    }
    @FXML
    public void hovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        highlight(clickedButton);
    }
    @FXML
    public void unHovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        unhighlight(clickedButton);
    }
    @FXML
    private void openAchievements(ActionEvent event) throws IOException {
        switchScene("AchievementsScene");
    }

    private void goToPlayerCount() {
        try {
            switchScene("playerCount");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onLoadClicked(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        selectedSlot = Integer.parseInt(clickedButton.getText());
        if (creatingFile) {
            Firestore db = FirestoreClient.getFirestore();
            String email = GameStateManager.getInstance().getCurrentUserEmail();
            GameSaves.setSelectedSlot(selectedSlot);
            try {
                DocumentSnapshot result = db.collection("saves")
                        .document(email)
                        .collection("slots")
                        .document("slot" + selectedSlot)
                        .get().get();
                if (result.exists()) {
                    boolean confirmed = ConfirmPopup.show("Overwrite Save Slot " + selectedSlot + "?");
                    if (confirmed) {
                        goToPlayerCount();
                    }
                } else {
                    goToPlayerCount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                GameSaves.loadGame(selectedSlot);
                String scene = GameStateManager.getInstance().getCurrentScene();
                switchScene(event, scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void tutorial(ActionEvent event) throws IOException {
        gameState.resetInstance();
        gameState.setPlayerCount(4);
        gameState.createKing();
        gameState.createKnight();
        gameState.createCleric();
        gameState.createMage();
        gameState.addToParty(gameState.getKing());
        gameState.addToParty(gameState.getKnight());
        gameState.addToParty(gameState.getCleric());
        gameState.addToParty(gameState.getMage());

        //gameState.createGoblin();
        //gameState.createOrc();
        gameState.createSorcerer();
        //gameState.addToEnemys(gameState.getGoblin());
        //gameState.addToEnemys(gameState.getOrc());
        gameState.addToEnemys(gameState.getSorcerer());


        switchScene(event,"Combat");
    }
}