module com.example.dandd_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;


    opens com.example.dandd_game to javafx.fxml;
    exports com.example.dandd_game;
    exports com.example.dandd_game.Controllers;
    opens com.example.dandd_game.Controllers to javafx.fxml;
}