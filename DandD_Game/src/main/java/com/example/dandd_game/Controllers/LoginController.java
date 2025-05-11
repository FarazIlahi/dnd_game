package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Properties;
import java.util.List;
import java.io.InputStream;

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

    private static final String API_KEY = loadApiKey();
    @FXML
    private void initialize() {
        super.init(root);
    }

    private static String loadApiKey() {
        try (InputStream input = LoginController.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty("firebase.api.key");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void loginErrorLabelOnAction(ActionEvent event) throws IOException {
        String email = userNField.getText();
        String password = passWField.getText();

        if (email.isBlank() || password.isBlank()) {
            playSoundFX("/com/example/dandd_game/soundFX/error.mp3", 1);
            loginErrorLabel.setText("Please enter an email and password.");
            return;
        }
        if (authenticateUser(email, password)) {
            loginErrorLabel.setText("Login successful!");
            gameState.setCurrentUserEmail(email);

            // load the achievements before switching to gameloads
            try {
                DocumentSnapshot document = FirestoreClient.getFirestore().collection("saves").document(email).get().get();
                if (document.exists()) {
                    Object rawAchievements = document.get("achievements");
                    if (rawAchievements instanceof List<?>) {
                        List<?> rawList = (List<?>) rawAchievements;
                        List<String> achievements = new ArrayList<>();
                        for (Object item : rawList) {
                            if (item instanceof String) {
                                achievements.add((String) item);
                            }
                        }
                        GameStateManager.getInstance().setAchievements(achievements);
                        System.out.println("Loadede: " + achievements);
                    }
                }
                playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
                switchScene("GameLoads");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean authenticateUser(String email, String password) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format(
                    "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}", email, password
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8");
            String jsonResponse = scanner.useDelimiter("\\A").next();
            scanner.close();

            JsonObject response = JsonParser.parseString(jsonResponse).getAsJsonObject();
            String idToken = response.get("idToken").getAsString();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void goToUserRegistration(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        switchScene("newUserRegister");
    }
    @FXML
    public void leaveButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) leaveButton.getScene().getWindow();
        stage.close();
    }
}
