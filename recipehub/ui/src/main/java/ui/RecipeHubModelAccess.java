package ui;

import java.util.Hashtable;
import java.util.List;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;

public interface RecipeHubModelAccess {
    Recipe getRecipe(String name);

    RecipeLibrary getRecipeLibrary();

    void removeRecipe(Recipe recipe);

    void addRecipe(Recipe recipe);

    boolean containsRecipe(Recipe recipe);

    Profile getProfile(String username);

    void addProfile(Profile profile);

    boolean containsProfile(Profile profile);

    List<Profile> getProfiles();

    Hashtable<String, String> getUserInfo();

    void writeProfiles(List<Profile> profiles);

    void writeProfile(Profile profile);

    void writeRecipe(Recipe recipe);
}
