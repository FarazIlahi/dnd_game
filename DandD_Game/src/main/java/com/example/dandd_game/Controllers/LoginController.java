package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController extends BaseController {
    @FXML
    private Pane root;
    @FXML
    private Button loginButton;
    @FXML
    private Button newUserButton;
    @FXML
    private Button leaveButton;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField userNField;
    @FXML
    private PasswordField passWField;

    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    public void loginErrorLabelOnAction(ActionEvent event) throws IOException {
        switchScene(event, "GameLoads");
        if (userNField.getText().isBlank() == false && passWField.getText().isBlank() == false) {
            loginErrorLabel.setText("You tried to login");

        }
        else {
            loginErrorLabel.setText("Please enter your username and password");
        }

    }

    @FXML
    public void goToUserRegistration(ActionEvent event) throws IOException {
        switchScene(event, "newUserRegister");
    }
    @FXML
    public void leaveButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) leaveButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void tutorial(ActionEvent event) throws IOException {gameState.setCurrentCharacter(gameState.getKing());
        gameState.createKing();
        gameState.createKnight();
        gameState.createCleric();
        gameState.createMage();
        gameState.addToParty(gameState.getKing());
        gameState.addToParty(gameState.getKnight());
        gameState.addToParty(gameState.getCleric());
        gameState.addToParty(gameState.getMage());
        gameState.setCurrentCharacter(gameState.getKing());

        //gameState.createGoblin();
        //gameState.createOrc();
        gameState.createSorcerer();
        //gameState.addToEnemys(gameState.getGoblin());
        //gameState.addToEnemys(gameState.getOrc());
        gameState.addToEnemys(gameState.getSorcerer());


        switchScene(event,"Combat");
    }



}
