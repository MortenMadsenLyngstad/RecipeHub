package file;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.util.List;

/**
 * This class centralizes access to the profiles and recipelibrary saved on file.
 * Makes it easier to support transparent use of a REST API.
 */
public class DirectRecipeHubAccess implements RecipeHubAccess {

    private UserFilehandler userFilehandler;
    private RecipeFilehandler recipeFilehandler;

    /**
     * This empty constructor will make default filehandlers for the class to use.
     */
    public DirectRecipeHubAccess() {
        this(new UserFilehandler(), new RecipeFilehandler());
    }

    /**
     * This contructor takes in two filehandlers for the class to use.
     * The filehandler cannot be null
     * 
     * @param userFilehandler - UserFilehandler to use for the profiles
     * @param recipeFilehandler - RecipeFilehandler to use for the recipelibrary/recipes
     */
    public DirectRecipeHubAccess(UserFilehandler userFilehandler,
            RecipeFilehandler recipeFilehandler) {
        if (userFilehandler == null || recipeFilehandler == null) {
            throw new IllegalArgumentException("Filehandlers cannot be null.");
        }
        this.userFilehandler = userFilehandler;
        this.recipeFilehandler = recipeFilehandler;
    }

    /**
     * This method will return a RecipeLibrary containing all recipes.
     * 
     * @return RecipeLibrary with all the recipes for the app
     * @see RecipeFilehandler#readRecipeLibrary()
     */
    @Override
    public RecipeLibrary getRecipeLibrary() {
        return recipeFilehandler.readRecipeLibrary();
    }

    /**
     * This method will remove the given recipe from the saved recipes.
     * 
     * @param recipe - Recipe to be removed
     * @see RecipeFilehandler#removeRecipe()
     */
    @Override
    public boolean removeRecipe(Recipe recipe) {
        return recipeFilehandler.removeRecipe(recipe);
    }

    /**
     * This method will save the given reecipe to file.
     * 
     * @param recipe - Recipe to be saved
     * @see RecipeFilehandler#writeRecipe(Recipe)
     */
    @Override
    public boolean saveRecipe(Recipe recipe) {
        return recipeFilehandler.writeRecipe(recipe);
    }

    /**
     * This method will save the given profile to file.
     * 
     * @param profile - Profile to be saved
     * @see UserFilehandler#writeProfile(Profile)
     */
    @Override
    public boolean saveProfile(Profile profile) {
        return userFilehandler.writeProfile(profile);
    }

    /**
     * This mehtod will return a list containing all profiles .
     * 
     * @return List with all profiles for the app
     * @see UserFilehandler#readProfiles()
     */
    @Override
    public List<Profile> getProfiles() {
        return userFilehandler.readProfiles();
    }

    /**
     * This method will load the first profile which matches the username, or null if not found.
     * 
     * @param username - Username of the profile to load
     * @return First profile to match the predicate
     * @see UserFilehandler#loadProfile()
     */
    @Override
    public Profile loadProfile(String username) {
        return userFilehandler.loadProfile(username);
    }

    /**
     * Saves the given list of profiles to file.
     * 
     * @param profiles - List of profiles to be saved
     * @see UserFilehandler#writeAllProfiles(List)
     */
    @Override
    public boolean saveProfiles(List<Profile> profiles) {
        return userFilehandler.writeAllProfiles(profiles);
    }
}
