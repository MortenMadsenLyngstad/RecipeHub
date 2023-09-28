package file;

import core.Recipe;
import core.RecipeLibrary;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddRecipeFilehandler {
    private Path filePath;
    private final Gson gson;

    // ! This class is very similar to UserFilehandler.java
    // ! Consider refactoring to use a single class or abstract class?

    public AddRecipeFilehandler(String file) {
        this.filePath = Path.of(System.getProperty("user.home") + System.getProperty("file.separator") + file);
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println("Error creating file");
                System.out.println(e.getMessage());
            }
        }
    }

    public void writeRecipe(Recipe recipe) {
        RecipeLibrary recipeLibrary = readRecipeLibrary();

        recipeLibrary.addRecipe(recipe);

        try (Writer writer = new FileWriter(filePath.toFile())) {
            gson.toJson(recipeLibrary, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RecipeLibrary readRecipeLibrary() {
        RecipeLibrary recipeLibrary = null;
        try (Reader reader = new FileReader(filePath.toFile())) {
            recipeLibrary = gson.fromJson(reader, RecipeLibrary.class);
        } catch (IOException e) {
            System.out.println("Error reading from file");
            System.out.println(e.getMessage());
        }
        if (recipeLibrary == null) {
            return new RecipeLibrary();
        }
        return recipeLibrary;
    }
}
