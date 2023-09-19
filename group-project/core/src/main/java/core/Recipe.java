package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recipe implements Serializable{
    private static final long serialVersionUID = -8470090944414208496L;
    private int id = 0;
    private List<String> steps;
    private int portions;
    private String name;
    private String description;
    private Map<String, Double> ingredients;
    private Map<String, String> ingredientUnits;
    private Profile author;

    public Recipe(String name, int portions, Profile author) {
        setName(name);
        setPortions(portions);
        this.author = author;
        author.addRecipe(this);
        ingredients = new HashMap<>();
        steps = new ArrayList<>();
        ingredientUnits = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the recipe.
     * 
     * @param name the string which is set as name, cannot be empty
     * @throws IllegalArgumentException if name is an empty string
     */

    public void setName(String name) {
        if (name.equals(""))
            throw new IllegalArgumentException("Name cannot be an empty String");
        this.name = name;
    }

    /**
     * 
     * @return The name attribute of the recipe
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the description of the recipe
     * 
     * @param description The description to be set
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return The String description of the recipe
     */

    public String getDescription() {
        return description;
    }

    /**
     * Sets the portions attribute for the recipe
     * 
     * @param portions The amount of portions the ingredients for this recipe make
     * @throws IllegalArgumentException If portions is less than one
     */

    public void setPortions(int portions) {
        if (portions < 1)
            throw new IllegalArgumentException("Portions must be at least one");
        this.portions = portions;
    }

    /**
     * 
     * @return The portions attribute for the recipe
     */

    public int getPortions() {
        return portions;
    }

    /**
     * A method to fetch the preparation steps of the recipe
     * 
     * @return A List of the steps in the recipe
     */
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    /**
     * Method to add a step to the list of steps in the recipe
     * 
     * @param step The step to add
     */
    public void addStep(String step) {
        steps.add(step);
    }

    /**
     * Method to remove a step from the list of steps in the recipe
     * 
     * @param step The step to remove
     * @throws IllegalArgumentException If the step is not in the list of steps
     */
    public void removeStep(String step) {
        if (!steps.remove(step))
            throw new IllegalArgumentException("Step not found in steps");
    }

    /**
     * Method to fetch all the different ingredients in the recipe
     * 
     * @return A set containing every ingredient in the set
     */
    public Set<String> getIngredients() {
        return new HashSet<>(ingredients.keySet());
    }

    /**
     * A method to get the amount needed for a single ingredient in the recipe
     * 
     * @param ingredient The ingredient in question
     * @throws IllegalArgumentException If the ingredient is not in the recipe
     * @return The amount needed as a double
     */
    public Double getIngredientAmount(String ingredient) {
        if (!ingredients.containsKey(ingredient))
            throw new IllegalArgumentException("Ingredient not found in recipe");
        return ingredients.get(ingredient);
    }

    /**
     * Add an amount of an ingredient as a double
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
            if (!getIngredientUnit(ingredient).equals(unit))
                throw new IllegalArgumentException("Unit must be the same as previously given");
            Double newAmount = ingredients.getOrDefault(ingredient, 0.0) + amount;
            ingredients.put(ingredient, newAmount);
        } else {
            ingredients.put(ingredient, amount);
            ingredientUnits.put(ingredient, unit);
        }
    }

    /**
     * A method to remove an ingredient completely from the recipe
     * 
     * @param ingredient The ingredient to remove
     * @throws IllegalArgumentException if the ingredient is not in the keyset
     */
    public void removeIngredient(String ingredient) {
        if (!ingredients.containsKey(ingredient))
            throw new IllegalArgumentException("Ingredient not found in recipe");
        else {
            ingredients.remove(ingredient);
            ingredientUnits.remove(ingredient);
        }
    }

    /**
     * Method to remove a specific amount of an ingredient
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
        if (amount < 0)
            throw new IllegalArgumentException("Removed amount must be over 0");
        if (!ingredients.containsKey(ingredient))
            throw new IllegalArgumentException("Ingredient not found in recipe");
        if (ingredients.get(ingredient) < amount)
            throw new IllegalArgumentException("Cannot remove more than already in ingredients");
        Double newAmount = ingredients.get(ingredient) - amount;
        ingredients.put(ingredient, newAmount);
    }

    /**
     * Method to get the ingredientunit of an ingredient in the recipe
     * 
     * @param ingredient The ingredient to find the unit of
     * @return A string of the unit of the ingredient
     * 
     * @throws IllegalargumentException If the ingredient is not in the recipe
     */
    public String getIngredientUnit(String ingredient) {
        if (!ingredientUnits.containsKey(ingredient))
            throw new IllegalArgumentException();
        return ingredientUnits.get(ingredient);
    }

    /**
     * Used to set the unit of the ingredient
     * 
     * @param ingredient The ingredient to set the unit of
     * @param unit       the unit to be set
     * @throws IllegalArgumentException if unit is not pcs, g or dL
     */

    public void setIngredientUnit(String ingredient, String unit) {
        if (!(unit.equals("pcs") || unit.equals("g") || unit.equals("dL")))
            throw new IllegalArgumentException("Unit must be pcs, g or dL");
        ingredientUnits.put(ingredient, unit);
    }

    /**
     * Gets the author of the recipe
     * 
     * @return The author attribute of the recipe
     */
    public Profile getAuthor() {
        return author;
    }
}
