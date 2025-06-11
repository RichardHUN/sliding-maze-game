package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controls the end UI.
 * Contains a text, which gets initialized to {@code You won! Number of steps: (number of steps taken)},
 * and a restart button. The restart button redirects to the start scene.
 */
public class EndUIController{
    private final SlideMazeApplication application = new SlideMazeApplication();
    @FXML
    private Text text;

    /**
     * Gets called when the restart button is pressed.
     * Redirects to the start scene.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void restartButtonPressed(ActionEvent event) {
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        application.start(stage);
    }

    /**
     * Gets called when the {@code Show leaderboard} button is pressed.
     * Redirects to the leaderboard scene.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void leaderBoardButtonPressed(ActionEvent event) {
        application.showLeaderBoard((Stage)(((Node)event.getSource()).getScene().getWindow()));
    }

    /**
     * Sets the text on the end scene to {@code You won! Number of steps: (number of steps taken)}.
     * @param nrOfSteps the number of steps taken
     */
    public void setText(int nrOfSteps) {
        text.setText("You won! Number of steps: " + nrOfSteps);
    }
}
