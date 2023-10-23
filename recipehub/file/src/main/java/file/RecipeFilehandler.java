package file;

import core.Recipe;
import core.RecipeLibrary;
import java.nio.file.Path;

/**
 * This class handles file operations for recipes.
 */
public class RecipeFilehandler extends FileUtil {

    /**
     * This method writes a recipe to the file.
     * 
     * @param recipe - Recipe object to write
     */
    public static void writeRecipe(Path filePath, Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary(filePath);
        recipeLibrary.addRecipe(recipe);
        FileUtil.writeFile(filePath, recipeLibrary);
    }

    /**
     * This method reads recipes from the file.
     * 
     * @return - Returns a RecipeLibrary object
     */
    public static RecipeLibrary readRecipeLibrary(Path filePath) {
        RecipeLibrary recipeLibrary = null;
        recipeLibrary = readFile(filePath, recipeLibrary, RecipeLibrary.class);
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
    public static void removeRecipe(Path filePath, Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary(filePath);
        recipeLibrary.removeRecipe(recipe);
        FileUtil.writeFile(filePath, recipeLibrary);
    }
}
