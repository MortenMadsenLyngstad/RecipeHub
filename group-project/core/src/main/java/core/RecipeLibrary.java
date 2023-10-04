package core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is used to contain several recipes
 * @author Adrian Haabpiht Solberg
 */
public class RecipeLibrary implements Iterable<Recipe> {
    
    private ArrayList<Recipe> recipes;

    /**
     * This contructor initilizes an empty ArrayList
     */
    public RecipeLibrary() {
        recipes = new ArrayList<>();
    }

    /**
     * This contructor initilizes a new ArrayList using the ArrayList sent in
     * @param recipes - Arraylist filled with recipes
     */
    public RecipeLibrary(ArrayList<Recipe> recipes) {
        this.recipes = new ArrayList<>(recipes);
    }

    /**
     * This metod returns the recipe with the given index
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
     * This method returns the amount of recipes
     * @return Integer value with the amount of recipes
     */
    public int getSize() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    /**
     * This method will add the recipe to the ArrayList recipes
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
     * This method will remove thee given recipe from the ArrayList recipes
     * @param recipe - Recipe object you wish to remove
     * @throws IllegalArgumentException if the recipe given does not exist in recipes
     */
    public void removeRecipe(Recipe recipe) {
        recipes.stream()
        .filter((r) -> (recipe.getName().equals(r.getName()) && recipe.getAuthor().equals(r.getAuthor())))
        .findFirst().ifPresentOrElse((r) -> recipes.remove(r), () -> {
            throw new IllegalArgumentException("The recipe does not exist in the RecipeLibrary");
        });
    }

    public boolean containsRecipe(Recipe recipe) {
        return recipes.stream()
        .anyMatch(r -> (r.getName().equals(recipe.getName()) && r.getAuthor().equals(recipe.getAuthor())));
    }

    /**
     * This method defines an iterator for the class, which makes it iterable
     * @return Iterator which iterates over the elements in recipes
     */
    @Override
    public Iterator<Recipe> iterator() {
        return recipes.iterator();
    }
}
