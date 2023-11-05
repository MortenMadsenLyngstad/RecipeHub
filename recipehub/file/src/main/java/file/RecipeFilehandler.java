package file;

import core.Recipe;
import core.RecipeLibrary;
import java.nio.file.Path;

/**
 * This class handles file operations for recipes.
 */
public class RecipeFilehandler {
    private Path filePath;

    /**
     * This constructor initializes the filePath.
     * 
     * @param file - File to write to
     */
    public RecipeFilehandler(String file) {
        this.filePath = Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + file);
        FileUtil.createFile(this.filePath);
    }

    /**
     * This method writes a recipe to the file.
     * 
     * @param recipe - Recipe object to write
     */
    public void writeRecipe(Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary();            
        recipeLibrary.putRecipe(recipe);
        FileUtil.writeFile(filePath, recipeLibrary);
    }

    /**
     * This method reads recipes from the file.
     * 
     * @return - Returns a RecipeLibrary object
     */
    public RecipeLibrary readRecipeLibrary() {
        RecipeLibrary recipeLibrary = null;
        recipeLibrary = FileUtil.readFile(filePath, recipeLibrary, RecipeLibrary.class);
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
        FileUtil.writeFile(filePath, recipeLibrary);
    }
}
