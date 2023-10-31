package ui;

import java.util.Hashtable;
import java.util.List;

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;
import core.RecipeLibrary;
import file.RecipeFilehandler;
import file.UserFilehandler;

public class DirectRecipeHubModelAccess implements RecipeHubModelAccess {

    private RecipeHubModel recipeHubModel;
    private UserFilehandler userFilehandler;
    private RecipeFilehandler recipeFilehandler;

    public DirectRecipeHubModelAccess() {
        userFilehandler = new UserFilehandler("userInfo.json");
        recipeFilehandler = new RecipeFilehandler("recipes.json");
        recipeHubModel = new RecipeHubModel(
                recipeFilehandler.readRecipeLibrary(), userFilehandler.readProfiles());
    }

    public DirectRecipeHubModelAccess(UserFilehandler userFilehandler, RecipeFilehandler recipeFilehandler) {
        this.userFilehandler = userFilehandler;
        this.recipeFilehandler = recipeFilehandler;
        recipeHubModel = new RecipeHubModel(
                recipeFilehandler.readRecipeLibrary(), userFilehandler.readProfiles());
    }

    @Override
    public RecipeLibrary getRecipeLibrary() {
        RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
        return recipeLibrary;
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipeFilehandler.removeRecipe(recipe);
        recipeHubModel.removeRecipe(recipe);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeHubModel.addRecipe(recipe);
    }

    @Override
    public void saveProfile(Profile profile) {
        recipeHubModel.putProfile(profile);
    }

    @Override
    public List<Profile> getProfiles() {
        return recipeHubModel.getProfiles();
    }

    @Override
    public Hashtable<String, String> getUserInfo() {
        return userFilehandler.readUsernamesAndPasswords();
    }

    @Override
    public void saveProfiles(List<Profile> profiles) {
        userFilehandler.writeAllProfiles(profiles);
    }
}
