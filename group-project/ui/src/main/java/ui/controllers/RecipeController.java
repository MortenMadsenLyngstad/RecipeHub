package ui.controllers;

import java.io.IOException;

import core.Recipe;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.RecipeFilehandler;
import file.UserFilehandler;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen
 */
public class RecipeController extends SuperController {
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");
    private Recipe recipe;

    @FXML
    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionText, stepsText, ingredientsText;
    @FXML
    private FontAwesomeIconView deleteButton, heartButton;
    @FXML
    private FontAwesomeIconView deleteButton;


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
        // If the recipe is the users own, show the delete button
        if (recipe.getAuthor().equals(currentProfile.getUsername())) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }

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

    /**
     * Handels the click of the deleteButton
     * 
     * @param event The click of the deleteButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void deleteRecipe(MouseEvent event) throws IOException {
        confirmationPopUp(event);
    }

    /**
     * Creates a pop up window to confirm the deletion of a recipe
     * 
     * @param event The click of the deleteButton
     */
    private void confirmationPopUp(MouseEvent event) {
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Delete this recipe?");

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Label label = new Label("If you delete this recipe, it will be gone forever. Are you sure?");
        Button noButton = new Button("No");
        noButton.setOnAction(e -> popUpStage.close());

        Button yesButton = new Button("Yes");

        yesButton.setOnAction(e -> {
            try {
                recipeFilehandler.removeRecipe(recipe);
                userFilehandler.removeRecipe(currentProfile, recipe);
                // TODO: Make switch to mainscreen work
                // switchSceneWithInfo(new ActionEvent(yesButton, yesButton), "Mainscreen.fxml", currentProfile);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            popUpStage.close();
        });

        vbox.getChildren().addAll(label, yesButton, noButton);

        Scene scene = new Scene(vbox);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }
}
