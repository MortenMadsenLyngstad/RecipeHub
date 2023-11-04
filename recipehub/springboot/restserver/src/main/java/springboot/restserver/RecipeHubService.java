package springboot.restserver;

import com.google.gson.reflect.TypeToken;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.FileUtil;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The service implementation.
 */
@Service
public class RecipeHubService {

    private RecipeLibrary recipeLibrary;
    private List<Profile> profiles;

    /**
     * Constructor.
     */
    public RecipeHubService() {
        this(loadRecipeLibrary(), loadProfiles());
    }

    /**
     * Constructor.
     * @param recipeLibrary - the recipe library
     * @param profiles - the profiles
     */
    public RecipeHubService(RecipeLibrary recipeLibrary, List<Profile> profiles) {
        this.recipeLibrary = recipeLibrary;
        this.profiles = new ArrayList<>(profiles);
    }

    /**
     * Get the recipe library.
     * @return the recipe library
     */
    public RecipeLibrary getRecipeLibrary() {
        return recipeLibrary;
    }

    /**
     * Check if the recipe exists.
     * @param recipe - the recipe
     * @return true if the recipe exists, false otherwise
     */
    public boolean containsRecipe(Recipe recipe) {
        return recipeLibrary.containsRecipe(recipe);
    }

    /**
     * Add a recipe.
     * @param recipe - the recipe to add
     */
    public void addRecipe(Recipe recipe) {
        recipeLibrary.addRecipe(recipe);
    }

    /**
     * Remove a recipe.
     * @param recipe - the recipe to remove
     */
    public void removeRecipe(Recipe recipe) {
        recipeLibrary.removeRecipe(recipe);
    }

    /**
     * Check if the profile exists.
     * @param profile - the profile
     * @return true if the profile exists, false otherwise
     */
    public boolean containsProfile(Profile profile) {
        return profiles.stream().anyMatch(p -> p.getUsername().equals(profile.getUsername()));
    }

    /**
     * Get the profile with the given username.
     * @param username - the username
     * @return the profile with the given username, or null if no such profile exists
     */
    public Profile getProfile(String username) {
        return profiles.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the profiles.
     * @return the profiles
     */
    public List<Profile> getProfiles() {
        return new ArrayList<>(profiles);
    }

    /**
     * Put a profile.
     * @param profile - the profile to put
     * @return the old profile with the same username, or null if no such profile exists
     */
    public Profile putProfile(Profile profile) {
        int index = profiles.stream()
                .filter(p -> p.getUsername().equals(profile.getUsername()))
                .findFirst()
                .map(p -> profiles.indexOf(p)).orElse(-1);
        if (index >= 0) {
            Profile oldProfile = profiles.get(index);
            profiles.set(index, profile);
            return oldProfile;
        } else {
            profiles.add(profile);
            return null;
        }
    }

    /**
     * Helper method to make a file path.
     * @param file - the filename
     * @return the file path
     */
    private static Path makeFilePath(String file) {
        return Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + file);
    }

    /**
     * Reads the profiles from the file.
     * @return the profiles
     */
    private static List<Profile> loadProfiles() {
        List<Profile> profiles = new ArrayList<>();
        Type profileListType = new TypeToken<List<Profile>>() {
        }.getType();
        profiles = FileUtil.readFile(makeFilePath("userInfo.json"), profiles, profileListType);
        return profiles;
    }

    /**
     * Reads the recipe library from the file.
     * @return the recipe library
     */
    private static RecipeLibrary loadRecipeLibrary() {
        RecipeLibrary recipeLibrary = new RecipeLibrary();
        recipeLibrary = FileUtil.readFile(makeFilePath("recipes.json"),
                recipeLibrary, RecipeLibrary.class);
        return recipeLibrary;
    }
}
