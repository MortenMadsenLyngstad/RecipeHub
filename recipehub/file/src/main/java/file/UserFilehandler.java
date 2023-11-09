package file;

import com.google.gson.reflect.TypeToken;
import core.Profile;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles file operations for users.
 */
public class UserFilehandler {
    private static String fileName = "userInfo.json";

    /**
     * This constructor initializes the filePath.
     */
    public UserFilehandler() {
        FileUtil.createFile(getFilePath());
    }

    /**
     * This method writes a profile to the file.
     * 
     * @param profile - Profile object to write
     */
    public boolean writeProfile(Profile profile) {
        List<Profile> profiles = readProfiles();

        profiles.remove(profiles.stream()
                .filter(p -> p.getUsername().equals(profile.getUsername()))
                .findFirst()
                .orElse(null));

        profiles.add(profile);
        return FileUtil.writeFile(getFilePath(), profiles);
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
        profiles = FileUtil.readFile(getFilePath(), profiles, profileListType);
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
    public boolean writeAllProfiles(List<Profile> profiles) {
        return FileUtil.writeFile(getFilePath(), profiles);
    }

    /**
     * This method returns a profile which matches the given username.
     * 
     * @param username - String with the username of the profile to load
     * @return - Returns a profile
     */
    public Profile loadProfile(String username) {
        List<Profile> profiles = readProfiles();
        return profiles.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * This method sets the getFilePath.
     * 
     * @param file - File to write to
     */
    public static void setFileName(String file) {
        fileName = file;
        FileUtil.createFile(getFilePath());
    }

    public static String getFileName() {
        return fileName;
    }

    public static Path getFilePath() {
        return Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + fileName);
    }
}
