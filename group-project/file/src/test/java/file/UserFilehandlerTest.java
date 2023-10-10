package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Profile;

public class UserFilehandlerTest {
    private UserFilehandler userFilehandler;

    /**
     * This method is run before each test
     * This method uses the UserFilehandler constructor which uses createFile from
     * FileUtil
     * createFile is tested in FileUtilTest
     */
    @BeforeEach
    public void setup() {
        this.userFilehandler = new UserFilehandler("test.json");
    }

    /**
     * Helper method to delete testfiles
     */
    private void deleteFile() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("test.json"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests if the profile is written to the file correctly
     */
    @Test
    public void testWriteProfile() {
        Assertions.assertDoesNotThrow(() -> userFilehandler.writeProfile(new Profile("testuser", "Password123")));
        userFilehandler.writeProfile(new Profile("testuser", "Password123"));
        Assertions.assertEquals(userFilehandler.readProfiles().size(), 1);
        deleteFile();
    }

    /**
     * Tests if the Profile is written and read from file correctly
     */
    @Test
    @DisplayName("Test if correct info is written to file")
    public void testWriteToFileAndReadFromFile() {
        String username = "testuser";
        String password = "Password123";
        userFilehandler.writeProfile(new Profile(username, password));
        Map<String, String> readFileInfo = userFilehandler.readUsernamesAndPasswords();
        Assertions.assertTrue(readFileInfo.containsKey(username), "The files should contain \"testuser\".");
        Assertions.assertEquals(password, readFileInfo.get(username),
                "The file should contain the password \"Password123\" for the username \"testuser\".");
        deleteFile();
    }

    /**
     * Tests if readUsernamesAndPasswords returns the correct information after a
     * profile is written to file
     * Tests if an empty hashtable is returned when the file does not exist
     */
    @Test
    @DisplayName("Test empty hashtable is returned when file does not exist")
    public void testReadUsernamesAndPasswords() {
        String username = "testuser";
        String password = "Password123";
        userFilehandler.writeProfile(new Profile(username, password));
        Assertions.assertEquals(new Hashtable<>(Map.of(username, password)),
                userFilehandler.readUsernamesAndPasswords());
        deleteFile();
        Assertions.assertEquals(new HashMap<String, String>(), userFilehandler.readUsernamesAndPasswords());
    }
}
