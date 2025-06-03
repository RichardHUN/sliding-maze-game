package model.reading;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Wall;

import java.io.InputStream;
import java.util.List;

/**
 * Contains a single method for reading a {@link model.PlayingSurface PlayingSurface} from file.
 */
public class JsonReader {

    /**
     * Reads the {@link model.PlayingSurface PlayingSurface} from a file with a given {@code path}.
     * @param pathInResources the path inside the resources folder of the needed file
     * @return a {@link List} of {@link Wall Walls}, read from file
     */
    public static List<Wall> readJsonFromResources(String pathInResources) {
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
    }
}