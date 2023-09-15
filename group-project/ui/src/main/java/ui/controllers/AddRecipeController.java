package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddRecipeController {
    private Recipe newRecipe;
    private SwitchController switchController = new SwitchController();

    @FXML
    private Button BackButton;

    @FXML
    private TextField RecipeName;

    @FXML
    private Button AddName;

    @FXML
    private Text NameText;

    @FXML
    private Text IngredientsText;

    @FXML private TextField IngredientNameInput;
    

    /**
     * Logs the user in if the login information is correct
     * 
     * @param event
     * @throws Exception   if the validateLogin method throws an exception
     * @throws IOException if the SwitchController.switchToMainScreen method throws
     *                     an exception
     * @see SwitchController#switchSceneMain(ActionEvent, String)
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        switchController.switchSceneMain(event, "Mainscreen.fxml");
    }

    private void createNewRecipe(String newRecipeName) {
        Profile profile = new Profile("Username", "Password");
        newRecipe = new Recipe(newRecipeName, 1, profile);
    }

    public void validateNewRecipeName() throws IOException {
        if (!RecipeName.getText().isBlank()) {
            createNewRecipe(RecipeName.getText());
            AddName.setVisible(false);
            NameText.setVisible(false);
            IngredientsText.setVisible(true);

        }
    }

}
