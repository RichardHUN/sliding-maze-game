package model.persistence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.Directions;
import model.Position;
import model.Wall;

import java.io.IOException;

public class WallDeserializer extends JsonDeserializer<Wall> {

    @Override
    public Wall deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        int x = node.get("x").asInt();
        int y = node.get("y").asInt();
        String sideStr = node.get("side").asText();

        Position position = new Position(x, y);
        Directions.Direction side = Directions.Direction.valueOf(sideStr.toUpperCase());

        return new Wall(position, side);
    }
}
