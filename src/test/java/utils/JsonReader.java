package utils;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

    private static final String BASE_PATH = "src/test/resources/testdata/";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T read(String fileName, Class<T> className) {

        try {
                return mapper.readValue(
                new File(BASE_PATH + fileName),
                className);
              
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + fileName, e);
        }
    }
}