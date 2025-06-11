package model.reading;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PlayingSurfaceData;
import model.Position;
import model.Wall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a single method for reading a {@link model.PlayingSurface PlayingSurface} from file.
 */
public class JsonReader {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Reads the {@link PlayingSurfaceData} from file.
     * @param pathInResources the relative path in the resources folder of the json file
     *                        describing the {@link model.PlayingSurface}
     * @return the {@link PlayingSurfaceData data} read from the file
     */
    public static PlayingSurfaceData readJsonFromResources(String pathInResources) {
        try {
            InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(pathInResources);
            if (inputStream == null) {
                LOGGER.error("Can't find file at: {}", pathInResources);
                throw new IllegalArgumentException("Can't find file at: " + pathInResources);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            if (!root.isArray() || root.size() < 2) {
                LOGGER.error("JSON must contain at least goal and start positions.");
                throw new IllegalArgumentException("JSON must contain at least goal and start positions.");
            }

            Position start = mapper.treeToValue(root.get(0), Position.class);
            Position goal = mapper.treeToValue(root.get(1), Position.class);

            List<Wall> walls = new ArrayList<>();
            for (int i = 2; i < root.size(); i++) {
                walls.add(mapper.treeToValue(root.get(i), Wall.class));
            }

            return new PlayingSurfaceData(start, goal, walls);

        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}