package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the Review class.
 */
public class ReviewTest {
    
    private Review review;
    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile("Profile1", "Password1");
        review = new Review(5, "This is a comment", profile.getUsername());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructor() {
        Assertions.assertEquals(5, review.getRating());
        Assertions.assertEquals("This is a comment", review.getComment());
        Assertions.assertEquals(profile.getUsername(), review.getReviewer());
    }

    /**
     * Tests the validateRating method.
     */
    @Test
    public void testValidateRating() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Review(0, "This is a comment", profile.getUsername());
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Review(6, "This is a comment", profile.getUsername());
        });
        Assertions.assertDoesNotThrow(() -> {
            new Review(5, "This is a comment", profile.getUsername());
        });
    }
}
