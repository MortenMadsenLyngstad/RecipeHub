package file;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserFilehandlerTest {
    private UserFilehandler userFilehandler;


    
    @BeforeEach
    public void setup() {
        this.userFilehandler = new UserFilehandler("/test.csv");
    }
    
    private void deleteFile() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("test.csv"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testWriteUserinfo() {
        assertDoesNotThrow(() -> userFilehandler.writeUserinfo("testuser", "Password123"));
        deleteFile();
    }
    
    @Test
    @DisplayName("Test if correct info is written to file")
    public void testWritToFileAndReadFromFile() {
        String username = "testuser";
        String password = "Password123";
        userFilehandler.writeUserinfo(username, password);
        Hashtable<String, String> readFileInfo = userFilehandler.getUserinfo();
        Assertions.assertTrue(readFileInfo.containsKey(username), "The files should contain \"testuser\".");
        Assertions.assertEquals(password, readFileInfo.get(username), "The file should contain the password \"Password123\" for the username \"testuser\".");
        deleteFile();
    }

    @Test
    @DisplayName("Test empty hashtable is returned when file does not exist")
    public void testReturnEmptyHashtable() {
        deleteFile();
        Assertions.assertEquals(new Hashtable<String, String>(), userFilehandler.getUserinfo());
    }
}
