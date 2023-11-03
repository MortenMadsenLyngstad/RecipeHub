package core;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeLibraryTest {

        /**
         * This method tests if the contructor with no parameters work
         */
        @Test
        @DisplayName("Empty Contructor Test")
        public void testEmptyContructor() {
                // Checks if the contructor works when there are no parameters
                RecipeLibrary recipeLibrary = new RecipeLibrary();
                Assertions.assertTrue(recipeLibrary.getSize() == 0, "The RecipeLibrary should be empty");
        }

        /**
         * This method tests if the contructor with a parameter works
         */
        @Test
        @DisplayName("Non-Empty Contructor test")
        public void testNonEmptyontructor() {
                // Check that constructor with null as argument throws exception
                Assertions.assertThrows(IllegalArgumentException.class, () -> new RecipeLibrary(null));

                // Makes an ArrayList filled with recipes
                Profile profile = new Profile("Username123", "Password123");
                Recipe r1 = new Recipe("Lasagne", 5, profile, 1);
                Recipe r2 = new Recipe("Cookies", 4, profile, 2);
                Recipe r3 = new Recipe("Fish and chips", 1, profile, 3);
                Recipe r4 = new Recipe("Kebab", 2, profile, 4);
                ArrayList<Recipe> recipeList = new ArrayList<>(List.of(r1, r2, r3, r4));

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
         * This metod tests if getSize() and getRecipe(n) works
         */
        @Test
        @DisplayName("Getters test")
        public void testGetters() {
                // Checks if the geetters works for an empty RecipeLibrary
                RecipeLibrary rl1 = new RecipeLibrary();
                Assertions.assertEquals(0, rl1.getSize(), "The size should be 0");
                Assertions.assertThrows(IllegalArgumentException.class, () -> rl1.getRecipe(0),
                                "Should not be able to ask for element which does not exist");

                // Makes a non-empty RecipeLibrary
                Profile profile = new Profile("Username123", "Password123");
                Recipe r1 = new Recipe("Lasagne", 5, profile, 1);
                Recipe r2 = new Recipe("Cookies", 4, profile, 2);
                Recipe r3 = new Recipe("Fish and chips", 1, profile, 3);
                Recipe r4 = new Recipe("Kebab", 2, profile, 4);
                ArrayList<Recipe> recipeList = new ArrayList<>(List.of(r1, r2, r3, r4));
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
         * This method tests if manipulation of the RecipeLibrary works
         * This includes testing of both addRecipe(Recipe) and removeRecipe(Recipe)
         */
        @Test
        @DisplayName("Manipulation test")
        public void testManipulation() {
                // Set up for test
                RecipeLibrary recipeLibrary = new RecipeLibrary();
                Profile profile1 = new Profile("Username123", "Password123");
                Profile profile2 = new Profile("Username1234", "Password1234");
                Recipe r1 = new Recipe("Cheesecake", 4, profile1, 1);
                Recipe r2 = new Recipe("Cheesecake", 4, profile2, 2);
                Recipe r3 = new Recipe("Hamburger", 4, profile1, 3);
                Recipe r4 = new Recipe("Hamburger", 4, profile2, 4);

                // Checks if addRecipe(Recipe) and removeRecipe(Recipe) works properly
                Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.removeRecipe(r1),
                                "Should throw exceptiopn when trying to remove recipe from empty RecipeLibrary");
                recipeLibrary.addRecipe(r1);
                Assertions.assertTrue(recipeLibrary.getSize() == 1, "The size should be 1");
                Assertions.assertEquals(r1, recipeLibrary.getRecipe(0),
                                "The added recipe should be the first recipe in the RecipeLibrary");
                Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.addRecipe(r1),
                                "Should not be able to add the same recipe twice");
                recipeLibrary.addRecipe(r3);
                recipeLibrary.addRecipe(r4);
                Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.removeRecipe(r2),
                                "Should not be able to remove a recipe not in the recipeLibrary");
                recipeLibrary.removeRecipe(r3);
                recipeLibrary.removeRecipe(r4);
                recipeLibrary.removeRecipe(r1);
                Assertions.assertTrue(recipeLibrary.getSize() == 0, "The size should be 0");
                Assertions.assertThrows(IllegalArgumentException.class, () -> recipeLibrary.addRecipe(null),
                                "Should not be able to add null");
        }

        /**
         * This method tests if the iterator given from iterator() works properly
         */
        @Test
        @DisplayName("Iterator test")
        public void testIterator() {
                RecipeLibrary recipeLibrary = new RecipeLibrary();
                Assertions.assertFalse(recipeLibrary.iterator().hasNext(),
                                "An empty RecipeLibrary should not have a next recipe");

                Profile profile = new Profile("Username123", "Password123");
                Recipe r1 = new Recipe("Cheesecake", 4, profile, 1);
                recipeLibrary.addRecipe(r1);

                Assertions.assertTrue(recipeLibrary.iterator().hasNext(),
                                "The RecipeLibrary should have a next recipe");
                Assertions.assertEquals(r1, recipeLibrary.iterator().next(),
                                "The next recipe from the iterator should be r1");
        }

        /**
         * Tests for the getFilteredRecipes() method
         */
        @Test
        public void getFilteredRecipesTest() {
                // Makes a non-empty RecipeLibrary
                Profile profile = new Profile("Username123", "Password123");
                Recipe r1 = new Recipe("Lasagne", 5, profile, 1);
                Recipe r2 = new Recipe("Cookies", 4, profile, 2);
                Recipe r3 = new Recipe("Fish and chips", 1, profile, 3);
                Recipe r4 = new Recipe("Kebab", 2, profile, 4);
                ArrayList<Recipe> recipeList = new ArrayList<>(List.of(r1, r2, r3, r4));
                RecipeLibrary rl2 = new RecipeLibrary(recipeList);

                // Checks when input is empty
                List<Recipe> filteredRecipes = rl2.getFilteredRecipes(List.of());
                Assertions.assertEquals(new ArrayList<>(), filteredRecipes);

                // Checks when input is not empty
                List<Recipe> filteredRecipes2 = rl2.getFilteredRecipes(List.of(1, 2));
                Assertions.assertEquals(new ArrayList<>(List.of(r1, r2)), filteredRecipes2);

                // Checks when input is the whole RecipeLibrary
                List<Recipe> filteredRecipes3 = rl2.getFilteredRecipes(List.of(1, 2, 3, 4));
                Assertions.assertEquals(new ArrayList<>(List.of(r1, r2, r3, r4)), filteredRecipes3);

                // Checks when input is not in the RecipeLibrary
                List<Recipe> filteredRecipes4 = rl2.getFilteredRecipes(List.of(5));
                Assertions.assertEquals(new ArrayList<>(), filteredRecipes4);
        }

}
