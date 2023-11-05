package core;

import java.util.regex.Pattern;


/**
 * This class is used to contain Profile information.
 * 
 * @author Adrian Haabpiht Solberg
 */

public class Profile {
    private String username;
    private transient String password;
    private String hashedPassword;
    private RecipeLibrary recipeLibrary;
    private RecipeLibrary favorites;

    /**
     * This constructor initializes the username, password and recipes.
     * 
     * @param username - String value to set as username
     * @param password - String value to set as password
     */
    public Profile(String username, String password) {
        isValidUsername(username);
        this.username = username;
        isValidPassword(password);
        this.password = password;
        this.hashedPassword = PasswordHasher.hashPassword(password);
        recipeLibrary = new RecipeLibrary();
        favorites = new RecipeLibrary();
    }

    /**
     * This metod checks if a username is valid.
     * These are the criterias:
     * - Must have length of at least 5
     * - Must only contain letters and numbers
     * 
     * @param username - String value to check
     * @throws IllegalArgumeentException if username is invalid
     */
    public static void isValidUsername(String username) {
        if (username.length() < 5) {
            throw new IllegalArgumentException("Username is too short");
        }
        if (!Pattern.matches("^[a-zA-Z0-9]+$", username)) {
            throw new IllegalArgumentException("Username must only contain letters and numbers");
        }
    }

    /**
     * This metod checks if a password is valid.
     * These are the criterias:
     * - Must have length of at least 8
     * - Must contain a lower case letter
     * - Must contain an upper case letter
     * 
     * @param password - String value to check
     * @throws IllegalArgumeentException if password is invalid
     */
    public static void isValidPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password is too short");
        }
        if (!Pattern.matches(".*[a-z].*", password)) {
            throw new IllegalArgumentException("Password must contain a lower case letter");
        }
        if (!Pattern.matches(".*[A-Z].*", password)) {
            throw new IllegalArgumentException("Password must contain an upper case letter");
        }
        if (!Pattern.matches(".*[0-9].*", password)) {
            throw new IllegalArgumentException("Password must contain a number");
        }
    }

    /**
     * This method will return the username of the given profile.
     * 
     * @return String value for username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method will return the password of the given profile.
     * 
     * @return String value for password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method will return the hashed password of the given profile.
     * @return String value for hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * This method will set thee prfile's username to the string sent in if the
     * string is a valid username.
     * 
     * @param username - String value to set as username
     */
    public void setUsername(String username) {
        isValidUsername(username);
        this.username = username;
    }

    /**
     * This method will set thee prfile's password to the string sent in if the
     * string is a valid password.
     * 
     * @param password - String value to set as password
     */
    public void setPassword(String password) {
        isValidPassword(password);
        this.password = password;
    }

    /**
     * This method will set the hashed password of the profile.
     * @param hashedPassword - String value to set as hashed password
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * This method will return the recipes made by the profile.
     * 
     * @return RecipeLibrary with the profile's recipes
     */
    public RecipeLibrary getRecipes() {
        return recipeLibrary;
    }

    /**
     * This method adds the given recipe to the profile's recipes.
     * 
     * @param recipe - Recipe to add to the profile's recipes
     */
    public void putRecipe(Recipe recipe) {
        recipeLibrary.putRecipe(recipe);
    }

    /**
     * This method will remove the recipe from the profile's recipes.
     */
    public void removeRecipe(Recipe recipe) {
        this.recipeLibrary.removeRecipe(recipe);
    }

    /**
     * This method will return this profile's favorite recipes.
     * 
     * @return RecipeLibrary with the profile's favorite recipes
     */
    public RecipeLibrary getFavorites() {
        return favorites;
    }

    /**
     * This method adds the given recipe to the profile's favorites.
     * 
     * @param recipe - Recipe to add to the profile's favorites
     */
    public void addFavorite(Recipe recipe) {
        favorites.putRecipe(recipe);
    }

    /**
     * This method will remove the given recipee from the profile's favorites.
     * 
     * @param recipe - Recipe to remove from the profile's favorite
     */
    public void removeFavorite(Recipe recipe) {
        if (favorites.containsRecipe(recipe)) {
            favorites.removeRecipe(recipe);
        }
    }

}
