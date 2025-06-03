package model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import puzzle.State;
import puzzle.solver.BreadthFirstSearch;

import java.util.*;

public class SlideMazeState implements puzzle.State<Directions.Direction>{
    private PlayingSurface playingSurface;
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates the state, its' playingSurface being read from {@code walls.json}.
     */
    public SlideMazeState() {
        this.playingSurface = new PlayingSurface("walls.json");
        LOGGER.info("SlidingMazeState instantiated from walls.json. Ball position: {}, goal position: {}.",
                playingSurface.getBallPosition(), playingSurface.getGoalPosition());
    }

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

    @Override
    public SlideMazeState clone() {
        SlideMazeState copy;
        try {
            copy = (SlideMazeState) super.clone();
        } catch (CloneNotSupportedException e) {
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
            throw new IllegalArgumentException("Cannot move ball in the given direction" + direction);
        }
        LOGGER.info("Ball moved {}.", direction);
        playingSurface.moveBall(direction);
        LOGGER.info("New ball position is {}.", playingSurface.getBallPosition());
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
