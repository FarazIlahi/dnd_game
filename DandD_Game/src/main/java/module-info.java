module com.example.dandd_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dandd_game to javafx.fxml;
    exports com.example.dandd_game;
}