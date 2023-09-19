package ui.controllers;

import java.io.IOException;

import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipeController extends SuperController{

    @FXML
    private TextField nameField;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ListView<String> ingredientsView;
    @FXML
    private TextArea stepsArea;

    private Recipe recipe;

    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
    }

    public void populate() {
        nameField.setText(recipe.getName());
        descriptionArea.setText("Posted by: " + recipe.getAuthor().getUsername() + "\n" + recipe.getDescription());
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
        stepsArea.setText(s);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
