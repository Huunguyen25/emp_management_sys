package employee_manager.util;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

// THis class will fetch username and password for db
public class Config {
    public static String DB_URL;
    public static String DB_USERNAME;
    public static String DB_PASSWORD;

    static{
        String configPath = System.getProperty("config.file", "config.properties");
        try(InputStream configIS = new FileInputStream(configPath)){
            Properties prop = new Properties();
            prop.load(configIS);
            DB_URL = prop.getProperty("db.url");
            DB_USERNAME = prop.getProperty("db.username");
            DB_PASSWORD = prop.getProperty("db.password");
        } catch (IOException e){
            System.out.println("Failed to load config.properties. Please create the file like the config.properties.example.");
            throw new RuntimeException(e);
        }
    }
}
