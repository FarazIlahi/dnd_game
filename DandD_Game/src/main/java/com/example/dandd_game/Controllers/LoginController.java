package com.example.dandd_game.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends BaseController {
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






}
