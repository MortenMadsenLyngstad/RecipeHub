package file;

import com.google.gson.reflect.TypeToken;

import core.Profile;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class handles file operations for users.
 */
public class UserFilehandler {
    private Path filePath;

    /**
     * This constructor initializes the filePath.
     * 
     * @param file - File to write to
     */
    public UserFilehandler(String file) {
        this.filePath = Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + file);
        FileUtil.createFile(this.filePath);
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
        FileUtil.writeFile(filePath, profiles);
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
        profiles = FileUtil.readFile(filePath, profiles, profileListType);
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
        FileUtil.writeFile(filePath, profiles);
    }

    /**
     * This method returns the profile with the given username.
     * @param username - Username of the profile to return
     * @return - Returns the profile with the given username
     */
    public Profile loadProfile(Predicate<Profile> predicate) {
        List<Profile> profiles = readProfiles();
        return profiles.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
