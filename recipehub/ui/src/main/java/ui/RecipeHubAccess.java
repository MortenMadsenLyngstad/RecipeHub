package ui;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.util.List;

/**
 * Interface for centralizing access to data.
 * Makes it easier to support transparent use of a REST API
 */
public interface RecipeHubAccess {

    RecipeLibrary getRecipeLibrary();

    boolean removeRecipe(Recipe recipe);

    boolean saveRecipe(Recipe recipe);

    boolean saveProfile(Profile profile);

    List<Profile> getProfiles();

    Profile loadProfile(String username);

    boolean saveProfiles(List<Profile> profiles);
}
