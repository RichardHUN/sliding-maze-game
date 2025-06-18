package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Directions;
import model.PlayableSlideMazeGame;
import model.Position;
import model.SlideMazeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles the happenings on the UI when the game is played.
 * If the game is won, redirects to the end UI.
 */
public class GameUIController implements PlayableSlideMazeGame {
    private final SlideMazeApplication application = new SlideMazeApplication();
    @FXML
    private GridPane grid = new GridPane();
    private static final Logger LOGGER = LogManager.getLogger(GameUIController.class);
    private Circle gameBall;

    /**
     * Does all necessary initializing moves.
     */
    @Override
    public void start() {

    }

    /**
     * Moves the ball on the UI.
     */
    @Override
    public void afterMove() {
        moveBallOnUI(state.getBallPosition());
    }

    /**
     * Redirects to the end scene.
     */
    @Override
    public void gameOver() {
        application.showEndScene((Stage)grid.getScene().getWindow(), state.getNrOfSteps());
    }

    /**
     * Gets called when the restart button is pressed.
     * Restarts the game, redirects to the start UI.
     * @param event the {@link ActionEvent} triggered by the button press
     */
    public void restartButtonPressed(ActionEvent event) {
        Stage stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        application.start(stage);
    }

    /**
     * Gets called when the up button is pressed. Moves the ball upwards.
     * After the move is done, check if the game is over. If yes, appends the
     * result to the leaderboard.
     */
    public void upButtonPressed(){
        LOGGER.info("Up button pressed.");
        makeMove(Directions.Direction.UP);
    }

    /**
     * Gets called when the down button is pressed. Moves the ball downward.ű
     * After the move is done, check if the game is over. If yes, appends the
     * result to the leaderboard.
     */
    public void downButtonPressed(){
        LOGGER.info("Down button pressed.");
        makeMove(Directions.Direction.DOWN);
    }

    /**
     * Gets called when the left button is pressed. Moves the ball to the left.
     * After the move is done, check if the game is over. If yes, appends the
     * result to the leaderboard.
     */
    public void leftButtonPressed(){
        LOGGER.info("Left button pressed.");
        makeMove(Directions.Direction.LEFT);
    }

    /**
     * Gets called when the right button is pressed. Moves the ball to the right.
     * After the move is done, check if the game is over. If yes, appends the
     * result to the leaderboard.
     */
    public void rightButtonPressed(){
        LOGGER.info("Right button pressed.");
        makeMove(Directions.Direction.RIGHT);
    }

    /**
     * Adds a child to the {@link GridPane}.
     * @param node the children that will be added
     * @param x row index
     * @param y column index
     */
    public void addChildToGrid(Node node, int x, int y){
        this.grid.add(node, y, x);
    }

    /**
     * Gives back the current state of the game.
     * @return the current state
     */
    public SlideMazeState getState(){
        return state;
    }

    private void moveBallOnUI(Position newPosition){
        if (gameBall == null) {
            throw new RuntimeException("No ball defined.");
        }
        StackPane old = (StackPane)gameBall.getParent();
        old.getChildren().remove(gameBall);
        StackPane newPane = (StackPane)grid.getChildren().stream()
                .filter(c -> GridPane.getColumnIndex(c).equals(newPosition.y())
                        && GridPane.getRowIndex(c).equals(newPosition.x()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No ball defined."));
        newPane.getChildren().add(gameBall);
    }

    /**
     * Sets the reference to the ball on the UI.
     * @param gameBall the reference that will be passed
     */
    public void setGameBall(Circle gameBall) {
        this.gameBall = gameBall;
    }

}
