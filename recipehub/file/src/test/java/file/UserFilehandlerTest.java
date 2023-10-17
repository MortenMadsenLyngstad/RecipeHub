package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.PasswordHasher;
import core.Profile;

public class UserFilehandlerTest {
    private UserFilehandler userFilehandler;
    private PasswordHasher passwordHasher = new PasswordHasher();

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
        String hashedPassword = passwordHasher.hashPassword(password);
        Profile profile = new Profile(username, password);
        profile.setHashedPassword(hashedPassword);
        userFilehandler.writeProfile(profile);
        Map<String, String> readFileInfo = userFilehandler.readUsernamesAndPasswords();
        Assertions.assertTrue(readFileInfo.containsKey(username), "The files should contain \"testuser\".");
        Assertions.assertEquals(hashedPassword, readFileInfo.get(username),
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
        String hashedPassword = passwordHasher.hashPassword(password);
        Profile profile = new Profile(username, password);
        profile.setHashedPassword(hashedPassword);
        userFilehandler.writeProfile(profile);
        Assertions.assertEquals(new Hashtable<>(Map.of(username, hashedPassword)),
                userFilehandler.readUsernamesAndPasswords(), "The hashtable should contain the username \"testuser\".");
        deleteFile();
        Assertions.assertEquals(new HashMap<String, String>(), userFilehandler.readUsernamesAndPasswords());
    }

    @Test
    @DisplayName("Test if writeAllProfiles writes all profiles to file")
    public void testWriteAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile("Testuser1", "Easypass1"));
        profiles.add(new Profile("Testuser2", "Easypass2"));
        profiles.add(new Profile("Testuser3", "Easypass3"));

        userFilehandler.writeAllProfiles(profiles);

        List<Profile> readProfiles = new ArrayList<>();
        readProfiles = userFilehandler.readProfiles();
        Assertions.assertEquals(readProfiles.get(0).getUsername(), readProfiles.get(0).getUsername(),
                "The first profile should have the username \"Testuser1\".");
        Assertions.assertEquals(readProfiles.get(1).getUsername(), readProfiles.get(1).getUsername(),
                "The second profile should have the username \"Testuser2\".");
        Assertions.assertEquals(readProfiles.get(2).getUsername(), readProfiles.get(2).getUsername(),
                "The third profile should have the username \"Testuser3\".");
        deleteFile();
    }
}
