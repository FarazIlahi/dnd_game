module com.example.dandd_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires firebase.admin;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.media;

    // Firebase serialization
    opens com.example.dandd_game to javafx.fxml, google.cloud.firestore;
    opens com.example.dandd_game.Characters to google.cloud.firestore;

    // FXML
    opens com.example.dandd_game.Controllers to javafx.fxml;
    opens com.example.dandd_game.Chapter1 to javafx.fxml;
    opens com.example.dandd_game.Chapter2 to javafx.fxml;
    opens com.example.dandd_game.Chapter3 to javafx.fxml;

    // Exports
    exports com.example.dandd_game;
    exports com.example.dandd_game.Controllers;
    exports com.example.dandd_game.Chapter1;
    exports com.example.dandd_game.Chapter2;
    exports com.example.dandd_game.Chapter3;
}