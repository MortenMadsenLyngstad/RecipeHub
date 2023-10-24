package springboot.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.RecipeFilehandler;

/**
 * Configures the todo service,
 * including autowired objects.
 */
@Service
public class RecipeLibraryService {
    private RecipeLibrary recipeLibrary;
    private RecipeFilehandler recipeFilehandler;

    public RecipeLibraryService(RecipeLibrary recipeLibrary) {
        this.recipeLibrary = recipeLibrary;
        this.recipeFilehandler = new RecipeFilehandler("springbootserver-recipes.json");
    }

    public RecipeLibrary getRecipeLibrary() {
      return recipeLibrary;
    }

    public void setRecipeLibrary(RecipeLibrary recipeLibrary) {
        this.recipeLibrary = recipeLibrary;
    }

    private static RecipeLibrary createDefaultRecipeLibrary() {
        RecipeFilehandler recipeFilehandler = new RecipeFilehandler("springbootserver-recipes.json");
        URL url = RecipeLibraryService.class.getResource("default-recipeLibrary.json");
        if (url != null) {
          try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            return recipeFilehandler.readRecipeLibrary();
          } catch (IOException e) {
            System.out.println("Couldn't read default-RecipeLibrary.json, so rigging RecipeLibrary manually ("
                + e + ")");
          }
        }

        RecipeLibrary recipeLibrary = new RecipeLibrary();
        Recipe recipe1 = new Recipe("recipe1", 2, new Profile("profile1", "Password1"));
        Recipe recipe2 = new Recipe("recipe2", 2, new Profile("profile2", "Password2"));
        recipeLibrary.addRecipe(recipe1);
        recipeLibrary.addRecipe(recipe2);
        return recipeLibrary;
      }

      public void autoSaveRecipeLibrary() {
        if (recipeFilehandler != null) {
          recipeFilehandler.writeRecipeLibrary(recipeLibrary);
        }
      }
}
