package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Represent a cell on the {@link PlayingSurface}. Each cell has 6 boolean attributes: one indicating if
 * the given cell contains the ball, one indicating if the given cell is a goal cell,
 * and one for each side(4), indicating if it is possible ot move the ball from the given cell towards
 * the given side.
 */
public class Cell implements Cloneable{
    private static final Logger LOGGER = LogManager.getLogger(Cell.class);
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean canMoveUp;
    private boolean canMoveDown;
    private boolean hasBall;
    private boolean isGoal;

    /**
     * Creates a {@link Cell} with the given attributes.
     * @param canMoveLeft specifies if the ball can move left from the {@code Cell}
     * @param canMoveRight specifies if the ball can move right from the {@code Cell}
     * @param canMoveUp specifies if the ball can move up from the {@code Cell}
     * @param canMoveDown specifies if the ball can move down from the {@code Cell}
     * @param hasBall specifies if the {@code Cell} has the ball
     * @param isGoal specifies if the given {@code Cell} is a goal {@code Cell}
     */
    public Cell(boolean canMoveLeft, boolean canMoveRight, boolean canMoveUp, boolean canMoveDown, boolean hasBall, boolean isGoal) {
        this.canMoveLeft = canMoveLeft;
        this.canMoveRight = canMoveRight;
        this.canMoveUp = canMoveUp;
        this.canMoveDown = canMoveDown;
        this.hasBall = hasBall;
        this.isGoal = isGoal;
    }

    @Override
    public Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Could not clone Cell.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the {@code Cell} has the ball.
     * @return true if the given cell has the ball, false otherwise
     */
    public boolean hasBall() {
        return hasBall;
    }

    /**
     * Checks if the {@code Cell} is a goal {@code Cell}.
     * @return true if the given {@code Cell} is a goal {@code Cell}, false otherwise
     */
    public boolean isGoal() {
        return isGoal;
    }

    /**
     * Sets the hasBall attribute of the cell.
     * @param hasBall the value to which the hasBall attribute will be set.
     */
    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    /**
     * Checks if the given cell has a wall in the given {@link model.Directions.Direction Direction}.
     * @param direction the direction in which the check is done
     * @return true, if the ball can be moved towards the given direction, false otherwise
     */
    public boolean canMove(Directions.Direction direction){
        return switch (direction){
            case LEFT -> canMoveLeft();
            case RIGHT -> canMoveRight();
            case UP -> canMoveUp();
            case DOWN -> canMoveDown();
        };
    }

    /**
     * Can be used to change if the {@link Cell} is a goal cell or not.
     * @param val {@code true} to make the {@link Cell} a goal cell
     */
    public void setGoal(boolean val){
        this.isGoal = val;
    }

    private boolean canMoveLeft() {
        return canMoveLeft;
    }

    private boolean canMoveRight() {
        return canMoveRight;
    }

    private boolean canMoveUp() {
        return canMoveUp;
    }

    private boolean canMoveDown() {
        return canMoveDown;
    }

    /**
     * Solidifies/unsolidifies the wall of the cell in the given {@link model.Directions.Direction Direction}.
     * The ball can pass through soft walls, but it can't pass through solid walls.
     * @param direction the side of the cell which will be modified
     * @param canMove true for solid walls, false for soft walls
     */
    public void setCanMove(Directions.Direction direction, boolean canMove){
        switch (direction){
            case LEFT -> setCanMoveLeft(canMove);
            case RIGHT -> setCanMoveRight(canMove);
            case UP -> setCanMoveUp(canMove);
            case DOWN -> setCanMoveDown(canMove);
        }
    }

    private void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    private void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    private void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    private void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return canMoveLeft == cell.canMoveLeft && canMoveRight == cell.canMoveRight && canMoveUp == cell.canMoveUp && canMoveDown == cell.canMoveDown && hasBall == cell.hasBall && isGoal == cell.isGoal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(canMoveLeft, canMoveRight, canMoveUp, canMoveDown, hasBall, isGoal);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "canMoveLeft=" + canMoveLeft +
                ", canMoveRight=" + canMoveRight +
                ", canMoveUp=" + canMoveUp +
                ", canMoveDown=" + canMoveDown +
                ", hasBall=" + hasBall +
                '}';
    }
}
