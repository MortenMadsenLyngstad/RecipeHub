package ui.controllers;

import core.Profile;
import core.Recipe;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen.
 */
public class RecipeController extends SuperController {
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");
    private MainscreenController mainscreenController = new MainscreenController();
    private Recipe recipe;
    private Boolean flag = false;
    private Alert alert;
    private double scale = 1.0;
    private int portions = 0;

    @FXML
    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionText, stepsText, ingredientsText;
    @FXML
    private TextField portionsField;
    @FXML
    private FontAwesomeIconView deleteButton, heartButton, minusButton, plusButton;

    /**
     * Handels the click of the backButton, sends the user back to the mainscreen.
     * 
     * @param event The click of the backButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
    }

    /**
     * Populates the RecipeScreen with the information from the recipe.
     */
    public void populate() {
        // If the recipe is the users own, show the delete button
        showDeleteButton();

        mainscreenController.setHeart(heartButton, recipe, currentProfile);
        nameField.setText(recipe.getName());
        authorLabel.setText("Posted by: " + recipe.getAuthor());
        portionsField.setText("" + portions);
        descriptionText.setText(recipe.getDescription());

        List<String> steps = recipe.getSteps();

        for (int i = 1; i < steps.size() + 1; i++) {
            stepsText.appendText("Step " + i + ":  " + steps.get(i - 1) + "\n");
        }
        for (String ingredient : recipe.getIngredients()) {
            String amount = String.format("%.1f", recipe.getIngredientAmount(ingredient) * scale);
            ingredientsText.appendText(amount
                    + " " + recipe.getIngredientUnit(ingredient)
                    + " : " + ingredient + "\n");
        }
        descriptionText.positionCaret(0);
        stepsText.positionCaret(0);
        ingredientsText.positionCaret(0);

        descriptionText.setEditable(false);
        stepsText.setEditable(false);
        ingredientsText.setEditable(false);
    }

    /**
     * Handles the adding of portions through writing in the portionsField.
     * Portions must be an int larger than 0.
     */

    public void handlePortions() {
        int por = 0;
        String fault = "" + portions;
        try {
            por = Integer.parseInt(portionsField.getText());
        } catch (Exception e) {
            portionsField.setText(fault);
            portionsField.positionCaret((fault).length());
        }
        if (por <= 0 || por > 50) {
            portionsField.setText(fault);
            portionsField.positionCaret((fault).length());
        } else {
            portions = por;
            scale = scale();
            clearText();
            populate();
        }
    }

    /**
     * Clears the text from stepsText and ingredientsText to avoid dupliucates.
     * Intended use with scale operations.
     */

    private void clearText() {
        ingredientsText.setText("");
        stepsText.setText("");
    }

    /**
     * Handles the click of the plus button to add a portion to the recipe.
     */
    public void plusButtonClick() {
        portions += 1;
        scale = scale();
        clearText();
        populate();
    }

    /**
     * Handles the click of the minus button to remove a portion from the recipe.
     * Portions may not be 0 or less.
     */
    public void minusButtonClick() {
        if (portions == 1) {
            return;
        }
        portions -= 1;
        scale = scale();
        clearText();
        populate();
    }

    /**
     * Computes a scalar for scaling the recipe.
     * 
     * @return A double to set as the scalar
     */
    private double scale() {
        return ((double) portions / (double) recipe.getPortions());
    }

    /**
     * Sets the recipe for the controller.
     * 
     * @param recipe The recipe to be set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        portions = recipe.getPortions();
    }

    /**
     * Method for showing the delete button or not.
     * Will show the delete button if the user is the author of the recipe.
     */
    private void showDeleteButton() {
        if (recipe.getAuthor().equals(currentProfile.getUsername())) {
            deleteButton.setVisible(true);
            deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStrokeWidth(0.7);
            });
            deleteButton.setOnMouseExited(event -> {
                deleteButton.setStrokeWidth(0.2);
                ;
            });
        } else {
            deleteButton.setVisible(false);
        }
    }

    /**
     * Handels the click of the deleteButton.
     * 
     * If the user confirms the deletion, the recipe is deleted and the user is sent
     * back to the mainscreen.
     * 
     * @param event The click of the deleteButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void deleteRecipe(MouseEvent event) throws IOException {
        if (showAlert()) {
            ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
            try {
                switchSceneWithInfo(actionEvent, "Mainscreen.fxml", currentProfile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Sets the current profile for the controller.
     * 
     * @return The current Alert
     */
    public Alert getAlert() {
        return this.alert;
    }

    /**
     * Shows an alert to the user to confirm the deletion of the recipe.
     * 
     * @return Boolean value for if the user confirmed the deletion
     */
    @FXML
    public boolean showAlert() {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete this recipe?");
        alert.setHeaderText("Delete recipe");
        alert.setContentText("If you delete this recipe, it will be gone forever. Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            recipeFilehandler.removeRecipe(recipe);
            // If another profile has the removed recipe favorited it is also removed from
            // their favorites
            List<Profile> profiles = new ArrayList<>();
            for (Profile p : userFilehandler.readProfiles()) {
                if (p.getUsername().equals(currentProfile.getUsername())) {
                    currentProfile.removeFavorite(recipe);
                    currentProfile.removeRecipe(recipe);
                    profiles.add(currentProfile);
                } else {
                    p.removeFavorite(recipe);
                    profiles.add(p);
                }
            }
            userFilehandler.writeAllProfiles(profiles);
            flag = true;
        } else {
            flag = false;
        }
        this.alert = null;
        return flag;
    }

    /**
     * Sets the filehandlers for the controller.
     * 
     * @param mockRecipeFilehandler The mock RecipeFilehandler to be set
     * @param mockUserFilehandler   The mock UserFilehandler to be set
     */
    public void setFilehandlers(RecipeFilehandler mockRecipeFilehandler,
            UserFilehandler mockUserFilehandler) {
        this.recipeFilehandler = mockRecipeFilehandler;
        this.userFilehandler = mockUserFilehandler;
        mainscreenController.setFilehandlers(recipeFilehandler, userFilehandler);
    }
}
