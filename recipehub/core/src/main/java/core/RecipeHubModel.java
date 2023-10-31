package core;

import java.util.ArrayList;
import java.util.List;

public class RecipeHubModel {

    private RecipeLibrary recipeLibrary;
    private List<Profile> profiles;

    public RecipeHubModel() {
        recipeLibrary = new RecipeLibrary();
        profiles = new ArrayList<>();
    }

    public RecipeHubModel(RecipeLibrary recipeLibrary, List<Profile> profiles) {
        this.recipeLibrary = recipeLibrary;
        this.profiles = new ArrayList<>(profiles);
    }

    public Recipe getRecipe(String name) {
        return recipeLibrary.getRecipes().stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No recipe with this name"));
    }

    public void removeRecipe(Recipe recipe) {
        recipeLibrary.removeRecipe(recipe);
    }

    public void addRecipe(Recipe recipe) {
        recipeLibrary.addRecipe(recipe);
    }

    public boolean containsRecipe(Recipe recipe) {
        return recipeLibrary.containsRecipe(recipe);
    }

    public RecipeLibrary getRecipeLibrary() {
        return recipeLibrary;
    }

    public Profile getProfile(String username) {
        return profiles.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No profile with this name"));
    }

    public void addProfile(Profile profile) {
        if (containsProfile(profile)) {
            throw new IllegalArgumentException("Profile already added");
        }
        profiles.add(profile);
    }

    public Profile putProfile(Profile profile) {
        int index = profiles.stream()
                .filter(p -> p.getUsername().equals(profile.getUsername()))
                .findFirst()
                .map(p -> profiles.indexOf(p)).orElse(-1);
        if (index >= 0) {
            Profile oldProfile = profiles.get(index);
            profiles.set(index, profile);
            return oldProfile;
        } else {
            profiles.add(profile);
            return null;
        }
    }

    public boolean containsProfile(Profile profile) {
        return profiles.stream().anyMatch(p -> p.getUsername().equals(profile.getUsername()));
    }

    public List<Profile> getProfiles() {
        return new ArrayList<>(profiles);
    }
}
