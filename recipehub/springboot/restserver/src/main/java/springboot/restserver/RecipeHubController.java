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

    @Autowired
    public RecipeHubController(RecipeHubService recipeHubService) {
        this.recipeHubService = recipeHubService;
    }

    @GetMapping(path = "/recipelibrary")
    public RecipeLibrary getRecipeLibrary() {
        return recipeHubService.getRecipeLibrary();
    }

    private boolean checkRecipe(Recipe recipe) {
        return recipeHubService.containsRecipe(recipe);
    }

    @PostMapping(path = "/recipelibrary")
    public boolean addRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe)) {
            return false;
        }
        recipeHubService.addRecipe(recipe);
        access.saveRecipe(recipe);
        return true;
    }

    @DeleteMapping(path = "/recipelibrary")
    public boolean removeRecipe(@RequestBody Recipe recipe) {
        if (!checkRecipe(recipe)) {
            return false;
        }
        recipeHubService.removeRecipe(recipe);
        access.removeRecipe(recipe);
        return true;
    }

    public boolean checkProfile(Profile profile) {
        return recipeHubService.containsProfile(profile);
    }

    @GetMapping(path = "/profiles/{username}")
    public Profile getProfile(@PathVariable("username") String username) {
        return recipeHubService.getProfile(username);
    }

    @GetMapping(path = "/profiles")
    public List<Profile> getProfiles() {
        System.out.println("getProfiles() :" + recipeHubService.getProfiles().get(0).getUsername());
        return recipeHubService.getProfiles();
    }

    @PutMapping(path = "/profiles")
    public boolean putProfile(@RequestBody Profile profile) {
        System.out.println("putProfile(Profile profile) :" + profile.getUsername());
        recipeHubService.putProfile(profile);
        access.saveProfile(profile);
        return true;
    }
}
