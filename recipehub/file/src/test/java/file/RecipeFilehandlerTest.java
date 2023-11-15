package file;

import core.PasswordHasher;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
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
     * This method is a helper method that run before each test.
     * This method uses creates a RecipeFilehandler at  which uses createFile from
     * FileUtil
     * createFile is tested in FileUtilTest
     * 
     * @see RecipeFilehandler#setFileName(String)
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
     * Helper method to delete testfiles from the home repository.
     */
    private void deleteFile(String filename) {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve(filename));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests if the recipe is wrting to and reading from file correctly.
     * 
     * @see RecipeFilehandler#writeRecipe(Recipe)
     * @see RecipeFilehandler#readRecipeLibrary()
     */
    @Test
    @DisplayName("Test if correct info is written to and read from file")
    public void testWriteAndReadRecipe() {
        Assertions.assertFalse(recipeFilehandler.writeRecipe(null));
        recipeFilehandler.writeRecipe(this.recipe);
        RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Recipe loadedRecipe = recipeLibrary.getRecipe(0);
        Assertions.assertEquals(this.recipe.getName(), loadedRecipe.getName(),
                "The file should contain the recipeName \"testRecipe\".");
        Assertions.assertEquals(this.recipe.getPortions(), loadedRecipe.getPortions(),
                "The recipe should have 1 portion.");
        Assertions.assertEquals(this.recipe.getAuthor(), loadedRecipe.getAuthor(),
                "The recipe should have the author \"testUser\".");
    }

    /**
     * This method test if setfilename throws ecxeption when filename is empty string.
     * 
     * @see RecipeFilehandler#setFileName(Recipe)
     */
    @Test
    @DisplayName("Test if exception is thrown when filename is an empty string")
    public void testEmptyFileName() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> RecipeFilehandler.setFileName(""), "When filename is empty, "
                        + "IllegalArgumentException should be thrown.");
    }

    /**
     * Tests if the recipe is removed from file correctly.
     * 
     * @see RecipeFilehandler#removeRecipe(Recipe)
     */
    @Test
    @DisplayName("Test if remove recipe works")
    public void testRemoveRecipe() {
        Assertions.assertFalse(recipeFilehandler.removeRecipe(null));
        recipeFilehandler.writeRecipe(this.recipe);
        RecipeLibrary recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Assertions.assertEquals(1, recipeLibrary.getSize(), "The recipe should be added.");
        recipeFilehandler.removeRecipe(this.recipe);
        recipeLibrary = recipeFilehandler.readRecipeLibrary();
        Assertions.assertEquals(0, recipeLibrary.getSize(), "The recipe should be removed.");
    }

    /**
     * This method tests if the getters and setters work properly.
     * 
     * @see RecipeFilehandler#setFileName(String)
     * @see RecipeFilehandler#getFileName()
     */
    @Test
    @DisplayName("Test getFileName")
    public void testGettersAndSetters() {
        Assertions.assertEquals("test.json", RecipeFilehandler.getFileName(),
                "The file should be named \"test.json\".");
        Assertions.assertEquals(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "test.json"),
                RecipeFilehandler.getFilePath(),
                "The file should be in the home directory.");
        RecipeFilehandler.setFileName("newtest.json");
        Assertions.assertEquals("newtest.json", RecipeFilehandler.getFileName(),
                "The file should be named \"newtest.json\".");
        Assertions.assertEquals(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "newtest.json"),
                RecipeFilehandler.getFilePath(),
                "The file should be in the home directory.");
    }

    /**
     * This method is run after each test.
     * It deletes test.json and newtest.json if it exists.
     * 
     * @see RecipeFilehandler#setFileName(String)
     * @see RecipeFilehandler#getFileName()
     */
    @AfterEach
    public void cleanUp() {
        deleteFile("test.json");
        if (Files.exists(Path.of(System.getProperty("user.home")
                + System.getProperty("file.separator") + "newtest.json"))) {
            deleteFile("newtest.json");
        }
    }
}
