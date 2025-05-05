package com.example.dandd_game.Controllers;

import com.example.dandd_game.FirebaseAuthHelper;
import com.example.dandd_game.FirebaseConfig;
import com.example.dandd_game.GameStateManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
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
    public void loginWithFacebook(ActionEvent event) throws IOException {
        try {
            System.out.println("Starting Facebook login...");

            // Step 1: Open Facebook Login WebView and get access token
            FacebookLoginWebView webView = new FacebookLoginWebView();
            String facebookAccessToken = webView.startFacebookLogin((Stage) root.getScene().getWindow());

            if (facebookAccessToken == null) {
                loginErrorLabel.setText("Facebook login failed. Try again.");
                return;
            }

            System.out.println("Facebook Access Token: " + facebookAccessToken);

            // Step 2: Exchange Facebook access token for Firebase ID token
            String firebaseIdToken = FirebaseAuthHelper.exchangeFacebookAccessTokenForFirebaseToken(facebookAccessToken);

            if (firebaseIdToken == null) {
                loginErrorLabel.setText("Failed to exchange token with Firebase.");
                return;
            }

            System.out.println("Firebase ID Token: " + firebaseIdToken);

            // Step 3: Verify Firebase ID Token
            FirebaseToken decodedToken = FirebaseConfig.verifyIdToken(firebaseIdToken);

            System.out.println("Firebase login successful!");
            System.out.println("UID: " + decodedToken.getUid());
            System.out.println("Email: " + decodedToken.getEmail());

            // Step 4: Switch scene
            switchScene(event, "GameLoads");

        } catch (Exception e) {
            e.printStackTrace();
            loginErrorLabel.setText("Something went wrong! Login failed.");
        }
        }
}
