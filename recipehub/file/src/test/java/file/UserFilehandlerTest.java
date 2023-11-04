package file;

import core.PasswordHasher;
import core.Profile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
        this.userFilehandler = new UserFilehandler("test.json");
    }

    /**
     * Helper method to delete testfiles.
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
     * Tests if the profile is written to the file correctly.
     */
    @Test
    public void testWriteProfile() {
        Assertions.assertDoesNotThrow(() -> userFilehandler.writeProfile(
                new Profile("testuser", "Password123")));
        userFilehandler.writeProfile(new Profile("testuser", "Password123"));
        Assertions.assertEquals(userFilehandler.readProfiles().size(), 1);
        deleteFile();
    }

    /**
     * Tests if the Profile is written and read from file correctly.
     */
    @Test
    @DisplayName("Test if correct info is written to file")
    public void testWriteToFileAndReadFromFile() {
        String username = "testuser";
        String password = "Password123";
        String hashedPassword = PasswordHasher.hashPassword(password);
        Profile profile = new Profile(username, password);
        userFilehandler.writeProfile(profile);
        Profile readProfile = userFilehandler.loadProfile(p -> p.getUsername().equals(username)
                && PasswordHasher.verifyPassword(password, hashedPassword));
        Assertions.assertTrue(username.equals(readProfile.getUsername()),
                "The files should contain \"testuser\".");
        Assertions.assertTrue(PasswordHasher.verifyPassword(password,
                readProfile.getHashedPassword()),
                "The file should contain the hashed password for the profile.");
        deleteFile();
    }

    /**
     * Tests if loadProfile returns the correct information after a profile is
     * written to file.
     */
    @Test
    @DisplayName("Test empty hashtable is returned when file does not exist")
    public void testLoadProfile() {
        String username = "testuser";
        String password = "Password123";
        Profile profile = new Profile(username, password);
        userFilehandler.writeProfile(profile);
        Profile readProfile = userFilehandler.loadProfile(p -> p.getUsername().equals(username)
                && PasswordHasher.verifyPassword(password, p.getHashedPassword()));
        Assertions.assertEquals(username, readProfile.getUsername(),
                "The username should be \"testuser\".");
        Assertions.assertTrue(PasswordHasher.verifyPassword(password,
                readProfile.getHashedPassword()),
                "The password \"Password123\" should be correct.");
        deleteFile();
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
        Assertions.assertEquals(readProfiles.get(0).getUsername(),
                readProfiles.get(0).getUsername(),
                "The first profile should have the username \"Testuser1\".");
        Assertions.assertEquals(readProfiles.get(1).getUsername(),
                readProfiles.get(1).getUsername(),
                "The second profile should have the username \"Testuser2\".");
        Assertions.assertEquals(readProfiles.get(2).getUsername(),
                readProfiles.get(2).getUsername(),
                "The third profile should have the username \"Testuser3\".");
        deleteFile();
    }
}
