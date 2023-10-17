package file;

import com.google.gson.reflect.TypeToken;
import core.Profile;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This class handles file operations for users.
 */
public class UserFilehandler extends FileUtil {
    private Path filePath;

    /**
     * This constructor initializes the filePath.
     * 
     * @param file - File to write to
     */
    public UserFilehandler(String file) {
        this.filePath = Path.of(System.getProperty("user.home") 
        + System.getProperty("file.separator") + file);
        createFile(this.filePath);
    }

    /**
     * This method writes a profile to the file.
     * 
     * @param profile - Profile object to write
     */
    public void writeProfile(Profile profile) {
        List<Profile> profiles = readProfiles();

        profiles.remove(profiles.stream()
            .filter(p -> p.getUsername().equals(profile.getUsername()))
            .findFirst()
            .orElse(null));

        profiles.add(profile);
        writeFile(filePath, profiles);
    }

    /**
     * This method reads profiles from the file.
     * 
     * @return - Returns a list of profiles
     */
    public List<Profile> readProfiles() {
        List<Profile> profiles = new ArrayList<>();
        Type profileListType = new TypeToken<List<Profile>>() {
        }.getType();
        profiles = readFile(filePath, profiles, profileListType);
        if (profiles == null) {
            return new ArrayList<>();
        }
        return profiles;
    }

    /**
     * This method writes all profiles to the file.
     * 
     * @param profiles - List of profiles to write
     */
    public void writeAllProfiles(List<Profile> profiles) {
        writeFile(filePath, profiles);
    }

    /**
     * This method reads usernames and passwords from the file.
     * 
     * @return - Returns a hashtable with usernames as keys and passwords as values
     */
    public Hashtable<String, String> readUsernamesAndPasswords() {
        Hashtable<String, String> userinfo = new Hashtable<>();

        List<Profile> profiles = readProfiles();

        for (Profile profile : profiles) {
            String username = profile.getUsername();
            String hashedPassword = profile.getHashedPassword();

            // Check if the username is not already in the map (to handle duplicates if
            // necessary)
            userinfo.put(username, hashedPassword);
        }
        return userinfo;
    }
}
