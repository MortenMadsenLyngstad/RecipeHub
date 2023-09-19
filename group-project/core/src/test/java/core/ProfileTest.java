package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testclass with JUnit test to check if the logic in Profile-class works properly
 * @author Adrian Haabpiht Solberg
 */
public class ProfileTest {

    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile("Username123", "Password123");
    }

    /**
     * This method tests if the contructor works properly
     */
    @Test
    @DisplayName("Contructor test")
    public void testContructor() {
        // Checks that you cant make an invalid profile
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("abc", "abc"), "Should not be able to make a profile with an invalid username and password");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("abc", "Password123"), "Should not be able to make a profile with an invalid username");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("Username123", "abc"), "Should not be able to make a profile with an invalid password");
        Assertions.assertDoesNotThrow(() -> new Profile("ValidUsername123", "ValidPassword123"), "Should not throw exception when both the username and the password is valid");

        // Checks if the correct values are stored properly
        Assertions.assertEquals("Username123", profile.getUsername(), "The username should be 'Username123'");
        Assertions.assertEquals("Password123", profile.getPassword(), "The password should be 'Password123'");
        Assertions.assertNotNull(profile.getRecipes(), "The recipes of the profilee should not be null");
        Assertions.assertTrue(profile.getRecipes().getSize() == 0, "The RecipeLibrary should be empty");
    }

    /**
     * This method tests if the username validation works properly
     */
    @Test
    @DisplayName("Username validation test")
    public void testIsValidUsername() {
        // Checks if the username validation works
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidUsername("Aa1"), "Should throw exception because the username is too short");
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidUsername("Username.,-"), "Should throw exception because the username contains invalid characters");
        Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidUsername(null), "Should throw exception because the username is null");
        Assertions.assertDoesNotThrow(() -> Profile.isValidUsername("Username123"), "Should not throw exception when the username is valid");
        
    }

    /**
     * This method tests if the password validation works properly
     */
    @Test
    @DisplayName("Password validation test")
    public void testIsValidPassword() {
        // Checks if the password validation works
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("Aa1"), "Should throw exception because the password is too short");
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("ABCDEFGH1"), "Should throw exception because the password does not contain any lower case letters");
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("abcdefgh1"), "Should throw exception because the password does not contain any upper case letters");
        Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("ABCDefgh"), "Should throw exception because the password does not contain any numbers");
        Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidPassword(null), "Should throw exception because the password is null");
        Assertions.assertDoesNotThrow(() -> Profile.isValidPassword("Password123"), "Should not throw exception when the Password is valid");

    }

    /**
     * This method tests if getters and setters for the username works properly
     */
    @Test
    @DisplayName("Username tests")
    public void testUsername() {

        // Cheks if getUsername() works
        Assertions.assertEquals("Username123", profile.getUsername(), "getUsername() should return the profile's username, 'Username123'");

        // Checks that you cannot set username to an invalid one
        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setUsername("Aa1"), "Should not be able to set username to an invalid one (too short)");
        Assertions.assertFalse(profile.getUsername().equals("Aa1"), "Username should not have cahnged");

        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setUsername("Username.,-"), "Should not be able to set username to an invalid one (invalid characters)");
        Assertions.assertFalse(profile.getUsername().equals("Username.,-"), "Username should not have cahnged");

        // Checks if the username changes when you set a valid username
        profile.setUsername("newUsername321");
        Assertions.assertEquals("newUsername321", profile.getUsername(), "The username should be changed to 'newUsername321'");

    }

    /**
     * This method tests if getters and setters for the password works properly
     */
    @Test
    @DisplayName("Password tests")
    public void testPassword() {
        
        // Cheks if getPassword() works
        Assertions.assertEquals("Password123", profile.getPassword(), "getPassword() should return the profile's password, 'Password123'");

        // Checks that you cannot set password to an invalid one
        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("Aa1"), "Should not be able to set password to an invalid one (too short)");
        Assertions.assertFalse(profile.getPassword().equals("Aa1"), "Password should not have cahnged");

        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("ABCDEFGH1"), "Should not be able to set password to an invalid one (no lower case letter)");
        Assertions.assertFalse(profile.getPassword().equals("ABCDEFGH1"), "Password should not have cahnged");

        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("abcdefgh1"), "Should not be able to set password to an invalid one (no upper case letter)");
        Assertions.assertFalse(profile.getPassword().equals("abcdefgh1"), "Password should not have cahnged");

        Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("ABCDefgh"), "Should not be able to set password to an invalid one (no number)");
        Assertions.assertFalse(profile.getPassword().equals("ABCDefgh"), "Password should not have cahnged");
        
        // Checks if the password changes when you send in a valid password
        profile.setPassword("newPassword321");
        Assertions.assertEquals("newPassword321", profile.getPassword(), "The password should be changed to 'newPassword321'");

    }

    /**
     * This method tests if getters and setters for the recipes works properly
     */
    @Test
    @DisplayName("Recipes tests")
    public void testRecipes() {

    //     // Checks if getRecipes() works
        Assertions.assertNotNull(profile.getRecipes(), "getREcipes() should return an empty RecipeLibrary");
        Assertions.assertTrue(profile.getRecipes().getSize() == 0, "The RecipeLibrary should be empty");

        // Checks if addRecipe() works and if getRecipes() works after adding recipes
        Recipe r1 = new Recipe("Pasta Carbonara", 2, profile);
        Recipe r2 = new Recipe("Pasta Bolognese", 5, profile);
        Recipe r3 = new Recipe("Pizza", 1, profile);
        Recipe r4 = new Recipe("Hamburger", 1, profile);

        Assertions.assertTrue(profile.getRecipes().getSize() == 4, "The RecipeLibrary should have size 4");
        Assertions.assertEquals(r1, profile.getRecipes().getRecipe(0), "r1 should be the first recipe in the RecipeLibrary");
        Assertions.assertEquals(r4, profile.getRecipes().getRecipe(profile.getRecipes().getSize() - 1), "r4 should be the last recipe in the recipeLibrary");
    }
}
