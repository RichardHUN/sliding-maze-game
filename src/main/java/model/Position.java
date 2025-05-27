package model;

public record Position(int x, int y) {
    public static Position of(Position position){
        return new Position(position.x(), position.y());
    }

    public static Position of(int x, int y){
        return new Position(x, y);
    }
}
