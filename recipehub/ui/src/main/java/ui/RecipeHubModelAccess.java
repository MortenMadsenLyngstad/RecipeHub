package ui;

import java.util.Hashtable;
import java.util.List;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;

public interface RecipeHubModelAccess {

    RecipeLibrary getRecipeLibrary();

    void removeRecipe(Recipe recipe);

    void saveRecipe(Recipe recipe);

    void saveProfile(Profile profile);

    List<Profile> getProfiles();

    Hashtable<String, String> getUserInfo();

    void saveProfiles(List<Profile> profiles);
}
