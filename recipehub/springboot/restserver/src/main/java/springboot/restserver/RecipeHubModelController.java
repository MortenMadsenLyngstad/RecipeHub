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
        return recipeHubModelService.getRecipeHubModel().getRecipeLibrary();
    }

    public boolean checkRecipe(Recipe recipe) {
        return recipeHubModelService.getRecipeHubModel().containsRecipe(recipe);
    }

    @GetMapping(path = "/recipelibrary/{name}")
    public Recipe getRecipe(@PathVariable("name") String name) {
        return recipeHubModelService.getRecipeHubModel().getRecipe(name);
    }

    @PostMapping(path = "/recipelibrary")
    public boolean addRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe) == true) {
            throw new IllegalArgumentException("The recipe is already added");
        }
        recipeHubModelService.getRecipeHubModel().addRecipe(recipe);
        return true;
    }

    @DeleteMapping(path = "/recipelibrary")
    public boolean removeRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe) == false) {
            throw new IllegalArgumentException("The recipe does not exist");
        }
        recipeHubModelService.getRecipeHubModel().removeRecipe(recipe);
        return true;
    }

    public boolean checkProfile(Profile profile) {
        return recipeHubModelService.getRecipeHubModel().containsProfile(profile);
    }

    @GetMapping(path = "/profiles/{username}")
    public Profile getProfile(@PathVariable("username") String username) {
        return recipeHubModelService.getRecipeHubModel().getProfile(username);
    }

    @GetMapping(path = "/profiles")
    public List<Profile> getProfiles() {
        return recipeHubModelService.getRecipeHubModel().getProfiles();
    }

    @PostMapping(path = "/profiles")
    public boolean addProfile(@RequestBody Profile profile) {
        if (checkProfile(profile) == true) {
            throw new IllegalArgumentException("The profile is already added");
        }
        recipeHubModelService.getRecipeHubModel().addProfile(profile);
        return true;
    }

    @PutMapping(path = "/profiles")
    public boolean putProfile(@RequestBody Profile profile) {
        if (checkProfile(profile) == false) {
            throw new IllegalArgumentException("The profile does not exist");
        }
        recipeHubModelService.getRecipeHubModel().putProfile(profile);
        return true;
    }
}
