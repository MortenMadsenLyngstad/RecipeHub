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

@Service
public class RecipeHubService {

    private RecipeLibrary recipeLibrary;
    private List<Profile> profiles;

    public RecipeHubService() {
        this(loadRecipeLibrary(), loadProfiles());
    }

    public RecipeHubService(RecipeLibrary recipeLibrary, List<Profile> profiles) {
        this.recipeLibrary = recipeLibrary;
        this.profiles = new ArrayList<>(profiles);
    }

    public RecipeLibrary getRecipeLibrary() {
        return recipeLibrary;
    }

    public boolean containsRecipe(Recipe recipe) {
        return recipeLibrary.containsRecipe(recipe);
    }

    public void addRecipe(Recipe recipe) {
        recipeLibrary.addRecipe(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipeLibrary.removeRecipe(recipe);
    }

    public boolean containsProfile(Profile profile) {
        return profiles.stream().anyMatch(p -> p.getUsername().equals(profile.getUsername()));
    }

    public Profile getProfile(String username) {
        return profiles.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<Profile> getProfiles() {
        return new ArrayList<>(profiles);
    }

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

    private static Path makeFilePath(String file) {
        return Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + file);
    }

    private static List<Profile> loadProfiles() {
        List<Profile> profiles = new ArrayList<>();
        Type profileListType = new TypeToken<List<Profile>>() {
        }.getType();
        profiles = FileUtil.readFile(makeFilePath("userInfo.json"), profiles, profileListType);
        return profiles;
    }

    private static RecipeLibrary loadRecipeLibrary() {
        RecipeLibrary recipeLibrary = new RecipeLibrary();
        recipeLibrary = FileUtil.readFile(makeFilePath("recipes.json"),
                recipeLibrary, RecipeLibrary.class);
        return recipeLibrary;
    }
}
