package ui;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.RecipeHubAccess;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * The AddRecipeController class is the controller class for the AddRecipe.fxml.
 * It handles the user input and creates a new recipe with the given input.
 * This class extends the SuperController class.
 *
 * @see SuperController
 */
public class AddRecipeController extends SuperController {
    private Recipe newRecipe;
    private Alert alert, duplicateRecipeAlert;
    private RecipeLibrary allRecipes;

    @FXML
    private Button addName, removeStep, backButton, removeIngredient, addIngredientButton,
            saveRecipeButten, ingredientsNextButton, addStepNext, addDescriptionButton,
            addStepButton;
    @FXML
    private Pane recipeNamePane, addIngredientPane, descriptionPane, portionAndConfirmPane,
            addStepPane;
    @FXML
    private TextField recipeName, ingredientNameInput, ingredientAmount, addStepText,
            addDescriptionText;
    @FXML
    private MenuButton ingredientPropertyMenu, numberOfPortionsMenu;
    @FXML
    private Text nameError, descriptionError, propertyError, noAddedPortionsError, amountError,
            notValidStepError, ingredientNameError, noAddedIngredientError, noAddedStepError;
    @FXML
    private Label description, name, ingredientAndSteps;

    /**
     * This method creates a new recipe when the user clicks on the "AddName"button.
     * The method takes the user input and creates a new recipe with the given name
     * if the name is not empty.
     * If the name is empty, the user will receive feedback to add a name.
     * After creating the recipe, the RecipeNamePane will disappear, and the
     * DescriptionPane will appear.
     *
     * @throws IllegalArgumentException if the createNewRecipe method throws an
     *                                  IllegalArgumentException (e.g., if the
     *                                  name is empty).
     * @see Recipe#Recipe(String, int, Profile)
     */
    public void createRecipe() throws IllegalArgumentException {
        allRecipes = currentRecipeHubAccess.getRecipeLibrary();

        if (!validateRecipeName()) {
            nameError.setVisible(true);
        } else if (allRecipes.containsRecipe(new Recipe(recipeName.getText(), 1, currentProfile))) {
            showDuplicateRecipePopup();
        } else {
            this.newRecipe = new Recipe(recipeName.getText(), 1, currentProfile);
            recipeNamePane.setVisible(false);
            name.setText(newRecipe.getName());
            descriptionPane.setVisible(true);
        }
        duplicateRecipeAlert = null;
    }

    /**
     * This method shows a pop up window if the user tries to create a recipe with
     * the same name as an existing recipe the user has created, and gives the user
     * feedback that they need to choose another name for the recipe or delete the
     * existing recipe with the same name.
     */
    private void showDuplicateRecipePopup() {
        duplicateRecipeAlert = new Alert(AlertType.INFORMATION);
        duplicateRecipeAlert.setTitle("Duplicate recipe");
        duplicateRecipeAlert.setHeaderText("Recipe already exists");
        duplicateRecipeAlert.setContentText(
                "A recipe with the name " + recipeName.getText() + " already exists." + "\n"
                        + "Please choose another name for your recipe, "
                        + "or delete the existing recipe.");
        Optional<ButtonType> okButton = duplicateRecipeAlert.showAndWait();
        if (okButton.isPresent() && okButton.get() == ButtonType.OK) {
            duplicateRecipeAlert.close();
        }
        duplicateRecipeAlert = null;
    }

    /**
     * This method is called when the user clicks the AddName button.
     * The method validates if the user has added a name to the recipe.
     *
     * @return true if the user has added a name and false if not
     *
     */
    public boolean validateRecipeName() {
        if (recipeName.getText().isBlank() || recipeName.getText().length() > 20) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method adds the user given description to the recipe and shows it to the
     * preview.
     * If there is no added description the user gets feedback to add an
     * description.
     *
     * @see Recipe#setDescription(String)
     */
    public void addDescription() {
        if (validateDescrition()) {
            newRecipe.setDescription(addDescriptionText.getText());
            description.setText(addDescriptionText.getText());
            cleanDescriptionButtons();
        } else {
            descriptionError.setVisible(true);
        }
    }

    /**
     * This method checks if there is added an description and checks that the
     * description is a string.
     *
     * @return true if the description meets the requierments, false otherwise
     */
    private boolean validateDescrition() {
        return !addDescriptionText.getText().isEmpty();
    }

    /**
     * This method removes the AddDescriptionPane and show the addIngredientPane.
     */
    private void cleanDescriptionButtons() {
        descriptionPane.setVisible(false);
        addIngredientPane.setVisible(true);
    }

    /**
     * This method validates if an added ingredient follows the requirements.
     * If one or more off the inputs does not meet the requirements the user
     * will receive feedback.
     * If the requirements are met, the ingredient will be added to the recipe.
     *
     * @see Recipe#addIngredient(String, Double, String) When the ingredient is
     *      added the inputs are cleared and it is ready to add more
     *      ingredients.
     */
    public void addIngredient() {
        if (validateIngredient()) {
            newRecipe.addIngredient(ingredientNameInput.getText(),
                    Double.parseDouble(ingredientAmount.getText()),
                    ingredientPropertyMenu.getText());
            newRecipe.setIngredientUnit(ingredientNameInput.getText(),
                    ingredientPropertyMenu.getText());
            showIngredientPreview();
            cleanIngredientButtons();
            amountError.setVisible(false);
            ingredientNameError.setVisible(false);
            propertyError.setVisible(false);
            noAddedIngredientError.setVisible(false);
            removeIngredient.setVisible(true);
        } else {
            ingredientError();
        }
    }

    /**
     * This method validates if the added ingredient meets the requirements.
     *
     * @return true if the added ingredient meets the requirements, false otherwise
     */
    private boolean validateIngredient() {
        if (!ingredientNameInput.getText().isEmpty()
                && ingredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")
                && !ingredientPropertyMenu.getText().equals("Property")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method creates a string representation of the added string.
     */
    private void showIngredientPreview() {
        String s = ingredientAndSteps.getText();
        s += newRecipe.getIngredientAmount(ingredientNameInput.getText())
                + ingredientPropertyMenu.getText() + "\t" + "\t" + ingredientNameInput.getText()
                + "\n";
        ingredientAndSteps.setText(s);
    }

    /**
     * This method is called when the added ingredient does not meet the
     * requirements and gives feedback to the user.
     */
    private void ingredientError() {
        if (ingredientNameInput.getText().isEmpty()
                || ingredientNameInput.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")) {
            ingredientNameError.setVisible(true);
        } else {
            ingredientNameError.setVisible(false);
        }

        if (!ingredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")
                || ingredientAmount.getText().isEmpty()) {
            amountError.setVisible(true);
        } else {
            amountError.setVisible(false);
        }

        if (ingredientPropertyMenu.getText().equals("Property")) {
            propertyError.setVisible(true);
        } else {
            propertyError.setVisible(false);
        }
    }

    /**
     * This method changes the text on the menubutton where the user selects a
     * property.
     *
     * @param event event when the user selects a property
     */
    public void selectProperty(ActionEvent event) {
        MenuItem selectedProperty = (MenuItem) event.getSource();
        String propertyAsText = selectedProperty.getText();
        ingredientPropertyMenu.setText(propertyAsText);
    }

    /**
     * This method cleans the input variables where the user has added an
     * ingredient.
     * This is done so the user can add more ingredients.
     */
    private void cleanIngredientButtons() {
        ingredientNameInput.deleteText(0, ingredientNameInput.getText().length());
        ingredientNameInput.setPromptText("Ingredient Name");
        ingredientAmount.deleteText(0, ingredientAmount.getText().length());
        ingredientAmount.setPromptText("Amount");
        ingredientPropertyMenu.setText("Property");
    }

    /**
     * This method checks if there is added an ingredient.
     * If there is added an ingredient the addIngredientPane will disappear and the
     * addStepPane will show up.
     * If there is not added an ingredient to the recipe, the user will get feedback
     * to add an ingredient first.
     */
    public void addedAllIngredients() {
        if (newRecipe.getIngredients().isEmpty()) {
            noAddedIngredientError.setVisible(true);
        } else {
            noAddedIngredientError.setVisible(false);
            addIngredientPane.setVisible(false);
            addStepPane.setVisible(true);
            ingredientAndSteps.setText(ingredientAndSteps.getText() + "\n" + "\n");
        }
    }

    /**
     * This method removes the last added ingredient from the recipe and the
     * preview.
     */
    public void removeIngredient() {
        List<String> ingredients = new ArrayList<>(newRecipe.getIngredients());
        String ingredient = ingredients.get(0);
        newRecipe.removeIngredient(ingredient);
        String[] s = ingredientAndSteps.getText().split("\n");
        StringBuffer newIngredientAndStepsBuffer = new StringBuffer();
        if (s.length >= 2) {
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndStepsBuffer.append(s[i] + "\n");
            }
            String newIngredientAndSteps = newIngredientAndStepsBuffer.toString();
            ingredientAndSteps.setText(newIngredientAndSteps);
        } else {
            ingredientAndSteps.setText("");
            removeIngredient.setVisible(false);
        }
    }

    /**
     * This method adds a step and shows it in the prewiew if the step meets the
     * requirements.
     * If the added step does not meet the requirements, the user gets feedback.
     */
    public void addStep() {
        if (validateStep()) {
            newRecipe.addStep(addStepText.getText());
            showStepsInPreview();
            cleanStepInput();
            notValidStepError.setVisible(false);
            noAddedStepError.setVisible(false);
            removeStep.setVisible(true);
        } else {
            notValidStepError.setVisible(true);
        }
    }

    /**
     * This method creates a string representation of the added step that will
     * show up in the preview.
     */
    private void showStepsInPreview() {
        String s = "Step " + (newRecipe.getSteps().size()) + ":" + "\t"
                + newRecipe.getSteps().get(newRecipe.getSteps().size() - 1) + "\n";
        ingredientAndSteps.setText(ingredientAndSteps.getText() + s);
    }

    /**
     * This method checks if the added step meets the requirements.
     *
     * @return true if added step meets the requirements, false otherwise
     */
    private boolean validateStep() {
        if (!addStepText.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method cleans the input variables after a step is added so the user
     * can add more steps.
     */
    private void cleanStepInput() {
        addStepText.deleteText(0, addStepText.getText().length());
        addStepText.setPromptText("Step " + (newRecipe.getSteps().size() + 1) + ":" + "\t");
    }

    /**
     * This method validates if there is added a step or not.
     * Gives feedback if there is not added any step.
     */
    public void addedAllSteps() {
        if (!newRecipe.getSteps().isEmpty()) {
            addStepsNext();
        } else if (newRecipe.getSteps().isEmpty()) {
            noAddedStepError.setVisible(true);
        }
    }

    /**
     * This method hides the addStepPane and shows the portionAndConfirmPane.
     */
    private void addStepsNext() {
        addStepPane.setVisible(false);
        portionAndConfirmPane.setVisible(true);
    }

    /**
     * This method removes the last added step from the recipe and the preview.
     */
    public void removeStep() {
        List<String> steps = newRecipe.getSteps();
        String step = steps.get(0);
        newRecipe.removeStep(step);

        String[] s = ingredientAndSteps.getText().split("\n");

        if (s.length >= 2) {
            StringBuffer newIngredientAndStepsBuffer = new StringBuffer();
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndStepsBuffer.append(s[i] + "\n");
            }
            String newIngredientAndSteps = newIngredientAndStepsBuffer.toString();
            ingredientAndSteps.setText(newIngredientAndSteps);

            if (newRecipe.getSteps().isEmpty()) {
                removeStep.setVisible(false);
            }

            cleanStepInput();
        }
    }

    /**
     * This method validates if the user has selectet an amount of portions.
     * If the user has selected an amount of portions the recipe will be saved.
     */
    public void saveRecipe() {
        if (validatePortions()) {
            newRecipe.setPortions(Integer.parseInt(numberOfPortionsMenu.getText()));
            portionAndConfirmPane.setVisible(false);
            newRecipe.setSaved(true);
            saveRecipeToLibrary();
        } else {
            noAddedPortionsError.setVisible(true);
        }
    }

    /**
     * This method uses the currentRecipeHubAccess to save the Recipe to file.
     * It also saves the recipe to the logged in profile.
     *
     * @see RecipeHubAccess#saveRecipe(Recipe)
     * @see RecipeHubAccess#saveProfile(Profile)
     */
    private void saveRecipeToLibrary() {
        currentRecipeHubAccess.saveRecipe(newRecipe);
        currentRecipeHubAccess.saveProfile(currentProfile);
    }

    /**
     * This method checks if the user has selected an amount of portions.
     *
     * @return true if portions is selected, false otherwise
     */
    private boolean validatePortions() {
        if (numberOfPortionsMenu.getText().matches("[1-9]")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method changes the text on the seletedPortions MenuItem.
     * This happens when the user clicks on an amount of portions.
     *
     * @param event event when user clicks on an amount of portions
     */
    public void selectPortions(ActionEvent event) {
        MenuItem selectedPortions = (MenuItem) event.getSource();
        String portionsAsText = selectedPortions.getText();
        numberOfPortionsMenu.setText(portionsAsText);
    }

    /**
     * This method is called when the user clicks on the back button.
     * If the recipe is saved the user will be sent back to the mainscreen.
     * If the recipe is not saved the user will get a pop up window where the
     * user can choose to go back without saving the recipe or to go back and
     * complete the recipe.
     *
     * @param event event when the user clicks on the back button
     * @throws IOException if the switchSceneMain method throws an IOException
     * @see #showAlert(ActionEvent)
     * @see SuperController#switchSceneMain(ActionEvent)
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        if (newRecipe == null || newRecipe.isSaved()) {
            switchSceneMain(event);
        } else {
            showAlert(event);
        }
    }

    /**
     * This method gives the alert that is shown when the user tries to go back
     * without saving the recipe.
     *
     * @return the alert that is shown when the user tries to go back without
     *         saving the recipe
     */
    public Alert getAlert() {
        return this.alert;
    }

    /**
     * This method gives the alert that is shown when the user tries to create a
     * recipe with the same name as an existing recipe the user has created.
     *
     * @return the alert that is shown when the user tries to create a recipe with
     *         the same name as an existing recipe the user has created
     */
    public Alert getDuplicateRecipeAlert() {
        return this.duplicateRecipeAlert;
    }

    /**
     * This method shows a pop up window where the user can choose to go back to
     * the mainscreen without saving the recipe or keep working on the recipe.
     *
     * @param event event when the user clicks on the back button
     */
    @FXML
    public void showAlert(ActionEvent event) {
        this.alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Your recipe is not saved!");
        alert.setHeaderText("Delete on-going recipe");
        alert.setContentText(
                "If you go back now your recipe will not be saved. Do you want to go back?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProfile.removeRecipe(newRecipe);
            try {
                switchSceneMain(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.alert = null;
    }
}
