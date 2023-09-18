package ui.controllers;

import java.io.IOException;

import core.Recipe;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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

    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneMain(event, "Mainscreen.fxml");
    }

    public void populate() {
        nameField.setText(recipe.getName());
        authorLabel.setText("Posted by: " + recipe.getAuthor().getUsername());
        descriptionLabel.setText(recipe.getDescription());
        for (String ingredient : recipe.getIngredients()) {
            ingredientsView.getItems().add(
                    recipe.getIngredientAmount(ingredient) + " " + recipe.getIngredientUnit(ingredient) + " : "
                            + ingredient);
        }
        ingredientsView.prefHeightProperty().bind(Bindings.size(ingredientsView.getItems()).multiply(25));
        int i = 1;
        String s = "";
        for (String step : recipe.getSteps()) {
            s += i + ".  " + step + "\n";
            i++;
        }
        stepsLabel.setText(s);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
