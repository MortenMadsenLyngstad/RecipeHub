package core;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * This class is used to contain Profile information
 * @author Adrian Haabpiht Solberg
 */
public class Profile implements Serializable{
    private static final long serialVersionUID = -7470090944414208496L;
    private int id = 0;
    private String username;
    private String password;
    private String name;
    private RecipeLibrary recipes;

    /**
     * This constructor initializes the username, password and recipes
     * @param username - String value to set as username
     * @param password - String value to set as password
     * @param name - String value is requierd to make this class Serializable
     */
    public Profile(String username, String password) {
        isValidUsername(username);
        this.username = username;
        this.name = username;
        isValidPassword(password);
        this.password = password;
        recipes = new RecipeLibrary();
    }

    /**
     * This metod checks if a username is valid according to the following criterias:
     * - Must have length of at least 5
     * - Must only contain letters and numbers
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
     * This metod checks if a password is valid according to the following criterias:
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
     * This method will return the username of the given profile
     * @return String value for username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method will return the password of the given profile
     * @return String value for password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method will set thee prfile's username to the string sent in if the string is a valid username
     * @param username - String value to set as username
     */
    public void setUsername(String username) {
        isValidUsername(username);
        this.username = username;
    }

    /**
     * This method will set thee prfile's password to the string sent in if the string is a valid password
     * @param password - String value to set as password
     */
    public void setPassword(String password) {
        isValidPassword(password);
        this.password = password;
    }

    /**
     * This method will return the recipes made by the profile
     * @return RecipeLibrary with the profile's recipes
     */
    public RecipeLibrary getRecipes() {
        return recipes;
    }

    /**
     * This method adds the given recipe to the profile's recipes
     * The method takes use of the validation in the RecipeLibrary method used
     * @param recipe - Recipe to add to the profile's recipes
     */
    public void addRecipe(Recipe recipe) {
        recipes.addRecipe(recipe);
    }
    

    /** 
     * This method will return the profiles id when the profile gets serialized
     * @return the int value of the profile
     */
    public int getId() {
        return id;
    }

    /** 
     * This method set an int value to id to make Porfile-class serializable.
     * @param id - int value to make the profile serializable.
     */
    public void setId(int id) {
        this.id = id;
    }
}
