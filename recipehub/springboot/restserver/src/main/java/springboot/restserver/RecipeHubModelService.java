package springboot.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;
import core.RecipeLibrary;
import file.RecipeFilehandler;
import file.UserFilehandler;

/**
 * Configures the todo service,
 * including autowired objects.
 */
@Service
public class RecipeHubModelService {

    private RecipeHubModel recipeHubModel;
    /* private RecipeFilehandler recipeFilehandler;
    private UserFilehandler userFilehandler; */

    public RecipeHubModelService() {
      this(createDefaultRecipeHubModel());
    }

    public RecipeHubModelService(RecipeHubModel recipeHubModel) {
        this.recipeHubModel = recipeHubModel;
        /* this.recipeFilehandler = new RecipeFilehandler("springbootserver-recipes.json");
        this.userFilehandler = new UserFilehandler("springbootserver-recipes.json"); */
    }

    public RecipeHubModel getRecipeHubModel() {
      return recipeHubModel;
    }

    public void setRecipeHubModel(RecipeHubModel recipeHubModel) {
        this.recipeHubModel = recipeHubModel;
    }

    private static RecipeHubModel createDefaultRecipeHubModel() {
        /* RecipeFilehandler recipeFilehandler = new RecipeFilehandler("springbootserver-recipes.json");
        URL url = RecipeHubModelService.class.getResource("default-recipeHubModel.json");
        if (url != null) {
          try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
            List<Profile> profiles = userFilehandler.readProfiles();
            return new RecipeHubModel(recipeLibrary, profiles);
          } catch (IOException e) {
            System.out.println("Couldn't read default-recipeHubModel.json, so rigging recipeHubModel manually ("
                + e + ")");
          }
        } */

        RecipeHubModel recipeHubModel = new RecipeHubModel();
        Profile p1 = new Profile("Username1", "Password1");
        Profile p2 = new Profile("Username2", "Password2");
        Recipe recipe1 = new Recipe("recipe1", 2, p1);
        Recipe recipe2 = new Recipe("recipe2", 2, p2);
        recipeHubModel.addRecipe(recipe1);
        recipeHubModel.addRecipe(recipe2);
        recipeHubModel.addProfile(p1);
        recipeHubModel.addProfile(p2);
        return recipeHubModel;
      }

      /* public void autoSaveRecipeLibrary() {
        if (recipeFilehandler != null) {
          recipeFilehandler.writeRecipeLibrary(recipeHubModel);
        }
      } */
}
