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

    /**
     * Contructor for creating a new Recipe object.
     * 
     * @param name name of the recipe.
     * @param portions amount of portions this recipe makes.
     * @param author author of this recipe, a Profile object
     * @see #setName(String)
     * @see #setPortions(int)
     * @see Profile#getUsername()
     * @see Profile#putRecipe(Recipe)
     */
    public Recipe(String name, int portions, Profile author) {
        setName(name);
        setPortions(portions);
        this.authorUsername = author.getUsername();
        author.putRecipe(this);
        ingredients = new HashMap<>();
        steps = new ArrayList<>();
        ingredientUnits = new HashMap<>();
        isSaved = false;
        reviewList = new ArrayList<>();
    }

    /**
     * This method sets the name of the recipe.
     * 
     * @param name new name of the recipe
     * @throws IllegalArgumentException if name is an empty string
     */

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be an empty String");
        }
        this.name = name;
    }

    /**
     * This method gets the recipe name.
     * 
     * @return The name of the recipe
     */

    public String getName() {
        return name;
    }

    /**
     * This method sets the description of the recipe.
     * 
     * @param description description to be set
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method sets the recipe's description.
     * 
     * @return The String description of the recipe
     */

    public String getDescription() {
        return description;
    }

    /**
     * This method sets the portions for the recipe.
     * 
     * @param portions amount of portions the ingredients for this recipe make
     * @throws IllegalArgumentException if portions is less than one
     */

    public void setPortions(int portions) {
        if (portions < 1) {
            throw new IllegalArgumentException("Portions must be at least one");
        }
        this.portions = portions;
    }

    /**
     * This method gets the recipe's portions.
     * 
     * @return The portions for the recipe
     */

    public int getPortions() {
        return portions;
    }

    /**
     * This method fetches the preparation steps of the recipe.
     * 
     * @return A List of the steps in the recipe
     */
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    /**
     * This method adds a step to the list of steps in the recipe.
     * 
     * @param step string with step to add
     */
    public void addStep(String step) {
        steps.add(step);
    }

    /**
     * This method removes a step from the list of steps in the recipe.
     * 
     * @param step step to remove
     * @throws IllegalArgumentException if the step is not in the list of steps
     */
    public void removeStep(String step) {
        if (!steps.remove(step)) {
            throw new IllegalArgumentException("Step not found in steps");
        }
    }

    /**
     * This method fetches all the different ingredients in the recipe.
     * 
     * @return A set containing every ingredient in the set
     */
    public Set<String> getIngredients() {
        return new HashSet<>(ingredients.keySet());
    }

    /**
     * A method to get the amount needed for a single ingredient in the recipe.
     * 
     * @param ingredient ingredient to get amount of
     * @return The amount needed as a double
     * @throws IllegalArgumentException if the ingredient is not in the recipe
     */
    public Double getIngredientAmount(String ingredient) {
        if (!ingredients.containsKey(ingredient)) {
            throw new IllegalArgumentException("Ingredient not found in recipe");
        }
        return ingredients.get(ingredient);
    }

    /**
     * This method adds the given amount of the given ingredient to the recipe.
     * 
     * @param ingredient ingredient to be added
     * @param amount amount to be added
     * @param unit unit of measurement
     * @throws IllegalArgumentException If amount is below 0
     * @throws IllegalArgumentException If the ingredient is already in the recipe
     *                                  and the unit is not the same
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
     * This method removes an ingredient completely from the recipe.
     * 
     * @param ingredient ingredient to remove
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
     * This method removes a specific amount of an ingredient.
     * 
     * @param ingredient ingredient to remove an amount from
     * @param amount amount of the ingredient to remove
     * @throws IllegalargumentException if amount is below 0
     * @throws IllegalargumentException if the ingredient is not in the keyset
     * @throws IllegalargumentException if the amount to remove is larger than what is in the recipe
     */

    public void removeIngredientAmount(String ingredient, Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to remove must be over 0");
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
     * This method gets the ingredientunit of an ingredient in the recipe.
     * 
     * @param ingredient ingredient to find the unit of
     * @return A string of the unit of the ingredient
     * @throws IllegalArgumentException if the ingredient is not in the recipe
     */
    public String getIngredientUnit(String ingredient) {
        if (!ingredientUnits.containsKey(ingredient)) {
            throw new IllegalArgumentException("Ingredient not found in recipe");
        }
        return ingredientUnits.get(ingredient);
    }

    /**
     * This method sets the unit of the ingredient.
     * 
     * @param ingredient ingredient to set the unit of
     * @param unit unit to be set
     * @throws IllegalArgumentException if unit is not pcs, g or dL
     */

    public void setIngredientUnit(String ingredient, String unit) {
        if (!(unit.equals("pcs") || unit.equals("g") || unit.equals("dL"))) {
            throw new IllegalArgumentException("Unit must be pcs, g or dL");
        }
        ingredientUnits.put(ingredient, unit);
    }

    /**
     * This method adds a rarings to the recipe.
     * 
     * @param rating rating given by the user
     * @see #hasRated(String)
     */
    public void addReview(Review rating) {
        if (hasRated(rating.getReviewer())) {
            throw new IllegalArgumentException("User has already rated this recipe");
        }
        reviewList.add(rating);
    }

    /**
     * This method checks if the recipe has been rated by the user.
     * 
     * @return true if the recipe has been rated by the user, false otherwise
     * @see Review#getReviewer()
     */
    public boolean hasRated(String username) {
        return reviewList.stream().anyMatch(r -> r.getReviewer().equals(username));
    }

    /**
     * This method gets the average rating of the recipe as double with two decimals.
     * 
     * @return The average rating of the recipe
     */
    public double getAverageRating() {
        double number = reviewList.stream().mapToDouble(r -> r.getRating()).average().orElse(0.00);
        double roundedNumber = Math.round(number * 100) / 100.0;
        return roundedNumber;
    }

    /**
     * This method gets the number of reviews the recipe has.
     * 
     * @return The number of reviews the recipe has as integer
     */
    public int getNumberOfReviewers() {
        return reviewList.size();
    }

    /**
     * This method gets the number of comments the recipe has.
     * 
     * @return The number of comments the recipe has as integer
     * @see Review#getComment()
     */
    public int getNumberOfcomments() {
        return (int) reviewList.stream().filter(r -> !r.getComment().equals("")).count();
    }

    /**
     * This method gets the reviews of the recipe.
     * 
     * @return The reviews of the recipe as a list
     */
    public List<Review> getReviews() {
        return new ArrayList<>(reviewList);
    }

    /**
     * This method gets the author of the recipe.
     * 
     * @return The author attribute of the recipe
     */
    public String getAuthor() {
        return authorUsername;
    }

    /**
     * This method will return whether the recipe is saved or not.
     * 
     * @return boolean value for whether the recipe is saved or not
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * This method sets the boolean value for if the recipe has been saved or not.
     * 
     * @param saved boolean value for whether the recipe has been saved or not
     */
    public void setSaved(boolean saved) {
        this.isSaved = saved;
    }
}
