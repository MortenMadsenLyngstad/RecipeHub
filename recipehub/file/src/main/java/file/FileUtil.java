package file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is a utility class for filehandling.
 */
public class FileUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * This method creates a file if it does not already exist.
     * 
     * @param filePath - Path object to the file
     */
    public void createFile(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                // This will never happen
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method writes data to a file.
     * 
     * @param <T>      - Generic type
     * @param filePath - Path object to the file
     * @param data     - Data to write to the file
     */
    public <T> void writeFile(Path filePath, T data) {
        try (Writer writer = new FileWriter(filePath.toFile(), Charset.forName("UTF-8"))) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method reads data from a file.
     * 
     * @param <T>      - Generic type
     * @param filePath - Path object to the file
     * @param data     - Data to read from the file
     * @param type     - Type object
     * @return - Returns the data read from the file
     */
    public <T> T readFile(Path filePath, T data, Type type) {
        try (Reader reader = new FileReader(filePath.toFile(), Charset.forName("UTF-8"))) {
            data = gson.fromJson(reader, type);
        } catch (IOException e) {
            System.out.println("Error reading from file");
            System.out.println(e.getMessage());
        }
        return data;
    }
}