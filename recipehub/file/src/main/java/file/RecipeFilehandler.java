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
     * This method gets an ID for a recipe to be made.
     * 
     * @return - Returns an ID for a recipe
     */
    public int getNextRecipeId() {
        Path filePath = Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "recipeID.json");
        FileUtil.createFile(filePath);
        Integer returnValue = FileUtil.readFile(filePath, 0, Integer.class);
        FileUtil.writeFile(filePath, returnValue + 1);
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
