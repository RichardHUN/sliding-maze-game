package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Directions;
import model.Position;
import model.SlideMazeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Extends the {@link javafx.application.Application}.
 * Implements the Slide Maze game UI.
 * Implements the scenes.
 */
public class SlideMazeApplication extends javafx.application.Application {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Starts the game with UI.
     * @param stage the stage that will be shown
     */
    @Override
    public void start(Stage stage){
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/start.fxml"));
            root = loader.load();
        } catch (IOException e) {
            LOGGER.error("Could not open start.fxml.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        stage.setTitle("Sliding Maze Game");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Redirects to the game scene, start the game.
     * @param stage the stage on which the game should be shown
     * @param playerName the name of the player currently playing
     */
    public void showGameScene(Stage stage, String playerName){
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            root = loader.load();
        } catch (IOException e) {
            LOGGER.error("Could not open game.fxml.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);

        GameUIController uiController = loader.getController();
        uiController.init();
        SlideMazeState state = uiController.getState();
        state.setPlayerName(playerName);

        StackPane rect;
        char baseWidth = '1';
        char solidWidth = '3';
        for (int i = 0; i < state.getPlayingSurfaceHeight(); i++) {
            for (int j = 0; j < state.getPlayingSurfaceWidth(); j++) {
                rect = new StackPane();
                String borderWidths =
                        (state.cellAt(Position.of(i, j)).canMove(Directions.Direction.UP) ? baseWidth : solidWidth) +
                                " " +
                                (state.cellAt(Position.of(i, j)).canMove(Directions.Direction.RIGHT) ? baseWidth : solidWidth) +
                                " " +
                                (state.cellAt(Position.of(i, j)).canMove(Directions.Direction.DOWN) ? baseWidth : solidWidth) +
                                " " +
                                (state.cellAt(Position.of(i, j)).canMove(Directions.Direction.LEFT) ? baseWidth : solidWidth)
                        ;
                rect.setStyle(
                        "-fx-border-color: black;" +
                                "-fx-border-width:" + borderWidths + ";" +
                                "-fx-width: 39;" +
                                "-fx-height: 39;" +
                                "-fx-background-color: white;"
                );
                if (uiController.getState().cellAt(Position.of(i,j)).hasBall()){
                    Circle ball = new Circle();
                    ball.setRadius(15);
                    ball.setStyle("-fx-fill: DODGERBLUE");
                    uiController.setGameBall(ball);
                    rect.getChildren().add(ball);
                }
                if (uiController.getState().cellAt(Position.of(i,j)).isGoal()){
                    Label label = new Label();
                    label.setAlignment(Pos.CENTER);
                    label.setText("Goal");
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setFont(new Font("System Bold", 12.0));
                    rect.getChildren().add(label);
                }
                uiController.addChildToGrid(rect, i, j);
            }
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Redirects to end scene.
     * @param stage the stage on which the game should be shown
     * @param nrOfSteps the number of steps taken by current player.
     */
    public void showEndScene(Stage stage, int nrOfSteps){
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/end.fxml"));
            root = loader.load();
        } catch (IOException e) {
            LOGGER.error("Could not open end.fxml.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        EndUIController uiController = loader.getController();
        uiController.setText(nrOfSteps);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Redirects to the leaderboard.
     * @param stage the stage on which the leaderboard will be shown
     */
    public void showLeaderBoard(Stage stage){
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/leaderboard.fxml"));
            root = loader.load();
        } catch (IOException e) {
            LOGGER.error("Could not open leaderboard.fxml.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
