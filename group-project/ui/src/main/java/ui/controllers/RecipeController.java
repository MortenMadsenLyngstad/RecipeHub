package ui.controllers;

import java.io.IOException;

import core.Recipe;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen
 */
public class RecipeController extends SuperController {

    @FXML
    private Label nameField;
    @FXML
    private Button backButton;
    @FXML
    private Label authorLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ListView<String> ingredientsView;
    @FXML
    private Label stepsLabel;

    private Recipe recipe;

    /**
     * Handels the click of the backButton, sends the user back to the mainscreen
     * 
     * @param event The click of the backButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
    }

    /**
     * Populates the RecipeScreen with the information from the recipe
     */
    public void populate() {
        nameField.setText(recipe.getName());
        authorLabel.setText("Posted by: " + recipe.getAuthor());
        descriptionLabel.setText(recipe.getDescription() + "\nMakes " + recipe.getPortions() + " portions \n  ");
        for (String ingredient : recipe.getIngredients()) {
            ingredientsView.getItems().add(
                    recipe.getIngredientAmount(ingredient) + " " + recipe.getIngredientUnit(ingredient) + " : "
                            + ingredient);
        }
        ingredientsView.prefHeightProperty().bind(Bindings.size(ingredientsView.getItems()).multiply(25));
        int i = 1;
        String s = "\n";
        for (String step : recipe.getSteps()) {
            s += "Step " + i + ".  " + step + "\n";
            i++;
        }
        stepsLabel.setText(s);
    }

    /**
     * Sets the recipe for the controller
     * 
     * @param recipe The recipe to be set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
