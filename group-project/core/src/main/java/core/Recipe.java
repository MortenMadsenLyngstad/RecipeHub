package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recipe {
    private List<String> steps;
    private int portions;
    private String name;
    private String description;
    private Map<String, Double> ingredients;
    private Map<String, String> ingredientUnits;
    private Profile author;

    public Recipe(String name, int portions) {
        setName(name);
        setPortions(portions);
        ingredients = new HashMap<>();
        steps = new ArrayList<>();
        ingredientUnits = new HashMap<>();
    }

    public void setName(String name) {
        if (name.equals(""))
            throw new IllegalArgumentException("Name cannot be an empty String");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPortions(int portions) {
        if (portions < 1)
            throw new IllegalArgumentException("Portions must be at least one");
        this.portions = portions;
    }

    public int getPortions() {
        return portions;
    }

    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    public void addStep(String step) {
        steps.add(step);
    }

    public void removeStep(String step) {
        if (!steps.remove(step))
            throw new IllegalArgumentException("Step not found in steps");
    }

    public Set<String> getIngredients() {
        return new HashSet<>(ingredients.keySet());
    }

    public Double getIngredientAmount(String ingredient) {
        if (!ingredients.containsKey(ingredient))
            throw new IllegalArgumentException("Ingredient not found in recipe");
        return ingredients.get(ingredient);
    }

    public void addIngredient(String ingredient, Double amount, String unit) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be over 0");
        }
        if (ingredients.keySet().contains(ingredient)) {
            Double newAmount = ingredients.getOrDefault(ingredient, 0.0) + amount;
            ingredients.put(ingredient, newAmount);
        } else {
            ingredients.put(ingredient, amount);
            ingredientUnits.put(ingredient, unit);
        }
    }

    public void removeIngredient(String ingredient) {
        if (!ingredients.containsKey(ingredient))
            throw new IllegalArgumentException("Ingredient not found in recipe");
        else {
            ingredients.remove(ingredient);
            ingredientUnits.remove(ingredient);
        }
    }

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

    public String getIngredientUnit(String ingredient) {
        return ingredientUnits.get(ingredient);
    }

    public void setAuthor(Profile author) {
    }

    public Profile getAuthor() {
        return author;
    }

    public static void main(String[] args) {
    }

}
