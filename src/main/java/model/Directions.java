package model;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Stores the possible move directions/sides of walls, and relating methods.
 */
public class Directions {
    /**
     * Stores the directions, in which moving the balls is possible, and the possible solid sides for {@link Cell}s.
     */
    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    public static HashSet<Direction> directions(){
        return new HashSet<>(Arrays.asList(Directions.Direction.UP, Directions.Direction.DOWN,
                Directions.Direction.LEFT, Directions.Direction.RIGHT));
    }

    /**
     * @return the direction opposite to the direction given.
     */
    public static Direction oppositeOf(Direction direction){
        return switch (direction){
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
        };
    }

    /**
     * The number of steps needed to make on the x-axis(vertical) for one step in the given direction.
     * @param direction the {@link Direction Direction} in which the x-value is asked
     * @return -1 for up, 1 for down, 0 otherwise
     */
    public static int xValue(Direction direction){
        return switch (direction){
            case LEFT, RIGHT -> 0;
            case UP -> -1;
            case DOWN -> 1;
        };
    }

    /**
     * The number of steps needed to make on the y-axis(horizontal) for one step in the given direction.
     * @param direction the {@link Direction Direction} in which the y-value is asked
     * @return -1 for left, 1 for right, 0 otherwise
     */
    public static int yValue(Direction direction){
        return switch (direction){
            case LEFT -> -1;
            case RIGHT -> 1;
            case UP, DOWN -> 0;
        };
    }
}