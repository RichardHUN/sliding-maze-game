package model.reading;

import model.PlayingSurface;
import model.Position;
import model.Wall;

import java.util.List;

/**
 * Represents the data read from file. Is used to create the {@link PlayingSurface}.
 */
public record PlayingSurfaceData(Position startPosition, Position goalPosition, List<Wall> walls) {
    /**
     * Creates a new {@link PlayingSurfaceData} with the given parameters.
     * Sets the length attribute to the length of {@code walls} + 2.
     *
     * @param startPosition the start {@link Position} of the ball
     * @param goalPosition  the goal {@link Position}
     * @param walls         all walls needed to be placed
     */
    public PlayingSurfaceData {
    }

    /**
     * Gives back the {@link Position} of the goal.
     *
     * @return the {@link Position} of the goal
     */
    @Override
    public Position goalPosition() {
        return goalPosition;
    }

    /**
     * Gives back the start {@link Position}.
     *
     * @return the start {@link Position}.
     */
    @Override
    public Position startPosition() {
        return startPosition;
    }

    /**
     * Gives back the list of {@link Wall walls} to be placed.
     *
     * @return list of {@link Wall walls}
     */
    @Override
    public List<Wall> walls() {
        return walls;
    }

}