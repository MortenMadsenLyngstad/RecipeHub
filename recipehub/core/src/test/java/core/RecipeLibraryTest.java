package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the RecipeLibrary class.
 */
public class RecipeLibraryTest {

    private RecipeLibrary recipeLibrary;
    private Profile profile;
    private ArrayList<Recipe> recipeList;
    private Recipe recipe1;

    /**
     * This method is run before each test.
     * It sets up the RecipeLibrary and the recipes used in the tests.
     */
    @BeforeEach
    public void setUp() {
        recipeLibrary = new RecipeLibrary();
        profile = new Profile("Username123", "Password123");
        recipe1 = new Recipe("Lasagne", 5, profile);
        Recipe recipe2 = new Recipe("Cookies", 4, profile);
        Recipe recipe3 = new Recipe("Fish and chips", 1, profile);
        Recipe recipe4 = new Recipe("Kebab", 2, profile);
        recipeList = new ArrayList<>(List.of(recipe1, recipe2, recipe3, recipe4));
    }

    /**
     * This method tests if the emoty contructor works properly.
     */
    @Test
    @DisplayName("Empty Contructor Test")
    public void testEmptyContructor() {
        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The RecipeLibrary should be empty");
    }

    /**
     * This method tests if the contructor with a parameter works properly.
     */
    @Test
    @DisplayName("Non-Empty Contructor test")
    public void testNonEmptyontructor() {
        // Check that constructor with null as argument throws exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecipeLibrary(null), 
            "Should not be able to send null into the constructor");

        // Checks that the contructor works properly
        RecipeLibrary newRecipeLibrary = new RecipeLibrary(recipeList);
        Assertions.assertEquals(4, newRecipeLibrary.getSize(), 
            "The RecipeLibrary should have size 4");
        Assertions.assertEquals(recipe1, newRecipeLibrary.getRecipe(0), 
            "The first recipe should be recipe1");

        // Checks that the recipeLibrary is independent from original list
        recipeList.clear();
        Assertions.assertTrue(newRecipeLibrary.getSize() != recipeList.size(),
                "The RecipeLibrary should not be affected by changes in the original list");
    }

    /**
     * This metod tests if the getters for size and recipe works properly.
     * 
     * @see RecipeLibrary#getSize()
     * @see RecipeLibrary#getRecipe(int)
     */
    @Test
    @DisplayName("Getters test")
    public void testGetters() {
        // Checks if the geetters works for an empty RecipeLibrary
        Assertions.assertEquals(0, recipeLibrary.getSize(), "The size should be 0");
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.getRecipe(0),
                "Should not be able to ask for element which does not exist");

        recipeLibrary = new RecipeLibrary(recipeList);

        // Checks if the geetters return correct values
        Assertions.assertEquals(4, recipeLibrary.getSize(), "The RecipeLibrary should have size 4");
        Assertions.assertEquals(recipe1, recipeLibrary.getRecipe(0), 
            "The first recipe should be recipe1");

        // Checks that getRecipe(n) does not accept invalid indexes
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.getRecipe(-1),
                "Should not be able to ask for recipe with negative index");
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.getRecipe(4),
                "Should not be able to ask for recipe with too high index");
    }

    /**
     * This method tests if manipulation of the RecipeLibrary works.
     * This includes testing of both putRecipe(Recipe) and removeRecipe(Recipe).
     * 
     * @see RecipeLibrary#putRecipe(Recipe)
     * @see RecipeLibrary#removeRecipe(Recipe)
     */
    @Test
    @DisplayName("Manipulation test")
    public void testManipulation() {
        // Checks if putRecipe(Recipe) and removeRecipe(Recipe) works properly
        Assertions.assertDoesNotThrow(() -> recipeLibrary.removeRecipe(recipe1),
                "Should not throw exceptiopn when trying "
                        + "to remove recipe from empty RecipeLibrary");
        recipeLibrary.putRecipe(recipe1);
        Assertions.assertTrue(recipeLibrary.getSize() == 1, "The size should be 1");
        Assertions.assertEquals(recipe1, recipeLibrary.getRecipe(0),
                "The added recipe should be the first recipe in the recipeLibrary");
        Assertions.assertEquals(0, recipeLibrary.getRecipe(0).getNumberOfReviewers());
        recipe1.addReview(new Review(4, "Very good!", profile.getUsername()));
        recipeLibrary.putRecipe(recipe1);
        Assertions.assertTrue(recipeLibrary.getSize() == 1,
                "The size should not change when updating a recipe");
        Assertions.assertEquals(1, recipeLibrary.getRecipe(0).getNumberOfReviewers(), 
            "The recipe in the RecipeLibrary should now be updated");

        // Adds a recipe with same name but different author
        Profile profile2 = new Profile("Profile2", "Password2");
        Recipe r5 = new Recipe("Lasagne", 5, profile2);
        recipeLibrary.putRecipe(r5);
        Assertions.assertEquals(2, recipeLibrary.getSize(), 
            "The recipe should be added to the RecipeLibrary, making it's size 2");
        Assertions.assertEquals(r5, recipeLibrary.getRecipe(1), 
            "r5 should be the recipe with index 1 in the RecipeLibrary");
        recipeLibrary.removeRecipe(recipe1);
        recipeLibrary.removeRecipe(r5);
        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The size should be 0");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.putRecipe(null),
                "Should not be able to add null to the RecipeLibrary");
        recipeLibrary.putRecipe(r5);
        Assertions.assertEquals(1, recipeLibrary.getSize(), 
            "The recipeLibrary should now have size 1");
        recipeLibrary.removeRecipe(new Recipe("Pizza", 1, profile2));
        Assertions.assertEquals(1, recipeLibrary.getSize(), 
            "No recipe should be removed when recipes share profile, but differ in name");
        recipeLibrary.removeRecipe(new Recipe("Lasagne", 1, new Profile("Profile3", "Password3")));
        Assertions.assertEquals(1, recipeLibrary.getSize(), 
            "No recipe should be removed when recipes share name, but have different authors");
    }

    /**
     * This method tests if the iterator given from iterator() works properly.
     * 
     * @see RecipeLibrary#iterator()
     */
    @Test
    @DisplayName("Iterator test")
    public void testIterator() {
        Assertions.assertFalse(recipeLibrary.iterator().hasNext(),
                "An empty RecipeLibrary should not have a next recipe");
        recipeLibrary.putRecipe(recipe1);
        Iterator<Recipe> iterator = recipeLibrary.iterator();
        Assertions.assertTrue(iterator.hasNext(),
                "The iterator should have a next recipe");
        Assertions.assertEquals(recipe1, iterator.next(),
                "The next recipe from the iterator should be recipe1");
        Assertions.assertFalse(iterator.hasNext(),
                "The iterator should not have another recipe");
    }

    /**
     * This method tests if the setter for RecipeLibrary works properly.
     * 
     * @see RecipeLibrary#setRecipeLibrary(List)
     */
    @Test
    @DisplayName("setRecipeLibrary() test")
    public void testSetRecipeLibrary() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipeLibrary.setRecipeLibrary(null),
                "Should not be able to set recipeLibrary to null");

        Assertions.assertTrue(recipeLibrary.getSize() == 0, "The size should be 0");
        recipeLibrary.setRecipeLibrary(recipeList);
        Assertions.assertTrue(recipeLibrary.getSize() == 4, "The size should be 0");
    }

    /**
     * This method tests if the getRecipes() works properly.
     * 
     * @see RecipeLibrary#getRecipes()
     */
    @Test
    @DisplayName("Test getRecipes")
    public void testGetRecipes() {
        Assertions.assertTrue(recipeLibrary.getRecipes().isEmpty(),
                "The RecipeLibrary should be empty");

        recipeLibrary.setRecipeLibrary(recipeList);
        Assertions.assertTrue(recipeLibrary.getRecipes().size() == 4,
                "The RecipeLibrary should have size 4");

        List<Recipe> readRecipes = recipeLibrary.getRecipes();
        readRecipes.clear();
        Assertions.assertTrue(recipeLibrary.getSize() != readRecipes.size(),
                "The RecipeLibrary should not be affected by changes made to the returnes list");
    }
}
