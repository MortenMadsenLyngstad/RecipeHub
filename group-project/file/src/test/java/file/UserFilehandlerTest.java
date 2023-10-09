package file;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Profile;

public class UserFilehandlerTest {
    private UserFilehandler userFilehandler;


    
    @BeforeEach
    public void setup() {
        this.userFilehandler = new UserFilehandler("test.json");
    }
    
    private void deleteFile() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("test.json"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testWriteUserinfo() {
        assertDoesNotThrow(() -> userFilehandler.writeProfile(new Profile("testuser", "Password123")));
        deleteFile();
    }
    
    @Test
    @DisplayName("Test if correct info is written to file")
    public void testWriteToFileAndReadFromFile() {
        String username = "testuser";
        String password = "Password123";
        userFilehandler.writeProfile(new Profile(username, password));
        Map<String, String> readFileInfo = userFilehandler.readUsernamesAndPasswords();
        Assertions.assertTrue(readFileInfo.containsKey(username), "The files should contain \"testuser\".");
        Assertions.assertEquals(password, readFileInfo.get(username), "The file should contain the password \"Password123\" for the username \"testuser\".");
        deleteFile();
    }

    @Test
    @DisplayName("Test empty hashtable is returned when file does not exist")
    public void testReturnEmptyHashtable() {
        deleteFile();
        Assertions.assertEquals(new HashMap<String, String>(), userFilehandler.readUsernamesAndPasswords());
    }
}
