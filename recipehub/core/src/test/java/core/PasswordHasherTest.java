package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the Recipe class.
 */
public class PasswordHasherTest {

    /**
     * This method tests if passwords are hashed correctly.
     * 
     * @see PasswordHasher#hashPassword(String)
     */
    @Test
    @DisplayName("hashPassword() test")
    public void testHashPassword() {
        String hashedPassword = PasswordHasher.hashPassword("Password123");
        Assertions.assertNotEquals("Password123", hashedPassword, "The password should be hashed");
        String[] hashedPasswordArray = hashedPassword.split(":");
        Assertions.assertEquals(2, hashedPasswordArray.length,
            "The hashed password should be split into two parts");
    }

    /**
     * This method tests if a password verifying works properly.
     * 
     * @see PasswordHasher#hashPassword(String)
     * @see PasswordHasher#verifyPassword(String, String)
     */
    @Test
    @DisplayName("Test if password is verified correctly")
    public void testVerifyPassword() {
        String hashedPassword = PasswordHasher.hashPassword("Password123");
        String wrongHashedPassword = hashedPassword + ":wrong";
        Assertions.assertFalse(PasswordHasher.verifyPassword("Password123", wrongHashedPassword),
            "The password should not be verified");
        Assertions.assertTrue(PasswordHasher.verifyPassword("Password123", hashedPassword),
            "The password should be verified");
    }
}
