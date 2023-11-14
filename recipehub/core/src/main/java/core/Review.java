package core;

/**
 * Information centered class for storing a review.
 */
public class Review {
    private double rating;
    private String comment;
    private String username;

    /**
     * This constructor initializes the rating, comment and profile.
     * 
     * @param rating integer value of the rating
     * @param comment string value of the comment
     * @param username username of the profile that made the review
     */
    public Review(double rating, String comment, String username) {
        validateRating(rating);
        this.rating = rating;
        this.comment = comment;
        this.username = username;
    }

    /**
     * This method validates that the rating is between 1 and 5.
     * 
     * @throws IllegalArgumentException if rating is below 1 or above 5
     */
    private void validateRating(double r) {
        if (r < 1.0 || r > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    /**
     * This method gets the rating of this review.
     * 
     * @return Integer value of the rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * This method gets the comment of this review.
     * 
     * @return String value of the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method gives the username that made the review.
     * 
     * @return String value with the username of the profile who made the review
     */
    public String getReviewer() {
        return username;
    }
}
