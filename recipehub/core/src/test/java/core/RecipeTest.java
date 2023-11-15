package core;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the Recipe class.
 */
public class RecipeTest {

    private Profile profile;
    private Recipe recipe;

    /**
     * This method makes a standard profile and recipe before each test.
     */
    @BeforeEach
    public void setUp() {
        profile = new Profile("User1234", "User12345");
        recipe = new Recipe("Pancakes", 4, profile);
        recipe.setDescription("We're making pancakes for breakfast!");
        recipe.addIngredient("flour", 400.0, "g");
        recipe.addIngredient("milk", 4.0, "dL");
        recipe.addIngredient("eggs", 3.0, "pcs");
        recipe.addIngredient("salt", 4.0, "g");
        recipe.addIngredient("baking powder", 2.0, "g");
        recipe.addStep("Mix all wet ingredients");
        recipe.addStep("Mix in all dry ingredients");
        recipe.addStep("Let sit for 30 minutes, then cook in a pan at medium heat");
    }

    /**
     * This method tests if the contructor works properly.
     */
    @Test
    @DisplayName("Constructor test")
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Recipe("", 4, profile));
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new Recipe("Pancakes", 0, profile));
        Recipe r = new Recipe("Pancakes", 4, profile);
        Assertions.assertEquals("Pancakes", r.getName(), "The recipe name should be 'Pancakes'");
        Assertions.assertEquals(4, r.getPortions(), "The recipe should have 4 portions");
        Assertions.assertEquals("User1234", r.getAuthor(), "The author should be 'User1234'");
        Assertions.assertTrue(profile.getRecipes().containsRecipe(r),
            "The profile should now have the recipe");
        Assertions.assertFalse(r.isSaved(), "isSaved shoul be false by default");
        Assertions.assertEquals(0, r.getIngredients().size(), "Should be an empty list");
        Assertions.assertEquals(0, r.getSteps().size(), "Should be an empty list");
        Assertions.assertEquals(0, r.getReviews().size(), "Should be an empty list");
    }

    /**
     * This method tests the getter and setter for the recipe name.
     * 
     * @see Recipe#getName()
     * @see Recipe#setName(String)
     */
    @Test
    @DisplayName("Name test")
    public void testName() {
        Assertions.assertEquals("Pancakes", recipe.getName(),
            "The recipe name should be 'Pancakes'");
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipe.setName(""),
            "Recipe name should not be able to be empty");
        recipe.setName("Tikka masala");
        Assertions.assertEquals("Tikka masala", recipe.getName(),
            "Recipe anme should now be 'Tikka masala'");
    }

    /**
     * This method tests the getter and setter for the recipe description.
     * 
     * @see Recipe#getDescription()
     * @see Recipe#setDescription(String)
     */
    @Test
    @DisplayName("Description test")
    public void testDescription() {
        Assertions.assertEquals("We're making pancakes for breakfast!", recipe.getDescription(),
            "Should return the description of the recipe");
        recipe.setDescription("Pancakes taste good");
        Assertions.assertEquals("Pancakes taste good", recipe.getDescription(),
            "Should return the new description: 'Pancakes taste good'");
    }

    /**
     * This method tests the getter and setter for the recipe portions.
     * 
     * @see Recipe#getPortions()
     * @see Recipe#setPortions(int)
     */
    @Test
    @DisplayName("Portions test")
    public void testPortions() {
        Assertions.assertEquals(4, recipe.getPortions(), "Recipe should have four portions");
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipe.setPortions(0),
            "Should not be able to set portions to 0");
        recipe.setPortions(5);
        Assertions.assertEquals(5, recipe.getPortions(), "Recipe should now have five protions");
    }

    /**
     * This method tests the adder, remover and getter for the recipe steps.
     * 
     * @see Recipe#addStep(String)
     * @see Recipe#removeStep(String)
     * @see Recipe#getSteps()
     */
    @Test
    @DisplayName("Steps test")
    public void testSteps() {
        Assertions.assertArrayEquals(
            new String[] { "Mix all wet ingredients", "Mix in all dry ingredients",
                "Let sit for 30 minutes, then cook in a pan at medium heat" },
            recipe.getSteps().toArray(), "Should return a list with the steps of the recipe");
        // Checks that you are not able to remove steps which are not in the recipe
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> recipe.removeStep("This step is not in the recipe"),
            "Should not be able to remove step which are not in the recipe");
        // Checks that removing an adding steps work
        recipe.removeStep("Mix all wet ingredients");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
            "Let sit for 30 minutes, then cook in a pan at medium heat" },
            recipe.getSteps().toArray(), "One of the steps should now be removed");
        recipe.addStep("Find some jam to put on");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
            "Let sit for 30 minutes, then cook in a pan at medium heat",
            "Find some jam to put on" },
            recipe.getSteps().toArray(), "A new step should now have been added");
    }

    /**
     * This method tests the adder, remover and getter for the recipe ingredients.
     * 
     * @see Recipe#addIngredient(String, Double, String)
     * @see Recipe#removeIngredient(String)
     * @see Recipe#getIngredients()
     */
    @Test
    @DisplayName("Ingredients test")
    public void testIngredients() {
        // Checks that the ingredients show up in the recipe
        Assertions.assertEquals(new HashSet<>(
                Arrays.asList("flour", "eggs", "milk", "salt", "baking powder")),
                recipe.getIngredients(), "Recipe should contain the given ingredients");
        // Checks that adding ingredients work
        recipe.addIngredient("ham", 200.0, "g");
        Assertions.assertEquals(new HashSet<>(
                Arrays.asList("flour", "ham", "eggs", "milk", "salt", "baking powder")),
                recipe.getIngredients(), "Recipe should now contain a new ingredient");
        // Checks that removing ingredients work
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.removeIngredient("ingredient45"), 
                "Should not be able to remove ingredient which is not in recipe");
        recipe.removeIngredient("flour");
        Assertions.assertEquals(new HashSet<>(
            Arrays.asList("ham", "eggs", "milk", "salt", "baking powder")), 
            recipe.getIngredients(), "'Flour' should now be removed from the ingredients");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.getIngredientAmount("flour"), 
                "Should not be able to remove the same ingredient twice");

    }

    /**
     * This method tests adder, remover and getter for the recipe ingredient amounts.
     * 
     * @see Recipe#addIngredient(String, Double, String)
     * @see Recipe#removeIngredientAmount(String, Double)
     * @see Recipe#getIngredientAmount(String)
     */
    @Test
    @DisplayName("Ingredient amount test")
    public void testIngredientAmount() {
        // Checks that getting the amount of an ingredient works
        Assertions.assertEquals(400.0, recipe.getIngredientAmount("flour"), 
            "Should return the amount of the given ingredient");
        // Checks that removing illegal amounts throws exceptions
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.removeIngredientAmount("ham", 30.0), 
                "Should not be able to remove ingredient which is not in the recipe");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.removeIngredientAmount("flour", 401.0), 
                "Should not be able to remove more of an ingredient than what is in the recipe");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.removeIngredientAmount("flour", -10.0), 
                "Should not be able to remove a negative amount of an ingredient");
        // Checks if adding illegal amount throws exception
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.addIngredient("flour", -10.0, "g"), 
                "Should not be able to add a negative amount of an ingredient");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.addIngredient("flour", 50.0, "dL"), 
                "Should not be able to add an amout with a different unit");
        // Checks that removing and adding amounts of ingredients
        recipe.removeIngredientAmount("flour", 40.0);
        Assertions.assertEquals(360.0, recipe.getIngredientAmount("flour"), 
            "The amount of flour should now be 400-40=360");
        recipe.addIngredient("flour", 50.0, "g");
        Assertions.assertEquals(410.0, recipe.getIngredientAmount("flour"), 
            "The amount should now be 360+50=410");
        recipe.addIngredient("ham", 200.0, "g");
        Assertions.assertEquals(200.0, recipe.getIngredientAmount("ham"), 
            "The amount of ham should now be 200");
    }

    /**
     * This method tests the getter and setter for the recipe ingredient units.
     * 
     * @see Recipe#getIngredientUnit(String)
     * @see Recipe#setIngredientUnit(String, String)
     */
    @Test
    @DisplayName("Ingredient unit test")
    public void testIngredientUnit() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.getIngredientUnit("Ingredient that is not in recipe"), 
                "Should not be able to set ingredient unit for an ingredient not in the recipe");
        Assertions.assertEquals("g", recipe.getIngredientUnit("flour"), 
            "The unit for the flour should be 'g'");
        // Checks that changing units works properly
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.setIngredientUnit("flour", "Non-unit"), 
                "Should not be able to set the unit to anything else than 'g', 'dL' or 'pcs'");
        recipe.setIngredientUnit("flour", "dL");
        recipe.setIngredientUnit("milk", "g");
        recipe.setIngredientUnit("salt", "pcs");
        Assertions.assertEquals("dL", recipe.getIngredientUnit("flour"), 
            "The unit for flour should now be 'dL'");
        Assertions.assertEquals("g", recipe.getIngredientUnit("milk"), 
            "The unit for milk should now be 'g'");
        Assertions.assertEquals("pcs", recipe.getIngredientUnit("salt"), 
            "The unit for salt should now be 'pcs'");
    }

    /**
     * This method tests the getter and setter for whether the recipe is saved.
     * 
     * @see Recipe#isSaved()
     * @see Recipe#setSaved(boolean)
     */
    @Test
    @DisplayName("isSaved test")
    public void testSaved() {
        Assertions.assertFalse(recipe.isSaved(), "The recipe should not be marked as saved");
        recipe.setSaved(true);
        Assertions.assertTrue(recipe.isSaved(), "The recipe should now be marked as saved");
        recipe.setSaved(true);
        Assertions.assertTrue(recipe.isSaved(), 
            "The recipe should still be marked as saved setting the same value twice");
        recipe.setSaved(false);
        Assertions.assertFalse(recipe.isSaved(), "The recipe should now be marked as false");
    }

    /**
     * This method tests the adder and getter for the recipe reviews.
     * 
     * @see Recipe#addReview(Review)
     * @see Recipe#getReviews()
     */
    @Test
    @DisplayName("Review test")
    public void testReview() {
        Assertions.assertEquals(0, recipe.getReviews().size(), "Should not be any reviews");
        recipe.addReview(new Review(5, "This is a comment", profile.getUsername()));
        Assertions.assertTrue(recipe.hasRated(profile.getUsername()), 
            "Should return true when the user has rated the recipe");
        Assertions.assertEquals(1, recipe.getReviews().size(), 
            "The recipe should now have one review");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> recipe.addReview(new Review(5, "This is a comment", profile.getUsername())), 
                "The same profile should not be able to give several reviews for the same recipe");
        recipe.addReview(new Review(4, "", "User12345"));
        Assertions.assertEquals(2, recipe.getNumberOfReviewers(), 
            "The recipe should now have two reviewers");
        Assertions.assertEquals(1, recipe.getNumberOfComments(), 
            "The recipe should only have one comments");
        Assertions.assertEquals(4.5, recipe.getAverageRating(), 
            "The average rating for the recipe should be (5+4)/2=4.5");
    }
}
