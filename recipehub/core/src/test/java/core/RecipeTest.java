package core;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the Recipe class.
 */
public class RecipeTest {

    private Profile profile = new Profile("User1234", "User12345");

    /**
     * Method for making standard recipe used in amny of the tests.
     * 
     * @return The finished recipe
     */
    private Recipe makeRecipe() {
        Recipe returnRecipe = new Recipe("Pancakes", 4, profile);
        returnRecipe.setDescription("We're making pancakes for breakfast!");
        returnRecipe.addIngredient("flour", 400.0, "g");
        returnRecipe.addIngredient("milk", 4.0, "dL");
        returnRecipe.addIngredient("eggs", 3.0, "pcs");
        returnRecipe.addIngredient("salt", 4.0, "g");
        returnRecipe.addIngredient("baking powder", 2.0, "g");
        returnRecipe.addStep("Mix all wet ingredients");
        returnRecipe.addStep("Mix in all dry ingredients");
        returnRecipe.addStep("Let sit for 30 minutes, then cook in a pan at medium heat");
        return returnRecipe;
    }

    /**
     * Method to test the constructor.
     */
    @Test
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Recipe("", 4, profile));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Recipe("Pancakes", 0, profile));
        Recipe r = new Recipe("Pancakes", 4, profile);
        Assertions.assertEquals("Pancakes", r.getName());
        Assertions.assertEquals(4, r.getPortions());
    }

    /**
     * Tests setter and getter for name.
     */
    @Test
    public void testName() {
        Recipe r = makeRecipe();
        Assertions.assertEquals("Pancakes", r.getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.setName(""));
        r.setName("Tikka masala");
        Assertions.assertEquals("Tikka masala", r.getName());
    }

    /**
     * Tests setter and getter for description.
     */
    @Test
    public void testDescription() {
        Recipe r = makeRecipe();
        Assertions.assertEquals("We're making pancakes for breakfast!", r.getDescription());
        r.setDescription("Pancakes taste good");
        Assertions.assertEquals("Pancakes taste good", r.getDescription());
    }

    /**
     * Tests setter and getter for portions.
     */
    @Test
    public void testPortions() {
        Recipe r = makeRecipe();
        Assertions.assertEquals(4, r.getPortions());
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.setPortions(0));
        r.setPortions(5);
        Assertions.assertEquals(5, r.getPortions());
    }

    /**
     * Tests adder, remover and getter for steps.
     */
    @Test
    public void testSteps() {
        Recipe r = makeRecipe();
        // You should not be able to remove steps which are not in the recipe
        Assertions.assertArrayEquals(
                new String[] { "Mix all wet ingredients", "Mix in all dry ingredients",
                    "Let sit for 30 minutes, then cook in a pan at medium heat" },
                r.getSteps().toArray());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.removeStep("This step is not in the recipe"));
        // Checking that removing an adding steps work
        r.removeStep("Mix all wet ingredients");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
            "Let sit for 30 minutes, then cook in a pan at medium heat" },
            r.getSteps().toArray());
        r.addStep("Find some jam to put on");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
            "Let sit for 30 minutes, then cook in a pan at medium heat",
            "Find some jam to put on" },
            r.getSteps().toArray());
    }

    /**
     * Tests adder, remover and getter for ingredients.
     */
    @Test
    public void testIngredients() {
        Recipe r = makeRecipe();
        // Checking thar added ingredients show up in the recipe
        Assertions.assertTrue(r.getIngredients()
                .equals(new HashSet<>(Arrays.asList(
                        "flour", "eggs", "milk", "salt", "baking powder"))));
        r.addIngredient("ham", 200.0, "g");
        Assertions.assertTrue(r.getIngredients()
                .equals(new HashSet<>(Arrays.asList(
                        "flour", "ham", "eggs", "milk", "salt", "baking powder"))),
                "check2");
        // Testing removeIngredient(String),
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.removeIngredient("ingredient45"));
        r.removeIngredient("flour");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.getIngredientAmount("flour"));
        Assertions.assertTrue(r.getIngredients()
                .equals(new HashSet<>(Arrays.asList(
                        "ham", "eggs", "milk", "salt", "baking powder"))));

    }

    /**
     * Tests adder, remover and getter for ingredientamounts.
     */
    @Test
    public void testIngredientAmount() {
        Recipe r = makeRecipe();
        // Checking that removing illegal amounts throw exceptions
        Assertions.assertEquals(400.0, r.getIngredientAmount("flour"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.removeIngredientAmount("ham", 30.0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.removeIngredientAmount("flour", 401.0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.removeIngredientAmount("flour", -10.0));
        // Checking removing and adding amounts of ingredients
        r.removeIngredientAmount("flour", 40.0);
        Assertions.assertEquals(360.0, r.getIngredientAmount("flour"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.addIngredient("flour", -10.0, "g"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.addIngredient("flour", 50.0, "dL"));
        r.addIngredient("flour", 50.0, "g");
        Assertions.assertEquals(410.0, r.getIngredientAmount("flour"));
        r.addIngredient("ham", 200.0, "g");
        Assertions.assertEquals(200.0, r.getIngredientAmount("ham"));
    }

    /**
     * Tests setter and getter for ingredientunits.
     */
    @Test
    public void testIngredientUnit() {
        Recipe r = makeRecipe();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.getIngredientUnit("Ingredient that is not in recipe"));
        Assertions.assertEquals("g", r.getIngredientUnit("flour"));
        // Checking that changing units for ingredients works in legal and not in
        // illegal cases
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.setIngredientUnit("flour", "Non-unit"));
        r.setIngredientUnit("flour", "dL");
        r.setIngredientUnit("milk", "g");
        r.setIngredientUnit("salt", "pcs");
        Assertions.assertEquals("dL", r.getIngredientUnit("flour"));
        Assertions.assertEquals("g", r.getIngredientUnit("milk"));
        Assertions.assertEquals("pcs", r.getIngredientUnit("salt"));
    }

    /**
     * Tests setter and getter for saved.
     */
    @Test
    public void testSaved() {
        Recipe r = makeRecipe();
        Assertions.assertFalse(r.isSaved());
        r.setSaved(true);
        Assertions.assertTrue(r.isSaved());
        r.setSaved(true);
        Assertions.assertTrue(r.isSaved());
        r.setSaved(false);
        Assertions.assertFalse(r.isSaved());
    }

    /**
     * Tests adder, remover and getter for reviews of a recipe.
     */
    @Test
    public void testReview() {
        Recipe r = makeRecipe();
        Assertions.assertEquals(0, r.getReviews().size());
        r.addReview(new Review(5, "This is a comment", profile.getUsername()));
        Assertions.assertTrue(r.hasRated(profile.getUsername()));
        Assertions.assertEquals(1, r.getReviews().size());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> r.addReview(new Review(5, "This is a comment", profile.getUsername())));
        r.addReview(new Review(4, "", "User12345"));
        Assertions.assertEquals(2, r.getNumberOfReviewers());
        Assertions.assertEquals(1, r.getNumberOfcomments());
        Assertions.assertEquals(4.5, r.getAverageRating());
    }
}
