package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;

public class AddRecipeFilehandlerTest {
    private AddRecipeFilehandler addRecipeFilehandler;
    private Recipe recipe;


    @BeforeEach
    public void setup() {
        this.recipe = new Recipe("testRecipe", 1, new Profile("testUser", "Password123"));
        this.addRecipeFilehandler = new AddRecipeFilehandler("/testAddedRecipes.ser");
    }

    private void deleteFile() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("addedRecipes.ser"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Test if correct info is written to file")
    public void testSaveRecipe() {
        addRecipeFilehandler.SaveRecipe(this.recipe);
        RecipeLibrary recipeLibrary = addRecipeFilehandler.loadRecipeLibrary();
        Recipe loadedRecipe = recipeLibrary.getRecipe(0);
        Assertions.assertEquals(this.recipe.getName(), loadedRecipe.getName(), "The file should contain the recipeName \"testRecipe\".");
        Assertions.assertEquals(this.recipe.getPortions(), loadedRecipe.getPortions(), "The recipe should have 1 portion.");
        Assertions.assertEquals(this.recipe.getAuthor().getUsername(), loadedRecipe.getAuthor().getUsername(), "The recipe should have the author \"testUser\".");
        deleteFile();
    }
}
