package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for storing several recipes.
 */
public class RecipeLibrary implements Iterable<Recipe> {

    private List<Recipe> recipes;

    /**
     * This contructor initilizes an empty list.
     */
    public RecipeLibrary() {
        recipes = new ArrayList<>();
    }

    /**
     * This contructor initilizes the recipe list using the list sent in.
     * 
     * @param recipes list filled with recipes
     */
    public RecipeLibrary(List<Recipe> recipes) {
        if (recipes == null) {
            throw new IllegalArgumentException("Recipes cannot be null");
        }
        this.recipes = new ArrayList<>(recipes);
    }

    /**
     * This metod returns the recipe with the given index.
     * 
     * @param n index of the given recipe
     * @return The recipe with index n in recipes
     * @throws IllegalArgumentException if the index is invalid
     */
    public Recipe getRecipe(int n) {
        if (n < 0 || n >= getSize()) {
            throw new IllegalArgumentException("The index is invalid");
        }
        return recipes.get(n);
    }

    /**
     * This method gets the amount of recipes in the recipe library.
     * 
     * @return Integer value with the amount of recipes
     */
    public int getSize() {
        return recipes.size();
    }

    /**
     * This method adds the recipe to recipe library.
     * 
     * @param recipe recipe to be added
     */
    private void addRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Can't add null to the RecipeLibrary");
        }
        recipes.add(recipe);
    }

    /**
     * This mehtod updates a recipe if it exists, or adds it if it doesn't.
     * 
     * @param recipe recipe to be updated or added
     * @see #containsRecipe(Recipe)
     * @see #removeRecipe(Recipe)
     * @see #addRecipe(Recipe)
     */
    public void putRecipe(Recipe recipe) {
        if (containsRecipe(recipe)) {
            removeRecipe(recipe);
        }
        addRecipe(recipe);
    }

    /**
     * This method removes the given recipe from the recipe library.
     * 
     * @param recipe recipe to be removed
     */
    public void removeRecipe(Recipe recipe) {
        for (Recipe r : recipes) {
            if (r.getName().equals(recipe.getName()) && r.getAuthor().equals(recipe.getAuthor())) {
                recipes.remove(r);
                return;
            }
        }
    }

    /**
     * This method checks if the given recipe exists in the recipe library.
     * 
     * @param recipe recipe to check
     * @return Boolean value, true if the recipe exists in the recipe library, false otherwise
     */
    public boolean containsRecipe(Recipe recipe) {
        return recipes.stream()
                .anyMatch(r -> (r.getName().equals(recipe.getName())
                        && r.getAuthor().equals(recipe.getAuthor())));
    }

    /**
     * This method sets the recipe library to the given list of recipes.
     * 
     * @param newRecipes list of recipes you wish to set the recipe library to
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
     * @return Iterator which iterates over the elements of the recipe library
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
