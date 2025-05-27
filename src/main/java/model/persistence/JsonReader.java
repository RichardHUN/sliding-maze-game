package model.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Wall;

import java.io.InputStream;
import java.util.List;

public class JsonReader {

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