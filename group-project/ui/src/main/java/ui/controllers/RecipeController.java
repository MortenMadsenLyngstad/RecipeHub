package ui.controllers;

import java.io.IOException;

import core.Recipe;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.RecipeFilehandler;
import file.UserFilehandler;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
