package springboot.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.Recipe;
import core.RecipeLibrary;

/**
 * The service implementation.
 */
@RestController
@RequestMapping(RecipeLibraryController.RECIPE_LIBRARY_SERVICE_PATH)
public class RecipeLibraryController {

    public static final String RECIPE_LIBRARY_SERVICE_PATH = "/recipeLibrary";

    @Autowired
    private RecipeLibraryService recipeLibraryService;

    @GetMapping
    public RecipeLibrary getRecipeLibrary() {
        return recipeLibraryService.getRecipeLibrary();
    }

    private void autoSaveRecipeLibrary() {
        recipeLibraryService.autoSaveRecipeLibrary();
    }

    private boolean checkRecipe(Recipe recipe) {
        return recipeLibraryService.getRecipeLibrary().containsRecipe(recipe);
    }

    @GetMapping(path = "/recipeLibary/{index}")
    public Recipe getRecipe(@PathVariable("index") int index) {
        Recipe recipe = recipeLibraryService.getRecipeLibrary().getRecipe(index);
        return recipe;
    }

    @PostMapping(path = "/recipeLibrary")
    public boolean addRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe) == true) {
            throw new IllegalArgumentException("The recipe is already added");
        }
        recipeLibraryService.getRecipeLibrary().addRecipe(recipe);
        autoSaveRecipeLibrary();
        return true;
    }

    @DeleteMapping(path = "/recipeLibrary")
    public boolean removeRecipe(@RequestBody Recipe recipe) {
        if (checkRecipe(recipe) == false) {
            throw new IllegalArgumentException("The recipe does not exist");
        }
        recipeLibraryService.getRecipeLibrary().removeRecipe(recipe);
        autoSaveRecipeLibrary();
        return true;
    }
}
