module com.example.dandd_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    opens com.example.dandd_game to javafx.fxml;
    exports com.example.dandd_game;
    exports com.example.dandd_game.Controllers;
    opens com.example.dandd_game.Controllers to javafx.fxml;
    opens com.example.dandd_game.Chapter1 to javafx.fxml;
    exports com.example.dandd_game.Chapter1;
    exports com.example.dandd_game.Chapter2;
    opens com.example.dandd_game.Chapter2 to javafx.fxml;
    opens com.example.dandd_game.Chapter3 to javafx.fxml;
    exports com.example.dandd_game.Chapter3;
}