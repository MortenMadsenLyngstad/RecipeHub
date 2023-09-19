package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import file.AddRecipe_filehandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AddRecipeController extends SuperController{
    private Recipe newRecipe; 
    private AddRecipe_filehandler addRecipe_filehandler = new AddRecipe_filehandler();

    @FXML
    private Button BackButton, AddName, AddIngredientButton, IngredientsNextButton, AddDescriptionButton, AddStepButton,
            AddStepNext, SaveRecipeButten;

    @FXML
    private Pane RecipeNamePane, AddIngredientPane, DescriptionPane, AddStepPane, PortionAndConfirmPane;

    @FXML
    private TextField RecipeName, IngredientNameInput, IngredientAmount, AddDescriptionText, AddStepText;

    @FXML
    private MenuButton IngredientPropertyMenu, NumberOfPortionsMenu;

    @FXML
    private Text Title, Ingredients, Description, NameError, DescriptionError, PropertyError, AmountError,
            IngredientNameError, NoAddedIngredientError, NoAddedStepError, NotValidStepError, NoAddedPortionsError;

    /**
     * Takes userinput when user gives a new dish a name
     * 
     *
     * @throws IOException if the SwitchController.switchToMainScreen method throws
     *                     an exception
     * @see SwitchController#switchSceneMain(ActionEvent, String)
     */
    public void validateNewRecipeName(ActionEvent event) throws IOException {
        if (!RecipeName.getText().isBlank()) {
            createNewRecipe(RecipeName.getText());
            RecipeNamePane.setVisible(false);
            Title.setText(newRecipe.getName());
            DescriptionPane.setVisible(true);
            /** Show the recipename as a title in the textarea on the right side */
        } else {
            NameError.setVisible(true);
        }
    }

    private void createNewRecipe(String newRecipeName) {
        this.newRecipe = new Recipe(newRecipeName, 1, currentProfile);
    }

    public void validateAddedIngredient() throws IOException {
        if (!IngredientNameInput.getText().isEmpty() && IngredientAmount.getText().matches("^[1-9]\\d*$")
                && !IngredientPropertyMenu.getText().equals("Property")) {
            newRecipe.addIngredient(IngredientNameInput.getText(), Double.parseDouble(IngredientAmount.getText()),
                    IngredientPropertyMenu.getText());
            newRecipe.setIngredientUnit(IngredientNameInput.getText(), IngredientPropertyMenu.getText());
            createIngredientString();
            cleanIngredientButtons();
            AmountError.setVisible(false);
            IngredientNameError.setVisible(false);
            PropertyError.setVisible(false);
            NoAddedIngredientError.setVisible(false);
        } else {
            if (IngredientNameInput.getText().isEmpty() || IngredientNameInput.getText().matches("^[1-9]\\d*$")) {
                IngredientNameError.setVisible(true);
            } else {
                IngredientNameError.setVisible(false);
            }

            if (!IngredientAmount.getText().matches("^[1-9]\\d*$") || IngredientAmount.getText().isEmpty()) {
                AmountError.setVisible(true);
            } else {
                AmountError.setVisible(false);
            }

            if (IngredientPropertyMenu.getText().equals("Property")) {
                PropertyError.setVisible(true);
            } else {
                PropertyError.setVisible(false);
            }
        }
    }

    private void createIngredientString() {
        String s = Ingredients.getText();
        s += IngredientNameInput.getText() + "\t" + IngredientAmount.getText() + IngredientPropertyMenu.getText();
        s += "\n";
        Ingredients.setText(s);
    }

    public void addedAllIngredients() throws IOException {
        if (newRecipe.getIngredients().isEmpty()) {
            NoAddedIngredientError.setVisible(true);
        } else {
            NoAddedIngredientError.setVisible(false);
            AddIngredientPane.setVisible(false);
            AddStepPane.setVisible(true);
            Ingredients.setText(Ingredients.getText() + "\n");
        }

    }

    public void selectProperty(ActionEvent event) {
        MenuItem selectedProperty = (MenuItem) event.getSource();
        String PropertyAsText = selectedProperty.getText();
        IngredientPropertyMenu.setText(PropertyAsText);
    }

    private void cleanIngredientButtons() {
        IngredientNameInput.deleteText(0, IngredientNameInput.getText().length());
        IngredientNameInput.setPromptText("Ingredient Name");
        IngredientAmount.deleteText(0, IngredientAmount.getText().length());
        IngredientAmount.setPromptText("Amount");
        IngredientPropertyMenu.setText("Property");
    }

    public void AddDescription() throws IOException {
        if (validateDescrition()) {
            newRecipe.setDescription(AddDescriptionText.getText());
            Description.setText(AddDescriptionText.getText());
            cleanDescriptionButtons();
        } else {
            DescriptionError.setVisible(true);
        }
    }

    private boolean validateDescrition() {
        if (!AddDescriptionText.getText().isEmpty() && !AddDescriptionText.getText().matches("-?\\d+(\\.\\d+)?")) {
            return true;
        } else {
            return false;
        }
    }

    private void cleanDescriptionButtons() {
        DescriptionPane.setVisible(false);
        AddIngredientPane.setVisible(true);
    }

    public void addStep() {
        if (validateStep()) {
            newRecipe.addStep(AddStepText.getText());
            createStepString();
            cleanStepInput();
            NotValidStepError.setVisible(false);
            NoAddedStepError.setVisible(false);
        } else {
            NotValidStepError.setVisible(true);
        }
    }

    private void createStepString() {
        String s = Ingredients.getText();
        s += "Step " + (newRecipe.getSteps().size()) + ":" + "\t"
                + newRecipe.getSteps().get(newRecipe.getSteps().size() - 1) + "\n";
        Ingredients.setText(s);
    }

    private boolean validateStep() {
        if (!AddStepText.getText().isEmpty() && !AddStepText.getText().matches("-?\\d+(\\.\\d+)?")) {
            return true;
        } else {
            return false;
        }
    }

    private void cleanStepInput() {
        AddStepText.deleteText(0, AddStepText.getText().length());
        AddStepText.setPromptText("Step " + (newRecipe.getSteps().size() + 1) + ":" + "\t");
    }

    public void addedAllSteps() {
        if (!newRecipe.getSteps().isEmpty()) {
            addStepsNext();
        } else if (newRecipe.getSteps().isEmpty()) {
            NoAddedStepError.setVisible(true);
        }
    }

    private void addStepsNext() {
        AddStepPane.setVisible(false);
        PortionAndConfirmPane.setVisible(true);
    }

    public void SaveRecipe() {
        if (valdatePortions()) {
            newRecipe.setPortions(Integer.parseInt(NumberOfPortionsMenu.getText()));
            PortionAndConfirmPane.setVisible(false);
            saveRecipeToLibrary();
        } else {
            NoAddedPortionsError.setVisible(true);
        }
    }

    private void saveRecipeToLibrary() {
        currentProfile.addRecipe(newRecipe);
    }

    private boolean valdatePortions() {
        if (NumberOfPortionsMenu.getText().matches("[1-9]")) {
            return true;
        } else {
            return false;
        }
    }

    public void selectPortions(ActionEvent event) {
        MenuItem selectedPortions = (MenuItem) event.getSource();
        String portionsAsText = selectedPortions.getText();
        NumberOfPortionsMenu.setText(portionsAsText);
    }

    /**
     * Logs the user in if the login information is correct
     * 
     * @param event
     * @throws Exception   if the validateLogin method throws an exception
     * @throws IOException if the switchToMainScreen method throws
     *                     an exception
     * @see SwitchController#switchSceneMain(ActionEvent, String)
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
    }
}
