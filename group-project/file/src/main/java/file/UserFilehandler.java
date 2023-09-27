package file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import core.Profile;

public class UserFilehandler {
  public Hashtable<String, String> userinfo = new Hashtable<String, String>();
  private Path filePath;
  private final Gson gson;

  /**
   * Constructor for UserFilehandler
   * Used to create a UserFilehandler object with a custom filepath (used for
   * testing)
   * 
   * @param file
   */
  public UserFilehandler(String file) {
    this.filePath = Path.of(System.getProperty("user.home") + System.getProperty("file.separator") + file);
    this.gson = new GsonBuilder().setPrettyPrinting().create();

    if (!Files.exists(filePath)) {
      try {
        Files.createFile(filePath);
      } catch (IOException e) {
        System.out.println("Error creating file");
        System.out.println(e.getMessage());
      }
    }
  }

  public List<Profile> readProfiles() {
    try (Reader reader = new FileReader(filePath.toFile())) {
      Type profileListType = new TypeToken<List<Profile>>() {
      }.getType();
      return gson.fromJson(reader, profileListType);
    } catch (IOException e) {
      System.out.println("Error reading from file");
      System.out.println(e.getMessage());
      return new ArrayList<>();
    }
  }

  public void writeProfile(Profile profile) {
    List<Profile> profiles = readProfiles();
    profiles.add(profile);
    try (Writer writer = new FileWriter(filePath.toFile())) {
      gson.toJson(profiles, writer);
    } catch (IOException e) {
      System.out.println("Error writing to file");
      System.out.println(e.getMessage());
    }
  }

  public Map<String, String> readUsernamesAndPasswords() {
    Map<String, String> usernamePasswordMap = new HashMap<>();

    List<Profile> profiles = readProfiles();

    for (Profile profile : profiles) {
      String username = profile.getUsername();
      String password = profile.getPassword();

      // Check if the username is not already in the map (to handle duplicates if
      // necessary)
      if (!usernamePasswordMap.containsKey(username)) {
        usernamePasswordMap.put(username, password);
      }
    }
    return usernamePasswordMap;
  }
}
