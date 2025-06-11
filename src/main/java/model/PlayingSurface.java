package model;
import model.reading.PlayingSurfaceReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the playing surface/playing field of the {@link SlideMazeState}, the {@link Position} of the ball
 * and the {@link Position} of the goal.
 * The playingSurface is a matrix, it stores {@link Cell}s with (x,y) coordinates, (0,0) being the top left cell.
 */
public class PlayingSurface implements Cloneable{
    private static final Logger LOGGER = LogManager.getLogger(PlayingSurface.class);
    private List<List<Cell>> playingSurface;
    private Position ballPosition;
    private Position goalPosition;

    /**
     * Creates an empty, 7x7 {@link PlayingSurface}, with the ball on the (1,4) position,
     * and the goal on (5,2).
     */
    public PlayingSurface() {
        List<List<Cell>> table = new ArrayList<>(7);
        List<Cell> row;

        for (int i = 0; i < 7; i++) {
            row = new ArrayList<>(7);
            for (int j = 0; j < 7; j++) {
                row.add(new Cell(true, true, true, true,
                        (i == 1 && j == 4), (i == 5 && j == 2)));
            }
            table.add(row);
        }
        this.playingSurface = table;
        this.ballPosition = new Position(1,4);
        this.goalPosition = new Position(5,2);
        this.placeBorderWalls();
    }

    /**
     * Reads the {@link Wall Walls} and the Start- and Goal positions
     * from the given path, and creates a {@link PlayingSurface} from it.
     * @param path the path from which the JSon file is read (inside .resources).
     */
    public PlayingSurface(String path){
        PlayingSurface p = PlayingSurfaceReader.readPlayingSurface(path);
        this.playingSurface = p.playingSurface;
        this.ballPosition = p.ballPosition;
        this.goalPosition = p.goalPosition;
    }

    /**
     * Gives back the {@link Cell} at the given {@link Position}
     * @param position the coordinates of the wanted cell {@code (x,y)<->(row,column)}
     * @return the {@link Cell} at the given coordinate of the playingSurface
     */
    public Cell at(Position position){
        return playingSurface.get(position.x()).get(position.y());
    }

    /**
     * Makes the {@link PlayingSurface PlayingSurfaces}' {@link Cell} at the given {@link Position}
     * be the {@code Goal Cell}.
     * @param position the {@link Position} of the {@link Cell} that will be the new goal
     */
    public void setGoal(Position position){
        this.at(goalPosition).setGoal(false);
        this.at(position).setGoal(true);
        this.goalPosition = position;
    }

    /**
     * Makes the {@link PlayingSurface PlayingSurfaces}' {@link Cell} at the given {@link Position}
     * be the {@code Start Cell}.
     * Also moves the ball to the given position.
     * @param position the {@link Position} of the {@link Cell} that will be the new start
     */
    public void setStart(Position position){
        this.at(ballPosition).setHasBall(false);
        this.at(position).setHasBall(true);
        this.ballPosition = position;
    }

    /**
     * Checks if the ball can move in the given {@link model.Directions.Direction Direction}.
     * @param direction the {@link model.Directions.Direction Direction} in which the check will happen
     * @return false, if there is a wall directly next to the ball
     * in the given direction, true otherwise.
     */
    public boolean canMove(Directions.Direction direction){
        return this.at(ballPosition).canMove(direction);
    }

    /**
     * Moves the ball in the given {@link model.Directions.Direction Direction} until it meets a wall.
     * @param direction the {@link model.Directions.Direction Direction} in which the ball will be moved
     */
    public void moveBall(Directions.Direction direction){
        Position moveTo = Position.of(ballPosition);
        while(this.at(moveTo).canMove(direction)){
            moveTo = Position.of(moveTo.x() + Directions.xValue(direction),
                    moveTo.y() + Directions.yValue(direction) );
        }
        this.at(ballPosition).setHasBall(false);
        ballPosition = moveTo;
        this.at(ballPosition).setHasBall(true);
    }

    private void placeBorderWalls(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(i == 0){
                    placeWall(new Position(i,j), Directions.Direction.UP);
                }
                if(j == 0){
                    placeWall(new Position(i,j), Directions.Direction.LEFT);
                }
                if(j == 6){
                    placeWall(new Position(i,j), Directions.Direction.RIGHT);
                }
                if(i == 6){
                    placeWall(new Position(i,j), Directions.Direction.DOWN);
                }
            }
        }
    }

    /**
     * Makes the two {@link Cell}s unavailable to each-other.
     * For example:
     * <pre>
     * {@code
     * Position position = new Position(1,1);
     * Cell.Direction side = Directions.Direction.RIGHT;
     * placeWall(position, side)}
     * </pre>
     * makes the (1,1) {@link Cell}s' canMoveRight attribute to false,
     * and the (1,2) {@link Cell}s' canMoveLeft attribute to false.
     * <pre>{@code placeWall(new Position(0,0), Directions.RIGHT)}</pre>
     * is equivalent to
     * <pre>{@code placeWall(new Position(0,1), Directions.LEFT)}</pre>
     * @param position the {@link Position} of the {@link Cell}
     * @param side the side of the {@link Cell} that will be solidified
     */
    public void placeWall(Position position, Directions.Direction side){
        this.at(position).setCanMove(side, false);
        Position otherPosition = Position.of(position.x() + Directions.xValue(side),
                position.y() + Directions.yValue(side));
        if(otherPosition.x() > 6 || otherPosition.x() < 0 ||
                otherPosition.y() > 6 || otherPosition.y() < 0){
            return;
        }
        this.at(otherPosition)
                .setCanMove(Directions.oppositeOf(side), false);
    }

    /**
     * Gives back the ball {@link Position}.
     * @return the {@link Position} of the ball
     */
    public Position getBallPosition() {
        return ballPosition;
    }

    /**
     * Gives back the {@link Position} of the goal.
     * @return the {@link Position} of the goal
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Gives back the height of the {@link PlayingSurface}.
     * {@code (max x coordinate + 1)}
     * @return the number of rows in the {@link PlayingSurface}
     */
    public int getPlayingSurfaceHeight(){
        return this.playingSurface.size();
    }

    /**
     * Gives back the width of the {@link PlayingSurface}.
     * @return the number of columns in a row of the {@link PlayingSurface}
     */
    public int getPlayingSurfaceWidth(){
        return this.playingSurface.getFirst().size();
    }

    @Override
    public PlayingSurface clone() {
        try {
            PlayingSurface copy = (PlayingSurface) super.clone();
            List<List<Cell>> surfaceCopy = new ArrayList<>();
            for (List<Cell> row : this.playingSurface) {
                List<Cell> rowCopy = new ArrayList<>();
                for (Cell cell : row) {
                    rowCopy.add(cell.clone());
                }
                surfaceCopy.add(rowCopy);
            }
            copy.playingSurface = surfaceCopy;
            copy.ballPosition = Position.of(ballPosition);
            return copy;
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Could not clone PlayingSurface.{}{}", System.lineSeparator(), e);
            throw new RuntimeException("Failed to clone PlayingSurface", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayingSurface that = (PlayingSurface) o;
        return Objects.equals(playingSurface, that.playingSurface) && Objects.equals(ballPosition, that.ballPosition) && Objects.equals(goalPosition, that.goalPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playingSurface, ballPosition, goalPosition);
    }

    @Override
    public String toString() {
        StringBuilder table = new StringBuilder();
        StringBuilder row = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            if (i == 0){
                row.append("#".repeat(15));
                table.append(row).append("\n");
            }
            row = new StringBuilder();
            for (int j = 0; j < 7; j++) {
                if (j == 0) {
                    row.append("#");
                }
                row.append(playingSurface.get(i).get(j).hasBall() ?
                        (playingSurface.get(i).get(j).isGoal() ? "0" : "O" ):
                        (playingSurface.get(i).get(j).isGoal() ? "X" : " " ));
                row.append(playingSurface.get(i).get(j).canMove(Directions.Direction.RIGHT) ? "|" : "#");
            }
            table.append(row).append("\n");

            if (i != 6) {//line between rows
                row = new StringBuilder();
                for (int m = 0; m < 15; m++) {
                    if (m % 2 == 0) {
                        row.append("-");
                        continue;
                    }
                    row.append(playingSurface.get(i).get((m - 1) / 2).canMove(Directions.Direction.DOWN) ? "-" : "#");
                }
                table.append(row).append("\n");
                continue;
            }
            table.append("#".repeat(15));
        }

        return table.toString();
    }
}
