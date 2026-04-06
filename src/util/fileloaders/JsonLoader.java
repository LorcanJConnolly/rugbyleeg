package util.fileloaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import world.templates.entities.PlayerTemplate;

import java.io.InputStream;

public class JsonLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T load(String path, Class<T> type){

        try (InputStream stream = JsonLoader.class.getClassLoader().getResourceAsStream(path)){
            if (stream == null){
                throw new RuntimeException("Resource not found: " + path);
            }

            return objectMapper.readValue(stream, type);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Basic test
    public static void main(String[] args) {
        PlayerTemplate player = JsonLoader.load("data/players/centre.json", PlayerTemplate.class);
        System.out.println(player);
    }
}
