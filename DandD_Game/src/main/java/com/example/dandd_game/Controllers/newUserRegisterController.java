package com.example.dandd_game.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class newUserRegisterController extends BaseController {
    @FXML
    private TextField elUNTextField;
    @FXML
    private PasswordField elPWPasswordField;
    @FXML
    private PasswordField elPWPasswordFieldtwo;
    @FXML
    private Button CSbutton;

    public void CreateUser(ActionEvent event) throws IOException {
        // switchScene(event, "");
    }


}
