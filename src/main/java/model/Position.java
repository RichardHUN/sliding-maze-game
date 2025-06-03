package model;

/**
 * Represents a position with x and y values.
 * @param x the row index
 * @param y the column index
 */
public record Position(int x, int y) {
    /**
     * Gives back a new {@link Position} with the same coordinates as the given one.
     * @param position the given {@link Position}, which will be copied
     * @return the new {@link Position}
     */
    public static Position of(Position position){
        return new Position(position.x(), position.y());
    }

    /**
     * Gives back a {@link Position} with the given x and y coordinates.
     * @param x the x coordinate of the new object
     * @param y the y coordinate of the new object
     * @return the new {@link Position}
     */
    public static Position of(int x, int y){
        return new Position(x, y);
    }
}
