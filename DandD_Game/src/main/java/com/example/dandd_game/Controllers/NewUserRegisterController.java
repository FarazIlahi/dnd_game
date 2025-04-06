package com.example.dandd_game.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewUserRegisterController extends BaseController {
    @FXML
    private TextField elUNTextField;
    @FXML
    private PasswordField elPWPasswordField;
    @FXML
    private PasswordField elPWPasswordFieldtwo;
    @FXML
    private Button CSbutton;
    @FXML
    private Button back;

    @FXML
    public void createUser(ActionEvent event) throws IOException {
        switchScene(event, "login");

    }


}
