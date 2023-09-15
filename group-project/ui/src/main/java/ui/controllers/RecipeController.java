package ui.controllers;

import java.io.IOException;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipeController {
    private SwitchController SwitchController = new SwitchController();

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

    public void backButtonClick(ActionEvent event) throws IOException {
        SwitchController.switchSceneMain(event, "Mainscreen.fxml");
    }

    public void populate(Recipe r) {
        nameField.setText(r.getName());
        descriptionArea.setText("Posted by: " + r.getAuthor().getUsername() + "\n" + r.getDescription());
        for (String ingredient : r.getIngredients()) {
            ingredientsView.getItems().add(
                    r.getIngredientAmount(ingredient) + " " + r.getIngredientUnit(ingredient) + " : " + ingredient);
        }
        int i = 1;
        String s = "";
        for (String step : r.getSteps()) {
            s += i + ".  " + step + "\n";
            i++;
        }
        stepsArea.setText(s);
    }
}
