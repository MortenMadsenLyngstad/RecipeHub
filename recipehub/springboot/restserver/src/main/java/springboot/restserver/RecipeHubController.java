package springboot.restserver;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ui.DirectRecipeHubAccess;
import ui.RecipeHubAccess;

/**
 * The service implementation.
 */
@RestController
@RequestMapping(RecipeHubController.RECIPEHUB_MODEL_SERVICE_PATH)
public class RecipeHubController {

    public static final String RECIPEHUB_MODEL_SERVICE_PATH = "recipehub";

    private RecipeHubService recipeHubService;

    private RecipeHubAccess access = new DirectRecipeHubAccess();

    /**
     * Constructor.
     * @param recipeHubService - the recipe hub service
     */
    @Autowired
    public RecipeHubController(RecipeHubService recipeHubService) {
        this.recipeHubService = recipeHubService;
    }

    /**
     * Get the recipe library.
     * @return the recipe library
     */
    @GetMapping(path = "/recipelibrary")
    public RecipeLibrary getRecipeLibrary() {
        return recipeHubService.getRecipeLibrary();
    }

    private boolean checkRecipe(Recipe recipe) {
        return recipeHubService.containsRecipe(recipe);
    }

    /**
     * Adds a recipe.
     * @param recipe - the recipe to add
     * @return true if the recipe was added, false otherwise
     */
    @PostMapping(path = "/recipelibrary")
    public boolean addRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe)) {
            return false;
        }
        recipeHubService.addRecipe(recipe);
        access.saveRecipe(recipe);
        return true;
    }

    /**
     * Removes a recipe.
     * @param recipe - the recipe to remove
     * @return true if the recipe was removed, false otherwise
     */
    @DeleteMapping(path = "/recipelibrary")
    public boolean removeRecipe(@RequestBody Recipe recipe) {
        if (!checkRecipe(recipe)) {
            return false;
        }
        recipeHubService.removeRecipe(recipe);
        access.removeRecipe(recipe);
        return true;
    }

    private boolean checkProfile(Profile profile) {
        return recipeHubService.containsProfile(profile);
    }

    /**
     * Get a profile.
     * @param username - the username of the profile to get
     * @return the profile
     */
    @GetMapping(path = "/profiles/{username}")
    public Profile getProfile(@PathVariable("username") String username) {
        return recipeHubService.getProfile(username);
    }

    /**
     * Get all profiles.
     * @return the profiles
     */
    @GetMapping(path = "/profiles")
    public List<Profile> getProfiles() {
        System.out.println("getProfiles() :" + recipeHubService.getProfiles().get(0).getUsername());
        return recipeHubService.getProfiles();
    }

    /**
     * Adds a profile.
     * @param profile - the profile to add
     * @return true if the profile was added, false otherwise
     */
    @PutMapping(path = "/profiles")
    public boolean putProfile(@RequestBody Profile profile) {
        if (checkProfile(profile)) {
            return false;
        }
        System.out.println("putProfile(Profile profile) :" + profile.getUsername());
        recipeHubService.putProfile(profile);
        access.saveProfile(profile);
        return true;
    }
}
