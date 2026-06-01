package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigManager.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("Arquivo config.properties não encontrado");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    public static String get(String key) {
        
        String envValue = System.getenv(key.toUpperCase().replace(".", "_"));

        if(envValue != null && !envValue.isEmpty()){
            return envValue;
        }
        
        String value = properties.getProperty(key);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Propriedade não encontrada: " + key);
        }

        return value;
    }
}