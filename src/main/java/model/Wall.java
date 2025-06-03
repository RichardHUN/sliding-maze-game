package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.reading.WallDeserializer;

/**
 * Represents a wall on the {@link PlayingSurface}.
 * @param position the {@link Position} of the {@link Cell} which is next to the wall
 * @param side the side of the {@link Cell} on which the wall is
 */
@JsonDeserialize(using = WallDeserializer.class)
public record Wall(Position position, Directions.Direction side) {

    @Override
    public String toString() {
        return "model.Wall{" +
                "position=" + position +
                ", side=" + side +
                '}';
    }
}
