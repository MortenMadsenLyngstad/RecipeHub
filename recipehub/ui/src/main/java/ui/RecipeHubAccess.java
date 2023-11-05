package ui;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for centralizing access to data.
 * Makes it easier to support transparent use of a REST API
 */
public interface RecipeHubAccess {

    RecipeLibrary getRecipeLibrary();

    void removeRecipe(Recipe recipe);

    void saveRecipe(Recipe recipe);

    void saveProfile(Profile profile);

    List<Profile> getProfiles();

    Profile loadProfile(Predicate<Profile> predicate);

    void saveProfiles(List<Profile> profiles);
}
