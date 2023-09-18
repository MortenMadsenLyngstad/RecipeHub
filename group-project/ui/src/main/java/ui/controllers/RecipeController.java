package ui.controllers;

import java.io.IOException;

import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class RecipeController extends SuperController {

    @FXML
    private TextField nameField;
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
