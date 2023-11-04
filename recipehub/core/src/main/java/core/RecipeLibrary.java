package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to contain several recipes.
 * 
 * @author Adrian Haabpiht Solberg
 */
public class RecipeLibrary implements Iterable<Recipe> {

    private List<Recipe> recipes;

    /**
     * This contructor initilizes an empty ArrayList.
     */
    public RecipeLibrary() {
        recipes = new ArrayList<>();
    }

    /**
     * This contructor initilizes a new ArrayList using the ArrayList sent in.
     * 
     * @param recipes - Arraylist filled with recipes
     */
    public RecipeLibrary(ArrayList<Recipe> recipes) {
        if (recipes == null) {
            throw new IllegalArgumentException("Recipes cannot be null");
        }
        this.recipes = new ArrayList<>(recipes);
    }

    /**
     * This metod returns the recipe with the given index.
     * 
     * @param n - The index of the given recipe
     * @throws IllegalArgumentException if the index is invalid
     * @return The recipe with index n in recipes
     */
    public Recipe getRecipe(int n) {
        if (n < 0 || n >= getSize()) {
            throw new IllegalArgumentException("The index is invalid");
        }
        return recipes.get(n);
    }

    /**
     * Method to get the amount of recipes in the RecipeLibrary.
     * 
     * @return Integer value with the amount of recipes
     */
    public int getSize() {
        return recipes.size();
    }

    /**
     * This method will add the recipe to the ArrayList recipes.
     * 
     * @param recipe - Recipe object you wish to add
     */
    public void addRecipe(Recipe recipe) {
        if (containsRecipe(recipe)) {
            throw new IllegalArgumentException("The recipe is already added");
        }
        if (recipe == null) {
            throw new IllegalArgumentException("Can't add null");
        }
        recipes.add(recipe);
    }

    /**
     * This method will remove thee given recipe from the ArrayList recipes.
     * 
     * @param recipe - Recipe object you wish to remove
     * @throws IllegalArgumentException if the recipe given does not exist in
     *                                  recipes
     */
    public void removeRecipe(Recipe recipe) {
        for (Recipe r : recipes) {
            if (r.getName().equals(recipe.getName()) && r.getAuthor().equals(recipe.getAuthor())) {
                recipes.remove(r);
                return;
            }
        }
        throw new IllegalArgumentException("The recipe does not exist");
    }

    /**
     * This method will check if the given recipe exists in the ArrayList recipes.
     * 
     * @param recipe - Recipe object you wish to check
     * @return Boolean value, true if the recipe exists in recipes, false if not
     */
    public boolean containsRecipe(Recipe recipe) {
        return recipes.stream()
                .anyMatch(r -> (r.getName().equals(recipe.getName())
                        && r.getAuthor().equals(recipe.getAuthor())));
    }

    /**
     * This method sets the recipe library to the given list of recipes.
     * @param newRecipes - List of recipes you wish to set the recipe library to
     */
    public void setRecipeLibrary(List<Recipe> newRecipes) {
        if (newRecipes == null) {
            throw new IllegalArgumentException("Recipes cannot be null");
        }
        recipes = newRecipes;
    }

    /**
     * This method defines an iterator for the class, which makes it iterable.
     * 
     * @return Iterator which iterates over the elements in recipes
     */
    @Override
    public Iterator<Recipe> iterator() {
        return recipes.iterator();
    }

    /**
     * This method copies the recipes into a new list.
     * 
     * @return List with all the recipes
     */
    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}
