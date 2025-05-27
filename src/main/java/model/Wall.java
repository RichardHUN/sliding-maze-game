package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.persistence.WallDeserializer;

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
