package com.example.dandd_game;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application implements GameMechanics {
    public static Firestore fStore;
    public static FirebaseAuth fAuth;
    public static FirebaseConfig contextFirebase = new FirebaseConfig();
    @Override
    public void start(Stage stage) throws IOException {
        fStore = contextFirebase.initialize();
        fAuth = FirebaseAuth.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("TitleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        FirebaseConfig.initialize();
        launch();

    }
}