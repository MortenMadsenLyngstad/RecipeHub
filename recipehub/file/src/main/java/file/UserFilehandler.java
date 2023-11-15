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
     * This method writes a profile to the file. If the profile already exists,
     * it will be overwritten to update the profile.
     * 
     * @param profile - Profile object to write
     * @return - Returns true if the profile was written, false if null or not written
     */
    public boolean writeProfile(Profile profile) {
        if (profile == null) {
            return false;
        }
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
     * This method writes all profiles to the file. If the list of profiles is emty
     * or null, nothing is written.
     * 
     * @param profiles - List of profiles to write
     * @return - Returns true if the profiles were written, false if null or not written
     */
    public boolean writeAllProfiles(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            return false;
        }
        return FileUtil.writeFile(getFilePath(), profiles);
    }

    /**
     * This method loads a profile from the file.
     * 
     * @param username - Username of the profile to load
     * @return Returns the profile
     */
    public Profile loadProfile(String username) {
        List<Profile> profiles = readProfiles();
        return profiles.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * This method sets the filename.
     * 
     * @param file - File to write to
     * @throws IllegalArgumentException if the filename is empty
     */
    public static void setFileName(String file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be an empty string");
        }
        fileName = file;
        FileUtil.createFile(getFilePath());
    }

    /**
     * This method gets the filename.
     * 
     * @return - String value of the filename
     */
    public static String getFileName() {
        return fileName;
    }

    /**
     * This method gets the filePath.
     * 
     * @return - Path object of the filePath
     */
    public static Path getFilePath() {
        return Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + fileName);
    }
}
