package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the Profile class.
 */
public class ProfileTest {

    private Profile profile;

    /**
     * This method makes a standard profile before each test.
     */
    @BeforeEach
    public void setUp() {
        profile = new Profile("Username123", "Password123");
    }

    /**
     * This method tests if the contructor works properly.
     */
    @Test
    @DisplayName("Contructor test")
    public void testContructor() {
        // Checks that you cant make an invalid profile
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("abc", "abc"),
                "Should not be able to make a profile with an invalid username and password");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Profile("abc", "Password123"),
                "Should not be able to make a profile with an invalid username");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Profile("Username123", "abc"),
                "Should not be able to make a profile with an invalid password");
        Assertions.assertDoesNotThrow(() -> new Profile("ValidUsername123", "ValidPassword123"),
                "Should not throw exception when both the username and the password is valid");

        // Checks if the correct values are stored properly
        Assertions.assertEquals("Username123", profile.getUsername(),
                "The username should be 'Username123'");
        Assertions.assertEquals("Password123", profile.getPassword(),
                "The password should be 'Password123'");
        Assertions.assertNotNull(profile.getRecipes(),
                "The recipes of the profile should not be null");
        Assertions.assertTrue(profile.getRecipes().getSize() == 0,
                "The RecipeLibrary with the profile's recipes should be empty");
        Assertions.assertNotNull(profile.getFavorites(), 
                "The favorites of the profile should not be null");
        Assertions.assertTrue(profile.getFavorites().getSize() == 0,
                "The RecipeLibrary with the profile's favorites should be empty");
    }

    /**
     * This method tests if the username validation works properly.
     * 
     * @see Profile#isValidUsername(String)
     */
    @Test
    @DisplayName("Username validation test")
    public void testIsValidUsername() {
        // Checks if the username validation works
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidUsername("Aa1"),
                "Should throw exception because the username is too short");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidUsername("Username.,-"),
                "Should throw exception because the username contains invalid characters");
        Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidUsername(null),
                "Should throw exception because the username is null");
        Assertions.assertDoesNotThrow(() -> Profile.isValidUsername("Username123"),
                "Should not throw exception when the username is valid");
    }

    /**
     * This method tests if the password validation works properly.
     * 
     * @see Profile#isValidPassword(String)
     */
    @Test
    @DisplayName("Password validation test")
    public void testIsValidPassword() {
        // Checks if the password validation works
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidPassword("Aa1"),
                "Should throw exception because the password is too short");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidPassword("ABCDEFGH1"),
                "Should throw exception because the password does not "
                        + "contain any lower case letters");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidPassword("abcdefgh1"),
                "Should throw exception because the password does "
                        + "not contain any upper case letters");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Profile.isValidPassword("ABCDefgh"),
                "Should throw exception because the password does not contain any numbers");
        Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidPassword(null),
                "Should throw exception because the password is null");
        Assertions.assertDoesNotThrow(() -> Profile.isValidPassword("Password123"),
                "Should not throw exception when the Password is valid");

    }

    /**
     * This method tests if getUsername() works properly.
     * 
     * @see Profile#getUsername()
     */
    @Test
    @DisplayName("getUsername() test")
    public void testGetUsername() {
        Assertions.assertEquals("Username123", profile.getUsername(),
                "getUsername() should return the profile's username, 'Username123'");
    }

    /**
     * This method tests if getPassword() works properly.
     * 
     * @see Profile#getPassword()
     */
    @Test
    @DisplayName("getPassword() tests")
    public void testGetPassword() {
        Assertions.assertEquals("Password123", profile.getPassword(),
                "getPassword() should return the profile's password, 'Password123'");
    }

    /**
     * This method tests everything related to recipes for the Profile class.
     * This means testing getters, setters, and removal of recipes.
     * 
     * @see Profile#getRecipes()
     * @see Profile#putRecipe(Recipe)
     * @see Profile#removeRecipe(Recipe)
     */
    @Test
    @DisplayName("Recipes tests")
    public void testRecipes() {

        // Checks if getRecipes() works
        Assertions.assertNotNull(profile.getRecipes(),
                "getRecipes() should return an empty RecipeLibrary");
        Assertions.assertTrue(profile.getRecipes().getSize() == 0,
                "The RecipeLibrary should be empty");

        // Checks if putRecipe() works to add recipes
        Recipe r1 = new Recipe("Pasta Carbonara", 2, profile);
        Recipe r2 = new Recipe("Hamburger", 1, profile);

        Assertions.assertTrue(profile.getRecipes().getSize() == 2,
                "The RecipeLibrary should have size 2");
        Assertions.assertEquals(r1, profile.getRecipes().getRecipe(0),
                "r1 should be the first recipe in the RecipeLibrary");
        Assertions.assertEquals(
                r2, profile.getRecipes().getRecipe(profile.getRecipes().getSize() - 1),
                "r2 should be the last recipe in the recipeLibrary");
        
        // Checks if putRecipe() works to update recipes
        Assertions.assertEquals(0, profile.getRecipes().getRecipe(0).getIngredients().size(), 
                "The first recipe should not have any ingredients yet");
        r1.addIngredient("Flour", 200.0, "g");
        profile.putRecipe(r1);
        Assertions.assertEquals(2, profile.getRecipes().getSize(), 
                "The profile should still have two recipes");
        // Note: The index of the recipe will change after using putRecipe()
        Assertions.assertEquals(1, profile.getRecipes().getRecipe(1).getIngredients().size(), 
                "The recipe should now have one ingredient");

        // Checks if removeRecipe() works and that removing all recipes returns empty recipeLibrary
        profile.removeRecipe(r2);
        Assertions.assertEquals(1, profile.getRecipes().getSize());
        Assertions.assertEquals(r1, profile.getRecipes().getRecipe(0));
        profile.removeRecipe(r1);
        Assertions.assertNotNull(profile.getRecipes(),
                "getRecipes() should return an empty RecipeLibrary");
        Assertions.assertTrue(profile.getRecipes().getSize() == 0,
                "The RecipeLibrary should be empty");
    }

    /**
     * This method tests if everything related to favorites works properly.
     * This means testing getters, setters and removal of favorites.
     * 
     * @see Profile#getFavorites()
     * @see Profile#addFavorite(Recipe)
     * @see Profile#removeFavorite(Recipe)
     */
    @Test
    @DisplayName("Favorites test")
    public void testFavorites() {
        Profile p2 = new Profile("ProfileP2username", "ProfileP2password");
        // Checks that a new Profile has an empty RecipeLibrary for favorites
        Assertions.assertNotNull(profile.getFavorites(),
                "getFavorites() should return an empty RecipeLibrary");
        Assertions.assertTrue(profile.getFavorites().getSize() == 0,
                "The RecipeLibrary should be empty");
        // Checks that the favorites is added to the Profile
        Recipe r1 = new Recipe("Pasta Carbonara", 2, p2);
        Recipe r2 = new Recipe("Hamburger", 1, profile);
        profile.addFavorite(r1);
        profile.addFavorite(r2);

        Assertions.assertEquals(2, profile.getFavorites().getSize(), 
                "The profile should now have two favorites");
        Assertions.assertEquals(r1, profile.getFavorites().getRecipe(0), 
                "r1 should be the first favorite");
        Assertions.assertEquals(r2, profile.getFavorites().getRecipe(1), 
                "r2 should be the first favorite");

        // Checks that removeFavorite() works
        profile.removeFavorite(r1);

        Assertions.assertEquals(1, profile.getFavorites().getSize(), 
                "The profile should now have one favorite");
        Assertions.assertEquals(r2, profile.getFavorites().getRecipe(0),
                "r2 should now be the profile's first favorite");

        // Removing r1 again should do nothing
        Assertions.assertDoesNotThrow(() -> profile.removeFavorite(r1), 
                "Removing the same favorite again should not throw exception.");
        Assertions.assertEquals(1, profile.getFavorites().getSize(), 
                "The profile should still have one favorite");
        Assertions.assertEquals(r2, profile.getFavorites().getRecipe(0), 
                "r2 should still be the profile's first favorite");

        profile.removeFavorite(r2);
        Assertions.assertNotNull(profile.getFavorites(), 
                "The RecipeLibrary with favorites should not be null");
        Assertions.assertEquals(0, profile.getFavorites().getSize(), 
                "The profile should not have any favorites");
    }

    /**
     * This method tests if the getters and setters for hashed password works properly.
     * 
     * @see Profile#setHashedPassword(String)
     * @see Profile#getHashedPassword()
     */
    @Test
    @DisplayName("setHashedPassword() test")
    public void testSetAndGetHashedPassword() {
        String hashedPassword = PasswordHasher.hashPassword("newHashedPassword");
        profile.setHashedPassword(hashedPassword);
        Assertions.assertEquals(hashedPassword, profile.getHashedPassword(),
                "The hashed password should be 'newHashedPassword'");
    }
}
