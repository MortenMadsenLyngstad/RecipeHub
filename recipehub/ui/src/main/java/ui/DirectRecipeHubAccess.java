package ui;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.util.List;
import java.util.function.Predicate;

public class DirectRecipeHubAccess implements RecipeHubAccess {

    private UserFilehandler userFilehandler;
    private RecipeFilehandler recipeFilehandler;

    public DirectRecipeHubAccess() {
        userFilehandler = new UserFilehandler("userInfo.json");
        recipeFilehandler = new RecipeFilehandler("recipes.json");
    }

    public DirectRecipeHubAccess(UserFilehandler userFilehandler,
            RecipeFilehandler recipeFilehandler) {
        this.userFilehandler = userFilehandler;
        this.recipeFilehandler = recipeFilehandler;
    }

    @Override
    public RecipeLibrary getRecipeLibrary() {
        return recipeFilehandler.readRecipeLibrary();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipeFilehandler.removeRecipe(recipe);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeFilehandler.writeRecipe(recipe);
    }

    @Override
    public void saveProfile(Profile profile) {
        userFilehandler.writeProfile(profile);
    }

    @Override
    public List<Profile> getProfiles() {
        return userFilehandler.readProfiles();
    }

    @Override
    public Profile loadProfile(Predicate<Profile> predicate) {
        return userFilehandler.loadProfile(predicate);
    }

    @Override
    public void saveProfiles(List<Profile> profiles) {
        userFilehandler.writeAllProfiles(profiles);
    }
}
