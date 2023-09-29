package file;

import core.Recipe;
import core.RecipeLibrary;
import java.nio.file.Path;

public class RecipeFilehandler extends FileUtil {
    private Path filePath;

    /**
     * This constructor initializes the filePath
     * 
     * @param file
     */
    public RecipeFilehandler(String file) {
        this.filePath = Path.of(System.getProperty("user.home") + System.getProperty("file.separator") + file);
        createFile(this.filePath);
    }

    /**
     * This method writes a recipe to the file
     * 
     * @param recipe - Recipe object to write
     */
    public void writeRecipe(Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary();
        recipeLibrary.addRecipe(recipe);
        writeFile(filePath, recipeLibrary);
    }

    /**
     * This method reads recipes from the file
     * 
     * @return - Returns a RecipeLibrary object
     */
    public RecipeLibrary readRecipeLibrary() {
        RecipeLibrary recipeLibrary = null;
        recipeLibrary = readFile(filePath, recipeLibrary, RecipeLibrary.class);
        if (recipeLibrary == null) {
            return new RecipeLibrary();
        }
        return recipeLibrary;
    }
}
