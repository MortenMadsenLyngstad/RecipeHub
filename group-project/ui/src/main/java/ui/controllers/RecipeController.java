package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipeController extends AbstractController{

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

    public RecipeController(Recipe recipe) {
        this.recipe = recipe;
    }

    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneMain(event, "Mainscreen.fxml");
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

    @Override
    protected void currentProfile(Profile profile) {
        currentProfile = null;
    }
}
