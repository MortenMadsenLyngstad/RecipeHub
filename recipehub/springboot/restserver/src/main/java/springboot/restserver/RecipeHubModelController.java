package springboot.restserver;

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

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;
import core.RecipeLibrary;

/**
 * The service implementation.
 */
@RestController
@RequestMapping(RecipeHubModelController.RECIPEHUB_MODEL_SERVICE_PATH)
public class RecipeHubModelController {

    public static final String RECIPEHUB_MODEL_SERVICE_PATH = "recipehub";

    private RecipeHubModelService recipeHubModelService;

    @Autowired
    public RecipeHubModelController(RecipeHubModelService recipeHubModelService) {
        this.recipeHubModelService = recipeHubModelService;
    }

    @GetMapping
    public RecipeHubModel getRecipeHubModel() {
        return recipeHubModelService.getRecipeHubModel();
    }

    @GetMapping(path = "/recipelibrary")
    public RecipeLibrary getRecipeLibrary() {
        return getRecipeHubModel().getRecipeLibrary();
    }

    private boolean checkRecipe(Recipe recipe) {
        return getRecipeHubModel().containsRecipe(recipe);
    }

    @GetMapping(path = "/recipelibrary/{name}")
    public Recipe getRecipe(@PathVariable("name") String name) {
        return getRecipeHubModel().getRecipe(name);
    }

    @PostMapping(path = "/recipelibrary")
    public boolean addRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe)) {
            return false;
        }
        getRecipeHubModel().addRecipe(recipe);
        return true;
    }

    @DeleteMapping(path = "/recipelibrary")
    public boolean removeRecipe(@RequestBody Recipe recipe) {
        if (!checkRecipe(recipe)) {
            return false;
        }
        getRecipeHubModel().removeRecipe(recipe);
        return true;
    }

    public boolean checkProfile(Profile profile) {
        return getRecipeHubModel().containsProfile(profile);
    }

    @GetMapping(path = "/profiles/{username}")
    public Profile getProfile(@PathVariable("username") String username) {
        return getRecipeHubModel().getProfile(username);
    }

    @GetMapping(path = "/profiles")
    public List<Profile> getProfiles() {
        return getRecipeHubModel().getProfiles();
    }

    @PutMapping(path = "/profiles")
    public boolean putProfile(@RequestBody Profile profile) {
        getRecipeHubModel().putProfile(profile);
        return true;
    }
}
