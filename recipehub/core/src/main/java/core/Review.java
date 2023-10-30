package core;

public class Review {
    private double rating;
    private String comment;
    private String username;


    /**
     * This constructor initializes the rating, comment and profile.
     * 
     * @param rating - An integer value of the rating
     * @param comment - A string value of the comment
     * @param profile - The profile that made the rating
     */
    public Review(double r, String comment, String username) {
        if (validateRating(r)) {
            this.rating = r;
        }
        this.comment = comment;
        this.username = username;
    }

    /**
     * This method validates the rating.
     * 
     * @return - Returns true if the rating is valid, false if not
     */
    public boolean validateRating(double r) {
        if (r < 1.0 || r > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        return true;
    }

    /**
     * This method returns the rating.
     * 
     * @return - Returns an integer value of the rating
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * This method returns the comment.
     * 
     * @return - Returns a string value of the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * This method returns the profile that made the comment.
     * 
     * @return - Returns a Profile object
     */
    public String getReviewer() {
        return this.username;
    }
}
