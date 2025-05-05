package com.example.dandd_game.Controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewUserRegisterController extends BaseController {
    @FXML
    private TextField elEmailTextField;
    @FXML
    private PasswordField elPWPasswordField;
    @FXML
    private PasswordField elPWPasswordFieldTwo;
    @FXML
    private Button CSbutton;

    @FXML
    public void createUser(ActionEvent event) throws IOException {
        String email = elEmailTextField.getText();
        String password = elPWPasswordField.getText();
        String password2 = elPWPasswordFieldTwo.getText();

        if(!password.equals(password2)){
            System.out.println("Passwords do not match");
        }
        try{
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setEmailVerified(false)
                    .setPassword(password)
                    .setDisabled(false);
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Firebase user created: " + userRecord.getUid());

            Firestore db = FirestoreClient.getFirestore();
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", email);
            userData.put("username", email.split("@")[0]);

            db.collection("users").document(userRecord.getUid()).set(userData);
            System.out.println("User info saved to database");
            switchScene(event, "login");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Firebase user creation failed");
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        switchScene(event,"Login");
    }
}


