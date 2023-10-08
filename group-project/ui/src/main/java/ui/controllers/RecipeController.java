package ui.controllers;

import java.io.IOException;

import core.Recipe;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen
 */
public class RecipeController extends SuperController {

    @FXML
    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionText, stepsText, ingredientsText;
    @FXML
    private FontAwesomeIconView deleteButton, heartButton;

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
        portionsLabel.setText("Portions: " + recipe.getPortions());
        descriptionText.setText(recipe.getDescription());
        for (int i = 1; i < recipe.getSteps().size() + 1; i++) {
            stepsText.appendText("Step " + i + ":  " + recipe.getSteps().get(i - 1) + "\n");
        }
        for (String ingredient : recipe.getIngredients()) {
            ingredientsText.appendText(
                recipe.getIngredientAmount(ingredient) + " " +
                recipe.getIngredientUnit(ingredient) + " : " +
                ingredient + "\n"
            );
        }
        descriptionText.positionCaret(0);
        stepsText.positionCaret(0);
        ingredientsText.positionCaret(0);
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
