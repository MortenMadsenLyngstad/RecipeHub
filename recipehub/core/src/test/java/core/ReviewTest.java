package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReviewTest {
    
    private Profile p = new Profile("User1234", "User12345");

    /**
     * Tests the constructor
     */
    @Test
    public void testConstructor() {
        Review review = new Review(5, "This is a comment", p.getUsername());
        Assertions.assertEquals(5, review.getRating());
        Assertions.assertEquals("This is a comment", review.getComment());
        Assertions.assertEquals(p.getUsername(), review.getReviewer());
    }

    /**
     * Tests the validateRating method
     */
    @Test
    public void testValidateRating() {
        Review review = new Review(5, "This is a comment", p.getUsername());
        Assertions.assertTrue(review.validateRating(5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            review.validateRating(0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            review.validateRating(6);
        });
    }
}
