package file;

import core.Recipe;
import core.RecipeLibrary;
import java.nio.file.Path;

/**
 * This class handles file operations for recipes.
 */
public class RecipeFilehandler {
    private static String fileName = "recipes.json";

    /**
     * This constructor initializes the filePath.
     */
    public RecipeFilehandler() {
        FileUtil.createFile(getFilePath());
    }

    /**
     * This method writes a recipe to the file.
     * 
     * @param recipe - Recipe object to write
     * @return - Returns true if the recipe was written, false if null or not written
     */
    public boolean writeRecipe(Recipe recipe) {
        if (recipe == null) {
            return false;
        }
        RecipeLibrary recipeLibrary = readRecipeLibrary();
        recipeLibrary.putRecipe(recipe);
        return FileUtil.writeFile(getFilePath(), recipeLibrary);
    }

    /**
     * This method reads recipes from the file.
     * 
     * @return - Returns a RecipeLibrary object
     */
    public RecipeLibrary readRecipeLibrary() {
        RecipeLibrary recipeLibrary = null;
        recipeLibrary = FileUtil.readFile(getFilePath(), recipeLibrary, RecipeLibrary.class);
        if (recipeLibrary == null) {
            return new RecipeLibrary();
        }
        return recipeLibrary;
    }

    /**
     * This method removes a recipe from the file.
     * 
     * @param recipe - Recipe object to remove
     * @return - Returns true if the recipe was removed, false if null or not removed
     */
    public boolean removeRecipe(Recipe recipe) {
        if (recipe == null) {
            return false;
        }
        RecipeLibrary recipeLibrary = readRecipeLibrary();
        recipeLibrary.removeRecipe(recipe);
        return FileUtil.writeFile(getFilePath(), recipeLibrary);
    }

    /**
     * This method sets the filePath.
     * 
     * @param file - File to write to
     */
    public static void setFileName(String file) {
        fileName = file;
        FileUtil.createFile(getFilePath());
    }

    public static String getFileName() {
        return fileName;
    }

    public static Path getFilePath() {
        return Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + fileName);
    }
}
