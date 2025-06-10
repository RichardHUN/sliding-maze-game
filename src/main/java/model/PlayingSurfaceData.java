package model;

import java.util.List;

public class PlayingSurfaceData {
    private final Position goalPosition;
    private final Position startPosition;
    private final List<Wall> walls;
    private final int length;

    public PlayingSurfaceData(Position startPosition, Position goalPosition, List<Wall> walls) {
        this.goalPosition = goalPosition;
        this.startPosition = startPosition;
        this.walls = walls;
        this.length = walls.toArray().length + 2;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public int getLength() {
        return length;
    }
}