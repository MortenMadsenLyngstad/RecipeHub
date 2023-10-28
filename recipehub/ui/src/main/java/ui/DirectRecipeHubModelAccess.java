package ui;

import java.util.Hashtable;
import java.util.List;

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;
import core.RecipeLibrary;
import file.RecipeFilehandler;
import file.UserFilehandler;

public class DirectRecipeHubModelAccess implements RecipeHubModelAccess{

    private RecipeHubModel recipeHubModel;
    private UserFilehandler userFilehandler = new UserFilehandler("userInfo.json");
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");

    public DirectRecipeHubModelAccess(RecipeHubModel recipeHubModel) {
        this.recipeHubModel = recipeHubModel;
    }

    @Override
    public Recipe getRecipe(String name) {
        return recipeHubModel.getRecipe(name);
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
    public void addRecipe(Recipe recipe) {
        recipeHubModel.addRecipe(recipe);
    }

    @Override
    public boolean containsRecipe(Recipe recipe) {
        return recipeHubModel.containsRecipe(recipe);
    }

    @Override
    public Profile getProfile(String username) {
        return recipeHubModel.getProfile(username);
    }

    @Override
    public void addProfile(Profile profile) {
        recipeHubModel.addProfile(profile);
    }

    @Override
    public boolean containsProfile(Profile profile) {
        return recipeHubModel.containsProfile(profile);
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
    public void writeProfiles(List<Profile> profiles) {
        userFilehandler.writeAllProfiles(profiles);
    }

    @Override
    public void writeProfile(Profile profile) {
        userFilehandler.writeProfile(profile);
    }

    @Override
    public void writeRecipe(Recipe recipe) {
        recipeFilehandler.writeRecipe(recipe);
        addRecipe(recipe);
    }
}
