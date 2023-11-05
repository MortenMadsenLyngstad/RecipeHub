package core;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the RecipeLibrary class.
 */
public class RecipeLibraryTest {

    private RecipeLibrary recipeLibrary;
    private Profile profile;
    private ArrayList<Recipe> recipeList;
    private Recipe r1;
    private Recipe r2;
    private Recipe r3;
    private Recipe r4;

    /**
     * This method is run before each test.
     * It sets up the RecipeLibrary and the recipes used in the tests.
     */
    @BeforeEach
    public void setUp() {
        recipeLibrary = new RecipeLibrary();
        profile = new Profile("Username123", "Password123");
        r1 = new Recipe("Lasagne", 5, profile);
        r2 = new Recipe("Cookies", 4, profile);
        r3 = new Recipe("Fish and chips", 1, profile);
        r4 = new Recipe("Kebab", 2, profile);
        recipeList = new ArrayList<>(List.of(r1, r2, r3, r4));
    }

    /**
     * This method tests if the contructor with no parameters work.
     */
    @Test
    @DisplayName("Empty Contructor Test")
    public void testEmptyContructor() {
        // Checks if the contructor works when there are no parameters
        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The RecipeLibrary should be empty");
    }

    /**
     * This method tests if the contructor with a parameter works.
     */
    @Test
    @DisplayName("Non-Empty Contructor test")
    public void testNonEmptyontructor() {
        // Check that constructor with null as argument throws exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecipeLibrary(null));

        // Checks if the contructor works properly
        RecipeLibrary recipeLibrary = new RecipeLibrary(recipeList);
        Assertions.assertEquals(4, recipeLibrary.getSize(), "The RecipeLibrary should have size 4");
        Assertions.assertTrue(recipeLibrary.getRecipe(0) == r1, "The first recipe should be r1");

        // Checks if the recipeLibrary is independent from original list
        recipeList.clear();
        Assertions.assertTrue(recipeLibrary.getSize() != recipeList.size(),
                "The RecipeLibrary should not be affected by changes in the original arraylist");
    }

    /**
     * This metod tests if getSize() and getRecipe(n) works.
     */
    @Test
    @DisplayName("Getters test")
    public void testGetters() {
        // Checks if the geetters works for an empty RecipeLibrary
        RecipeLibrary rl1 = new RecipeLibrary();
        Assertions.assertEquals(0, rl1.getSize(), "The size should be 0");
        Assertions.assertThrows(IllegalArgumentException.class, () -> rl1.getRecipe(0),
                "Should not be able to ask for element which does not exist");

        RecipeLibrary rl2 = new RecipeLibrary(recipeList);

        // Checks if the geetters return correct values
        Assertions.assertTrue(rl2.getSize() == 4, "The RecipeLibrary should have size 4");
        Assertions.assertTrue(rl2.getRecipe(0) == r1, "The first recipe should be r1");

        // Checks that getRecipe(n) does not accept invalid indexes
        Assertions.assertThrows(IllegalArgumentException.class, () -> rl2.getRecipe(-1),
                "Should not be able to ask for recipe with negative index");
        Assertions.assertThrows(IllegalArgumentException.class, () -> rl2.getRecipe(4),
                "Should not be able to ask for recipe with too high index");
    }

    /**
     * This method tests if manipulation of the RecipeLibrary works.
     * This includes testing of both putRecipe(Recipe) and removeRecipe(Recipe)
     */
    @Test
    @DisplayName("Manipulation test")
    public void testManipulation() {
        // Checks if putRecipe(Recipe) and removeRecipe(Recipe) works properly
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.removeRecipe(r1),
                "Should throw exceptiopn when trying to remove recipe from empty RecipeLibrary");
        recipeLibrary.putRecipe(r1);
        Assertions.assertTrue(recipeLibrary.getSize() == 1, "The size should be 1");
        Assertions.assertEquals(r1, recipeLibrary.getRecipe(0),
                "The added recipe should be the first recipe in the recipeLibrary");
        recipeLibrary.putRecipe(r1);
        Assertions.assertTrue(recipeLibrary.getSize() == 1,
                "The size should not change when updating a recipe");
        recipeLibrary.putRecipe(r3);
        recipeLibrary.putRecipe(r4);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.removeRecipe(r2),
                "Should not be able to remove a recipe not in the recipeLibrary");
        recipeLibrary.removeRecipe(r3);
        recipeLibrary.removeRecipe(r4);

        // Adds a recipe with same name but different author
        Profile profile2 = new Profile("Profile2", "Password2");
        Recipe r5 = new Recipe("Lasagne", 5, profile2);
        recipeLibrary.putRecipe(r5);
        recipeLibrary.removeRecipe(r1);
        recipeLibrary.removeRecipe(r5);
        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The size should be 0");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.putRecipe(null),
                "Should not be able to add null");
        recipeLibrary.putRecipe(r5);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.removeRecipe(new Recipe("Pizza",
                        1, profile2)),
                        "Throw exception when recipe is different but profile is the same");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.removeRecipe(new Recipe("Lasagne",
                        1, new Profile("Profile3", "Password3"))),
                        "Throw exception when recipe and profile are different");
    }

    /**
     * This method tests if the iterator given from iterator() works properly.
     */
    @Test
    @DisplayName("Iterator test")
    public void testIterator() {
        RecipeLibrary newRecipeLibrary = new RecipeLibrary();
        Assertions.assertFalse(newRecipeLibrary.iterator().hasNext(),
                "An empty RecipeLibrary should not have a next recipe");
        recipeLibrary.putRecipe(r1);
        Assertions.assertTrue(recipeLibrary.iterator().hasNext(),
                "The RecipeLibrary should have a next recipe");
        Assertions.assertEquals(r1, recipeLibrary.iterator().next(),
                "The next recipe from the iterator should be r1");
    }

    @Test
    @DisplayName("setRecipeLibrary test")
    public void setRecipeLibraryTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.setRecipeLibrary(null),
                "Should not be able to set recipeLibrary to null");

        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The size should be 0");
        recipeLibrary.setRecipeLibrary(recipeList);
        Assertions.assertTrue(recipeLibrary.getSize() == 4, "The size should be 0");
    }

    @Test
    @DisplayName("Test getRecipes")
    public void getRecipesTest() {
        Assertions.assertTrue(recipeLibrary.getRecipes().isEmpty(),
                "The RecipeLibrary should be empty");

        recipeLibrary.setRecipeLibrary(recipeList);
        Assertions.assertTrue(recipeLibrary.getRecipes().size() == 4,
                "The RecipeLibrary should have size 4");
    }
}
