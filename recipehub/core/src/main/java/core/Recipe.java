package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Information centered class for storing and changing recipes.
 */
public class Recipe {
    private List<String> steps;
    private int portions;
    private String name;
    private String description;
    private Map<String, Double> ingredients;
    private Map<String, String> ingredientUnits;
    private String authorUsername;
    private boolean isSaved;
    private List<Review> reviewList;
    private final int id;

    /**
     * Contructor for creating a new Recipe object.
     * 
     * @param name     The name of the recipe.
     * @param portions The amount of portions this recipe makes.
     * @param author   The author of this recipe, a Profile object
     * @param id       The id of the recipe
     */
    public Recipe(String name, int portions, Profile author, int id) {
        setName(name);
        setPortions(portions);
        this.id = id;
        this.authorUsername = author.getUsername();
        author.addRecipe(id);
        ingredients = new HashMap<>();
        steps = new ArrayList<>();
        ingredientUnits = new HashMap<>();
        isSaved = false;
        reviewList = new ArrayList<>();
    }

    /**
     * Sets the name of the recipe.
     * 
     * @param name the string which is set as name, cannot be empty
     * @throws IllegalArgumentException if name is an empty string
     */

    public void setName(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Name cannot be an empty String");
        }
        this.name = name;
    }

    /**
     * Getter for the name attribute.
     * 
     * @return The name attribute of the recipe
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the description of the recipe.
     * 
     * @param description The description to be set
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the decription attribute.
     * 
     * @return The String description of the recipe
     */

    public String getDescription() {
        return description;
    }

    /**
     * Sets the portions attribute for the recipe.
     * 
     * @param portions The amount of portions the ingredients for this recipe make
     * @throws IllegalArgumentException If portions is less than one
     */

    public void setPortions(int portions) {
        if (portions < 1) {
            throw new IllegalArgumentException("Portions must be at least one");
        }
        this.portions = portions;
    }

    /**
     * Getter for the portions attribute.
     * 
     * @return The portions attribute for the recipe
     */

    public int getPortions() {
        return portions;
    }

    /**
     * A method to fetch the preparation steps of the recipe.
     * 
     * @return A List of the steps in the recipe
     */
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    /**
     * Method to add a step to the list of steps in the recipe.
     * 
     * @param step The step to add
     */
    public void addStep(String step) {
        steps.add(step);
    }

    /**
     * Method to remove a step from the list of steps in the recipe.
     * 
     * @param step The step to remove
     * @throws IllegalArgumentException If the step is not in the list of steps
     */
    public void removeStep(String step) {
        if (!steps.remove(step)) {
            throw new IllegalArgumentException("Step not found in steps");
        }
    }

    /**
     * Method to fetch all the different ingredients in the recipe.
     * 
     * @return A set containing every ingredient in the set
     */
    public Set<String> getIngredients() {
        return new HashSet<>(ingredients.keySet());
    }

    /**
     * A method to get the amount needed for a single ingredient in the recipe.
     * 
     * @param ingredient The ingredient in question
     * @throws IllegalArgumentException If the ingredient is not in the recipe
     * @return The amount needed as a double
     */
    public Double getIngredientAmount(String ingredient) {
        if (!ingredients.containsKey(ingredient)) {
            throw new IllegalArgumentException("Ingredient not found in recipe");
        }
        return ingredients.get(ingredient);
    }

    /**
     * Add an amount of an ingredient as a double.
     * 
     * @param ingredient The ingredient to be added
     * @param amount     The amount to be added, must be over 0
     * @param unit       The unit of measurement
     * 
     * @throws IllegalArgumentException If the ingredient is already in the recipe
     *                                  and the unit is not the same
     * @throws IllegalArgumentException If amount is below 0
     */
    public void addIngredient(String ingredient, Double amount, String unit) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be over 0");
        }
        if (ingredients.keySet().contains(ingredient)) {
            if (!getIngredientUnit(ingredient).equals(unit)) {
                throw new IllegalArgumentException("Unit must be the same as previously given");
            }
            Double newAmount = ingredients.getOrDefault(ingredient, 0.0) + amount;
            ingredients.put(ingredient, newAmount);
        } else {
            ingredients.put(ingredient, amount);
            ingredientUnits.put(ingredient, unit);
        }
    }

    /**
     * A method to remove an ingredient completely from the recipe.
     * 
     * @param ingredient The ingredient to remove
     * @throws IllegalArgumentException if the ingredient is not in the keyset
     */
    public void removeIngredient(String ingredient) {
        if (!ingredients.containsKey(ingredient)) {
            throw new IllegalArgumentException("Ingredient not found in recipe");
        } else {
            ingredients.remove(ingredient);
            ingredientUnits.remove(ingredient);
        }
    }

    /**
     * Method to remove a specific amount of an ingredient.
     * 
     * @param ingredient The ingredient to remove
     * @param amount     The amount of the ingredient to remove, must be lower than
     *                   the amount of the ingredient, and over 0
     * 
     * @throws IllegalargumentException If amount is below 0
     * @throws IllegalargumentException If the ingredient is not in the keyset
     * @throws IllegalargumentException If the amount to remove is larger than what
     *                                  is in the recipe
     */

    public void removeIngredientAmount(String ingredient, Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Removed amount must be over 0");
        }
        if (!ingredients.containsKey(ingredient)) {
            throw new IllegalArgumentException("Ingredient not found in recipe");
        }
        if (ingredients.get(ingredient) < amount) {
            throw new IllegalArgumentException("Cannot remove more than already in ingredients");
        }
        Double newAmount = ingredients.get(ingredient) - amount;
        ingredients.put(ingredient, newAmount);
    }

    /**
     * Method to get the ingredientunit of an ingredient in the recipe.
     * 
     * @param ingredient The ingredient to find the unit of
     * @return A string of the unit of the ingredient
     * 
     * @throws IllegalargumentException If the ingredient is not in the recipe
     */
    public String getIngredientUnit(String ingredient) {
        if (!ingredientUnits.containsKey(ingredient)) {
            throw new IllegalArgumentException();
        }
        return ingredientUnits.get(ingredient);
    }

    /**
     * Used to set the unit of the ingredient.
     * 
     * @param ingredient The ingredient to set the unit of
     * @param unit       the unit to be set
     * @throws IllegalArgumentException if unit is not pcs, g or dL
     */

    public void setIngredientUnit(String ingredient, String unit) {
        if (!(unit.equals("pcs") || unit.equals("g") || unit.equals("dL"))) {
            throw new IllegalArgumentException("Unit must be pcs, g or dL");
        }
        ingredientUnits.put(ingredient, unit);
    }

    /**
     * Adds a rarings to the recipe.
     * 
     * @param rating The rating given by the user
     */
    public void addReview(Review rating) {
        if (hasRated(rating.getReviewer())) {
            throw new IllegalArgumentException("User has already rated this recipe");
        }
        reviewList.add(rating);
    }

    /**
     * Checks if the recipe has been rated by the user.
     * 
     * @return true of the recipe has been rated by the user, false otherwise
     */
    public boolean hasRated(String username) {
        return reviewList.stream().anyMatch(r -> r.getReviewer().equals(username));
    }

    /**
     * Gets the average rating of the recipe as double with two decimals.
     * 
     * @return The average rating of the recipe
     */
    public double getAverageRating() {
        String number = String.format("%.2f", reviewList.stream()
                .mapToDouble(r -> r.getRating()).average().orElse(0.0));
        StringBuilder sb = new StringBuilder();
        sb.append(number.charAt(0));
        sb.append('.');
        sb.append(number.charAt(2));
        number = sb.toString();
        return Double.parseDouble(number);
    }

    /**
     * Gets the number of reviews the recipe has.
     * 
     * @return The number of reviews the recipe has as integer
     */
    public int getNumberOfReviewers() {
        return reviewList.size();
    }

    /**
     * Gets the number of comments the recipe has.
     * 
     * @return The number of comments the recipe has as integer
     */
    public int getNumberOfcomments() {
        return (int) reviewList.stream().filter(r -> !r.getComment().equals("")).count();
    }

    /**
     * Gets the reviews of the recipe.
     * 
     * @return The reviews of the recipe as a list
     */
    public List<Review> getReviews() {
        return new ArrayList<>(reviewList);
    }

    /**
     * Gets the author of the recipe.
     * 
     * @return The author attribute of the recipe
     */
    public String getAuthor() {
        return authorUsername;
    }

    /**
     * Gets the isSaved attribute of the recipe.
     * 
     * @return boolean value for the isSaved boolean value
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * This method sets the boolean value for if the recipe has been saved to the
     * database.
     * 
     * @param saved - boolean value for if the recipe has been saved to the database
     */
    public void setSaved(boolean saved) {
        this.isSaved = saved;
    }

    /**
     * Gets the id of the recipe.
     * 
     * @return The id of the recipe
     */
    public int getId() {
        return id;
    }
}
