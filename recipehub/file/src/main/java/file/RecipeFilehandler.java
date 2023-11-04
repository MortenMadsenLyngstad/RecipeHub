package file;

import core.Recipe;
import core.RecipeLibrary;
import java.nio.file.Path;

/**
 * This class handles file operations for recipes.
 */
public class RecipeFilehandler {
    private Path recipesFile;
    private Path idFile;

    /**
     * This constructor initializes the filePath.
     * 
     * @param recipesFile - File to write recipes to
     * @param idFile      - File to keep track of recipe ids
     */
    public RecipeFilehandler(String recipesFile, String idFile) {
        this.recipesFile = Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + recipesFile);
        this.idFile = Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + idFile);
        FileUtil.createFile(this.recipesFile);
        FileUtil.createFile(this.idFile);
    }

    /**
     * This method gets an id for a recipe to be made.
     * 
     * @return - Returns an id for a recipe
     */
    public int getNextRecipeId() {
        Integer returnValue = FileUtil.readFile(idFile, 0, Integer.class);
        FileUtil.writeFile(idFile, returnValue + 1);
        return returnValue;
    }

    /**
     * This method writes a recipe to the file.
     * 
     * @param recipe - Recipe object to write
     */
    public void writeRecipe(Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary();

        if (recipeLibrary.getRecipes().stream()
                .anyMatch(r -> r.getName().equals(recipe.getName()))) {
            recipeLibrary.removeRecipe(recipe);
        }

        recipeLibrary.addRecipe(recipe);
        FileUtil.writeFile(recipesFile, recipeLibrary);
    }

    /**
     * This method reads recipes from the file.
     * 
     * @return - Returns a RecipeLibrary object
     */
    public RecipeLibrary readRecipeLibrary() {
        RecipeLibrary recipeLibrary = null;
        recipeLibrary = FileUtil.readFile(recipesFile, recipeLibrary, RecipeLibrary.class);
        if (recipeLibrary == null) {
            return new RecipeLibrary();
        }
        return recipeLibrary;
    }

    /**
     * This method removes a recipe from the file.
     * 
     * @param recipe - Recipe object to remove
     */
    public void removeRecipe(Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary();
        recipeLibrary.removeRecipe(recipe);
        FileUtil.writeFile(recipesFile, recipeLibrary);
    }
}
