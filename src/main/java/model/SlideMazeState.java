package model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.*;

/**
 * Represent a state of the game.
 * Contains the {@link PlayingSurface}, the name of the player and the number of steps taken already.
 * Implements {@link puzzle.State}, with moves being represented by {@link model.Directions.Direction}.
 */
public class SlideMazeState implements puzzle.State<Directions.Direction>{
    private PlayingSurface playingSurface;
    private String playerName;
    private int nrOfSteps;
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates the state, its {@link PlayingSurface} being read from {@code playingSurface.json}.
     * Initializes the number of steps taken to zero and the player name to {@code no name given}.
     */
    public SlideMazeState() {
        initializeState();
    }

    public void resetToInitialState() {
        initializeState();
    }

    private void initializeState(){
        this.playingSurface = new PlayingSurface("playingSurface.json");
        this.playerName = "no name given";
        this.nrOfSteps = 0;
        LOGGER.info("SlidingMazeState instantiated from playingSurface.json. Ball position: {}, goal position: {}.",
                playingSurface.getBallPosition(), playingSurface.getGoalPosition());
    }

    /**
     * Gives back the current {@link Position} of the ball.
     * @return the {@link Position} of the ball
     */
    public Position getBallPosition(){
        return playingSurface.getBallPosition();
    }

    /**
     * Checks if the game is won.
     * @return true if the ball is in the goal cell, false otherwise
     */
    @Override
    public boolean isSolved() {
        boolean isSolved = playingSurface.getGoalPosition().equals(playingSurface.getBallPosition());
        if(isSolved){
            LOGGER.info("Game won.");
        }
        return isSolved;
    }

    /**
     * Checks in which {@link model.Directions.Direction Directions} does the ball NOT have a wall.
     * @return the set of legal moves
     */
    @Override
    public Set<Directions.Direction> getLegalMoves() {
        HashSet<Directions.Direction> directions = Directions.directions();
        directions.removeIf(direction -> !this.isLegalMove(direction));
        //System.out.println(directions);
        return directions;
    }

    /**
     * Gives back a new {@link SlideMazeState} object, with the same attributes as the
     * original one, but on a different memory position.
     * @return the clone of the object
     */
    @Override
    public SlideMazeState clone() {
        SlideMazeState copy;
        try {
            copy = (SlideMazeState) super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Could not clone SlideMazeState.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }
        copy.playingSurface = this.playingSurface.clone();
        return copy;
    }

    /**
     * Checks if the ball has a wall in the given direction. A move is illegal, if the ball has a wall
     * in the {@link model.Directions.Direction Direction}, in which the move wanted to be made.
     * @param direction the {@link model.Directions.Direction Directions} in which the check will be made
     * @return false if the given cell has a wall on its side,
     * which is toward the given {@link model.Directions.Direction Direction}, true otherwise
     */
    @Override
    public boolean isLegalMove(Directions.Direction direction) {
        return playingSurface.canMove(direction);
    }

    /**
     * Moves the ball in the given {@link model.Directions.Direction Direction}.
     * @param direction the {@link model.Directions.Direction Directions} in which the ball will be moved
     */
    @Override
    public void makeMove(Directions.Direction direction) {
        if(!this.isLegalMove(direction)){
            LOGGER.warn("Illegal move tried! Move: {}", direction);
            return;
        }
        nrOfSteps++;
        LOGGER.info("Ball moved {}. Number of steps taken: {}", direction, nrOfSteps);
        playingSurface.moveBall(direction);
        LOGGER.info("New ball position is {}.", playingSurface.getBallPosition());
    }

    /**
     * Gives back the height of the {@link PlayingSurface} stored in the {@link SlideMazeState game state}.
     * {@code (max x coordinate + 1)}
     * @return the number of rows in the {@link PlayingSurface}
     */
    public int getPlayingSurfaceHeight(){
        return this.playingSurface.getPlayingSurfaceHeight();
    }

    /**
     * Gives back the width of the {@link PlayingSurface} stored in the {@link SlideMazeState game state}.
     * @return the number of columns in a row of the {@link PlayingSurface}
     */
    public int getPlayingSurfaceWidth(){
        return this.playingSurface.getPlayingSurfaceWidth();
    }

    /**
     * Gives back the number of steps taken until the moment it's called.
     * @return the number of steps taken
     */
    public int getNrOfSteps() {
        return nrOfSteps;
    }

    /**
     * Gives back the name of the current player.
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the current player.
     * @param playerName the name of the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gives back the {@link Cell} at the given {@link Position} of the {@link PlayingSurface}
     * stored in the {@link SlideMazeState game state}.
     * @param position the {@link Position} of the requested {@link Cell}
     * @return the {@link Cell} at the given {@link Position}
     */
    public Cell cellAt(Position position){
        return playingSurface.at(position);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SlideMazeState other = (SlideMazeState) o;
        return playingSurface.equals(other.playingSurface);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playingSurface);
    }

    @Override
    public String toString() {
        return "SlideMazeState{" +
                "playingSurface=\n" + playingSurface +
                "}\n";
    }
}
