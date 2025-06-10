package model.reading;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PlayingSurfaceData;
import model.Position;
import model.Wall;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a single method for reading a {@link model.PlayingSurface PlayingSurface} from file.
 */
public class JsonReader {

    /**
     * Reads the {@link model.PlayingSurface PlayingSurface} from a file with a given {@code path}.
     * @param pathInResources the path inside the resources folder of the needed file
     * @return the position of the Start cell, the Goal cell, and a
     * {@link List} of {@link Wall Walls}, read from file
     */
    /*public static List<Wall> readJsonFromResources(String pathInResources) {
        try {
            InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(pathInResources);
            if(inputStream == null){
                throw new IllegalArgumentException("Can't find file at: " + pathInResources);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, new TypeReference<List<Wall>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Unexpected error occurred when reading file.");
    }*/

    public static PlayingSurfaceData readJsonFromResources(String pathInResources) {
        try {
            InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(pathInResources);
            if (inputStream == null) {
                throw new IllegalArgumentException("Can't find file at: " + pathInResources);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            if (!root.isArray() || root.size() < 2) {
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
            e.printStackTrace();
        }

        throw new RuntimeException("Unexpected error occurred when reading file.");
    }
}