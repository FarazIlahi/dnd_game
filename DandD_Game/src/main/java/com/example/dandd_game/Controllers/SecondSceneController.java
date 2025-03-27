package com.example.dandd_game.Controllers;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class SecondSceneController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button prepareSiegeButton;

    @FXML
    private Button investigateButton;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void goToSiege(ActionEvent event) throws IOException {
        switchScene(event, "SiegeScene"); // defend siege
    }


    @FXML
    private void goToInvestigate(ActionEvent event) throws IOException {
        switchScene(event, "InvestigateScene"); // investigate
    }
}
