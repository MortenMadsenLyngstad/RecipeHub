package file;

import core.PasswordHasher;
import core.Profile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the UserFilehandler class.
 */
public class UserFilehandlerTest {
    private UserFilehandler userFilehandler;

    /**
     * This method is run before each test.
     * This method uses the UserFilehandler constructor which uses createFile from
     * FileUtil
     * createFile is tested in FileUtilTest
     */
    @BeforeEach
    public void setup() {
        this.userFilehandler = new UserFilehandler();
        UserFilehandler.setFileName("test.json");
    }

    /**
     * Helper method to delete testfiles.
     */
    private void deleteFile(String filename) {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve(filename));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests if the profile is written to the file correctly.
     */
    @Test
    public void testWriteProfile() {
        Assertions.assertFalse(userFilehandler.writeProfile(null));
        Assertions.assertDoesNotThrow(() -> userFilehandler.writeProfile(
                new Profile("testuser", "Password123")));
        userFilehandler.writeProfile(new Profile("testuser", "Password123"));
        Assertions.assertEquals(userFilehandler.readProfiles().size(), 1);
    }

    /**
     * Tests if the Profile is written and read from file correctly.
     */
    @Test
    @DisplayName("Test if correct info is written to and read from file")
    public void testWriteToFileAndReadFromFile() {
        String username = "testuser";
        String password = "Password123";
        Profile profile = new Profile(username, password);
        userFilehandler.writeProfile(profile);
        Profile readProfile = userFilehandler.loadProfile(username);
        Assertions.assertTrue(username.equals(readProfile.getUsername()),
                "The files should contain \"testuser\".");
        Assertions.assertTrue(PasswordHasher.verifyPassword(password,
                readProfile.getHashedPassword()),
                "The file should contain the hashed password for the profile.");
    }

    @Test
    @DisplayName("Test if writeAllProfiles writes all profiles to file")
    public void testWriteAllProfiles() {
        Assertions.assertFalse(userFilehandler.writeAllProfiles(null));
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Testuser1", "Easypass1"));
        profiles.add(new Profile("Testuser2", "Easypass2"));
        profiles.add(new Profile("Testuser3", "Easypass3"));

        userFilehandler.writeAllProfiles(profiles);

        List<Profile> readProfiles = new ArrayList<>();
        readProfiles = userFilehandler.readProfiles();
        Assertions.assertEquals(readProfiles.get(0).getUsername(),
                readProfiles.get(0).getUsername(),
                "The first profile should have the username \"Testuser1\".");
        Assertions.assertEquals(readProfiles.get(1).getUsername(),
                readProfiles.get(1).getUsername(),
                "The second profile should have the username \"Testuser2\".");
        Assertions.assertEquals(readProfiles.get(2).getUsername(),
                readProfiles.get(2).getUsername(),
                "The third profile should have the username \"Testuser3\".");
    }

    /**
     * This method tests if the getters and setters work properly.
     */
    @Test
    @DisplayName("Test getFileName")
    public void testGettersAndSetters() {
        Assertions.assertEquals("test.json", UserFilehandler.getFileName(),
                "The file should be named \"test.json\".");
        Assertions.assertEquals(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "test.json"),
                UserFilehandler.getFilePath(),
                "The file should be in the home directory.");
        UserFilehandler.setFileName("newtest.json");
        Assertions.assertEquals("newtest.json", UserFilehandler.getFileName(),
                "The file should be named \"newtest.json\".");
        Assertions.assertEquals(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "newtest.json"),
                UserFilehandler.getFilePath(),
                "The file should be in the home directory.");
    }

    /**
     * This method is run after each test.
     * It deletes test.json and newtest.json if it exists.
     */
    @AfterEach
    public void cleanUp() {
        deleteFile("test.json");
        if (Files.exists(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "newtest.json"))) {
            deleteFile("newtest.json");
        }
    }
}
