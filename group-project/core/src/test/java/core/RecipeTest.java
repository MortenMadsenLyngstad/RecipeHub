package core;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecipeTest {
    private Profile p = new Profile("User1234", "User12345");

    private Recipe makeRecipe() {
        Recipe returnRecipe = new Recipe("Pancakes", 4);
        returnRecipe.setDescription("We're making pancakes for breakfast!");
        returnRecipe.addIngredient("flour", 400.0, "g");
        returnRecipe.addIngredient("milk", 4.0, "dL");
        returnRecipe.addIngredient("eggs", 3.0, "pcs");
        returnRecipe.addIngredient("salt", 4.0, "g");
        returnRecipe.addIngredient("baking powder", 2.0, "g");
        returnRecipe.addStep("Mix all wet ingredients");
        returnRecipe.addStep("Mix in all dry ingredients");
        returnRecipe.addStep("Let sit for 30 minutes, then cook in a pan at medium heat");

        returnRecipe.setAuthor(p);
        return returnRecipe;
    }

    @Test
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Recipe("", 4));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Recipe("Pancakes", 0));
        Recipe r = new Recipe("Pancakes", 4);
        Assertions.assertEquals("Pancakes", r.getName());
        Assertions.assertEquals(4, r.getPortions());
    }

    @Test
    public void testName() {
        Recipe r = makeRecipe();
        Assertions.assertEquals("Pancakes", r.getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.setName(""));
        r.setName("Tikka masala");
        Assertions.assertEquals("Tikka masala", r.getName());
    }

    @Test
    public void testDescription() {
        Recipe r = makeRecipe();
        Assertions.assertEquals("We're making pancakes for breakfast!", r.getDescription());
        r.setDescription("Pancakes taste good");
        Assertions.assertEquals("Pancakes taste good", r.getDescription());
    }

    @Test
    public void testPortions() {
        Recipe r = makeRecipe();
        Assertions.assertEquals(4, r.getPortions());
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.setPortions(0));
        r.setPortions(5);
        Assertions.assertEquals(5, r.getPortions());
    }

    @Test
    public void testSteps() {
        Recipe r = makeRecipe();
        Assertions.assertArrayEquals(new String[] { "Mix all wet ingredients", "Mix in all dry ingredients",
                "Let sit for 30 minutes, then cook in a pan at medium heat" }, r.getSteps().toArray());
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.removeStep("This step is not in the recipe"));
        r.removeStep("Mix all wet ingredients");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
                "Let sit for 30 minutes, then cook in a pan at medium heat" }, r.getSteps().toArray());
        r.addStep("Find some jam to put on");
        Assertions.assertArrayEquals(new String[] { "Mix in all dry ingredients",
                "Let sit for 30 minutes, then cook in a pan at medium heat", "Find some jam to put on" },
                r.getSteps().toArray());
    }

    @Test
    public void testIngredients() {
        Recipe r = makeRecipe();
        Assertions.assertTrue(r.getIngredients()
                .equals(new HashSet<>(Arrays.asList("flour", "eggs", "milk", "salt", "baking powder"))));
        Assertions.assertEquals(400.0, r.getIngredientAmount("flour"));
        Assertions.assertEquals("g", r.getIngredientUnit("flour"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.removeIngredientAmount("ham", 30.0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.removeIngredientAmount("flour", 401.0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.removeIngredientAmount("flour", -10.0));
        r.removeIngredientAmount("flour", 40.0);
        Assertions.assertEquals(360.0, r.getIngredientAmount("flour"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.addIngredient("flour", -10.0, "g"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.addIngredient("flour", 50.0, "dL"));
        r.addIngredient("flour", 50.0, "g");
        Assertions.assertEquals(410.0, r.getIngredientAmount("flour"));
        r.addIngredient("ham", 200.0, "g");
        Assertions.assertEquals(200.0, r.getIngredientAmount("ham"));
        Assertions.assertEquals("g", r.getIngredientUnit("ham"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.removeIngredient("ingredient45"));
        r.removeIngredient("flour");
        Assertions.assertThrows(IllegalArgumentException.class, () -> r.getIngredientAmount("flour"));
        Assertions.assertTrue(r.getIngredients()
                .equals(new HashSet<>(Arrays.asList("ham", "eggs", "milk", "salt", "baking powder"))));
    }

    @Test
    public void testAuthor() {
        Recipe r = makeRecipe();
        Assertions.assertTrue(p.equals(r.getAuthor()));
        Profile p2 = new Profile("Alphabet23", "Alphabet24");
        r.setAuthor(p2);
        Assertions.assertTrue(p2.equals(r.getAuthor()));
    }
}
