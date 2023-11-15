package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for the Review class.
 * This is a small information centered class without the ability to 
 * change reviews once they are initialized, making this test class quite small.
 */
public class ReviewTest {

    /**
     * This method tests the whole review class.
     * This includes testing of the constructors, the getters and the rating validation
     * 
     * @see Review#getRating()
     * @see Review#getComment()
     * @see Review#getReviewer()
     */
    @Test
    public void testReview() {
        Profile profile = new Profile("Profile1", "Password1");

        // Checks that the constructor throws exception when rating is invalid
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new Review(0, "This is a comment", profile.getUsername()), 
            "Should not be able to give a rating below 1 when creating a review");
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> new Review(6, "This is a comment", profile.getUsername()), 
            "Should not be able to give a rating below 1 when creating a review");

        // Cheks that constructor does not throw exception when rating is valid
        Assertions.assertDoesNotThrow(() -> {
            new Review(5, "This is a comment", profile.getUsername());
        }, "Should not throw when creating a review with a valid rating");

        Review review = new Review(5, "This is a comment", profile.getUsername());

        // Checks that the getters works and that the attributes were initialized properly
        Assertions.assertEquals(5, review.getRating(), "The rating should be 5");
        Assertions.assertEquals("This is a comment", review.getComment(), 
            "The review should have the given comment");
        Assertions.assertEquals(profile.getUsername(), review.getReviewer(), 
            "The reviewer should be set as the given username");
    }
}
