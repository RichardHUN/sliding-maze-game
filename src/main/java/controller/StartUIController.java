package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles the happenings on the start UI.
 * The start UI gets called after the game is won, and the restart button is pressed.
 * Contains a text field asking for a name, and a start button. The start button in inaccessible
 * if the text field is empty.
 * The name of the player gets passed into the {@link model.SlideMazeState SlideMazeState}.
 * */
public class StartUIController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private TextField textField;

    /**
     * Initializes the start button to be inaccessible if the text field is empty.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.disableProperty().bind(textField.textProperty().isEmpty());
    }

    private static final Logger LOGGER = LogManager.getLogger(StartUIController.class);
    private final SlideMazeApplication application = new SlideMazeApplication();

    /**
     * Gets called when the start button is pressed.
     * Initializes the game, and shows the game scene.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void startButtonPressed(ActionEvent event) {
        LOGGER.info("Start button pressed.");
        String text = textField.getText();
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        application.showGameScene(stage, text);
    }

    /**
     * Redirects to the start scene.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void leaderBoardButtonClicked(ActionEvent event) {
        application.showLeaderBoard( (Stage)(((Node)event.getSource()).getScene().getWindow()) );
    }
}
