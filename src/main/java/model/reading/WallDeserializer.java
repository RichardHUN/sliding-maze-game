package model.reading;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.Directions;
import model.Position;
import model.Wall;

import java.io.IOException;

/**
 * Contains a single method needed to deserialize a JSON file containing a {@link model.PlayingSurface PlayingSurface}.
 */
public class WallDeserializer extends JsonDeserializer<Wall> {

    /**
     * Creates a {@link Wall Wall} from a JSON object.
     * @param parser the {@link JsonParser} used the parse through the file
     * @param ctxt contextual information(not needed)
     * @return a wall read from the file
     * @throws IOException if an error occurs during the reading of the file
     */
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
