package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testclass with JUnit test to check if the logic in Profile-class works
 * properly
 * 
 * @author Adrian Haabpiht Solberg
 * @author Trygve Eriksen
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
                Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("abc", "abc"),
                                "Should not be able to make a profile with an invalid username and password");
                Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("abc", "Password123"),
                                "Should not be able to make a profile with an invalid username");
                Assertions.assertThrows(IllegalArgumentException.class, () -> new Profile("Username123", "abc"),
                                "Should not be able to make a profile with an invalid password");
                Assertions.assertDoesNotThrow(() -> new Profile("ValidUsername123", "ValidPassword123"),
                                "Should not throw exception when both the username and the password is valid");

                // Checks if the correct values are stored properly
                Assertions.assertEquals("Username123", profile.getUsername(), "The username should be 'Username123'");
                Assertions.assertEquals("Password123", profile.getPassword(), "The password should be 'Password123'");
                Assertions.assertNotNull(profile.getRecipes(), "The recipes of the profile should not be null");
                Assertions.assertTrue(profile.getRecipes().size() == 0, "The Ararylist should be empty");
        }

        /**
         * This method tests if the username validation works properly
         */
        @Test
        @DisplayName("Username validation test")
        public void testIsValidUsername() {
                // Checks if the username validation works
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidUsername("Aa1"),
                                "Should throw exception because the username is too short");
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidUsername("Username.,-"),
                                "Should throw exception because the username contains invalid characters");
                Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidUsername(null),
                                "Should throw exception because the username is null");
                Assertions.assertDoesNotThrow(() -> Profile.isValidUsername("Username123"),
                                "Should not throw exception when the username is valid");

        }

        /**
         * This method tests if the password validation works properly
         */
        @Test
        @DisplayName("Password validation test")
        public void testIsValidPassword() {
                // Checks if the password validation works
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("Aa1"),
                                "Should throw exception because the password is too short");
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("ABCDEFGH1"),
                                "Should throw exception because the password does not contain any lower case letters");
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("abcdefgh1"),
                                "Should throw exception because the password does not contain any upper case letters");
                Assertions.assertThrows(IllegalArgumentException.class, () -> Profile.isValidPassword("ABCDefgh"),
                                "Should throw exception because the password does not contain any numbers");
                Assertions.assertThrows(NullPointerException.class, () -> Profile.isValidPassword(null),
                                "Should throw exception because the password is null");
                Assertions.assertDoesNotThrow(() -> Profile.isValidPassword("Password123"),
                                "Should not throw exception when the Password is valid");

        }

        /**
         * This method tests if getters and setters for the username works properly
         */
        @Test
        @DisplayName("Username tests")
        public void testUsername() {

                // Cheks if getUsername() works
                Assertions.assertEquals("Username123", profile.getUsername(),
                                "getUsername() should return the profile's username, 'Username123'");

                // Checks that you cannot set username to an invalid one
                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setUsername("Aa1"),
                                "Should not be able to set username to an invalid one (too short)");
                Assertions.assertFalse(profile.getUsername().equals("Aa1"), "Username should not have cahnged");

                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setUsername("Username.,-"),
                                "Should not be able to set username to an invalid one (invalid characters)");
                Assertions.assertFalse(profile.getUsername().equals("Username.,-"), "Username should not have cahnged");

                // Checks if the username changes when you set a valid username
                profile.setUsername("newUsername321");
                Assertions.assertEquals("newUsername321", profile.getUsername(),
                                "The username should be changed to 'newUsername321'");

        }

        /**
         * This method tests if getters and setters for the password works properly
         */
        @Test
        @DisplayName("Password tests")
        public void testPassword() {

                // Cheks if getPassword() works
                Assertions.assertEquals("Password123", profile.getPassword(),
                                "getPassword() should return the profile's password, 'Password123'");

                // Checks that you cannot set password to an invalid one
                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("Aa1"),
                                "Should not be able to set password to an invalid one (too short)");
                Assertions.assertFalse(profile.getPassword().equals("Aa1"), "Password should not have cahnged");

                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("ABCDEFGH1"),
                                "Should not be able to set password to an invalid one (no lower case letter)");
                Assertions.assertFalse(profile.getPassword().equals("ABCDEFGH1"), "Password should not have cahnged");

                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("abcdefgh1"),
                                "Should not be able to set password to an invalid one (no upper case letter)");
                Assertions.assertFalse(profile.getPassword().equals("abcdefgh1"), "Password should not have cahnged");

                Assertions.assertThrows(IllegalArgumentException.class, () -> profile.setPassword("ABCDefgh"),
                                "Should not be able to set password to an invalid one (no number)");
                Assertions.assertFalse(profile.getPassword().equals("ABCDefgh"), "Password should not have cahnged");

                // Checks if the password changes when you send in a valid password
                profile.setPassword("newPassword321");
                Assertions.assertEquals("newPassword321", profile.getPassword(),
                                "The password should be changed to 'newPassword321'");

        }

        /**
         * This method tests if getters and setters for the recipes works properly, also
         * checks removeRecipe()
         */
        @Test
        @DisplayName("Recipes tests")
        public void testRecipes() {

                // Checks if getRecipes() works
                Assertions.assertNotNull(profile.getRecipes(), "getRecipes() should return an empty ArrayList");
                Assertions.assertTrue(profile.getRecipes().size() == 0, "The ArrayList should be empty");

                // Checks if addRecipe() works and if getRecipes() works after adding recipes
                Recipe r1 = new Recipe("Pasta Carbonara", 2, profile, 1);
                Recipe r2 = new Recipe("Hamburger", 1, profile, 2);

                Assertions.assertTrue(profile.getRecipes().size() == 2, "The Arraylist should have size 2");
                Assertions.assertEquals(r1.getId(), profile.getRecipes().get(0),
                                "r1 should be the first recipe in the RecipeLibrary");
                Assertions.assertEquals(r2.getId(), profile.getRecipes().get(profile.getRecipes().size() - 1),
                                "r2 should be the last recipe in the recipeLibrary");
                // Checks if removeRecipe() works and that removing all recipes returns an empty
                // RecipeLibrary
                profile.removeRecipe(r2);
                Assertions.assertEquals(1, profile.getRecipes().size());
                Assertions.assertEquals(r1.getId(), profile.getRecipes().get(0));
                profile.removeRecipe(r1);
                Assertions.assertNotNull(profile.getRecipes(), "getRecipes() should return an empty ArrayList");
                Assertions.assertTrue(profile.getRecipes().size() == 0, "The RecipeLibrary should be empty");
        }

        /**
         * This method will tests if everything related to favorites works properly
         */
        @Test
        @DisplayName("Favorites test")
        public void testFavorites() {
                Profile p2 = new Profile("ProfileP2username", "ProfileP2password");
                // Checks that a new Profile has an empty RecipeLibrary for favorites
                Assertions.assertNotNull(profile.getFavorites(), "getFavorites() should return an empty Arraylist");
                Assertions.assertTrue(profile.getFavorites().size() == 0, "The Arraylist should be empty");
                // Checks that the favorites is added the the Profile
                Recipe r1 = new Recipe("Pasta Carbonara", 2, p2, 1);
                Recipe r2 = new Recipe("Hamburger", 1, profile, 2);
                profile.addFavorite(r1.getId());
                profile.addFavorite(r2.getId());

                Assertions.assertEquals(2, profile.getFavorites().size());
                Assertions.assertEquals(r1.getId(), profile.getFavorites().get(0));
                Assertions.assertEquals(r2.getId(), profile.getFavorites().get(1));

                // Checks that removeFavorite() works
                profile.removeFavorite(r1.getId());

                Assertions.assertEquals(1, profile.getFavorites().size());
                Assertions.assertEquals(r2.getId(), profile.getFavorites().get(0));

                // Removing r1 again should do nothing
                profile.removeFavorite(r1.getId());

                Assertions.assertEquals(1, profile.getFavorites().size());
                Assertions.assertEquals(r2.getId(), profile.getFavorites().get(0));

                profile.removeFavorite(r2.getId());
                Assertions.assertNotNull(profile.getFavorites());
                Assertions.assertEquals(0, profile.getFavorites().size());
        }

        @Test
        @DisplayName("Check if setHashedPassword() works")
        public void testSetAndGetHashedPassword() {
                String hashedPassword = PasswordHasher.hashPassword("newHashedPassword");
                profile.setHashedPassword(hashedPassword);
                Assertions.assertEquals(hashedPassword, profile.getHashedPassword(),
                                "The hashed password should be 'newHashedPassword'");
        }
}
