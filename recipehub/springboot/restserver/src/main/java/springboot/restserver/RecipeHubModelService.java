package springboot.restserver;

import org.springframework.stereotype.Service;

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;

@Service
public class RecipeHubModelService {

    private RecipeHubModel recipeHubModel;

    public RecipeHubModelService() {
        this(createDefaultRecipeHubModel());
    }

    public RecipeHubModelService(RecipeHubModel recipeHubModel) {
        this.recipeHubModel = recipeHubModel;
    }

    public RecipeHubModel getRecipeHubModel() {
        return recipeHubModel;
    }

    public void setRecipeHubModel(RecipeHubModel recipeHubModel) {
        this.recipeHubModel = recipeHubModel;
    }

    private static RecipeHubModel createDefaultRecipeHubModel() {
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
}
