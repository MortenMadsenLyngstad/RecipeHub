package file;

import core.PasswordHasher;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This is a test class for RecipeFilehandler.
 */
public class RecipeFilehandlerTest {
    private RecipeFilehandler recipeFilehandler;
    private Recipe recipe;

    /**
     * This method is run before each test.
     * This method uses the RecipeFilehandler constructor which uses createFile from
     * FileUtil
     * createFile is tested in FileUtilTest
     */
    @BeforeEach
    public void setup() {
        Profile profile = new Profile("testUser", "Password123");
        profile.setHashedPassword(PasswordHasher.hashPassword(profile.getPassword()));
        this.recipe = new Recipe("testRecipe", 1, profile);
        this.recipeFilehandler = new RecipeFilehandler();
        RecipeFilehandler.setFileName("test.json");
    }

    /**
     * Helper method to delete testfiles.
     */
    private void deleteFile() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("test.json"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests if the recipe is written to and read from file correctly.
     */
    @Test
    @DisplayName("Test if correct info is written to and read from file")
    public void testWriteAndReadRecipe() {
        recipeFilehandler.writeRecipe(this.recipe);
        RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Recipe loadedRecipe = recipeLibrary.getRecipe(0);
        Assertions.assertEquals(this.recipe.getName(), loadedRecipe.getName(),
                "The file should contain the recipeName \"testRecipe\".");
        Assertions.assertEquals(this.recipe.getPortions(), loadedRecipe.getPortions(),
                "The recipe should have 1 portion.");
        Assertions.assertEquals(this.recipe.getAuthor(), loadedRecipe.getAuthor(),
                "The recipe should have the author \"testUser\".");
        deleteFile();
    }

    /**
     * Tests if the recipe is removed from file correctly.
     */
    @Test
    @DisplayName("Test if remove recipe works")
    public void testRemoveRecipe() {
        recipeFilehandler.writeRecipe(this.recipe);
        RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Assertions.assertEquals(1, recipeLibrary.getSize(), "The recipe should be added.");
        recipeFilehandler.removeRecipe(this.recipe);
        recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Assertions.assertEquals(0, recipeLibrary.getSize(), "The recipe should be removed.");
        deleteFile();
    }
}
