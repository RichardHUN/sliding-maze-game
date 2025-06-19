package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import leaderboard.LeaderBoardEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderBoardController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(LeaderBoardController.class);
    SlideMazeApplication application = new SlideMazeApplication();
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox vBox;

    /**
     * Fills the leaderboard with the entries read from file.
     * @param url The location used to resolve relative paths for the root object,
     *            or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object,
     *                       or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObjectMapper mapper = new ObjectMapper();
        List<LeaderBoardEntry> leaderboard;
        try {
            leaderboard = mapper.readValue(
                    new File("leaderboard/leaderboard.json"),
                    new TypeReference<>() {});
        } catch (IOException e) {
            LOGGER.error("Could not open leaderboard/leaderboard.json.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            LOGGER.error("Error occurred when trying to open leaderboard/leaderboard.json.{}{}",
                    System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        leaderboard.stream()
                .sorted(Comparator.comparingInt(LeaderBoardEntry::getNrOfSteps))
                .forEach(entry ->
                    vBox.getChildren().add( new Text(
                            entry.getPlayerName() + ": " + entry.getNrOfSteps()
                        )
                    )
                );
    }

    /**
     * Gets called when the {@code Continue} button is clicked.
     * Redirects to the start scene.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void continueButtonClicked(ActionEvent event) {
        application.start( (Stage)(((Node)event.getSource()).getScene().getWindow()) );
    }

}
