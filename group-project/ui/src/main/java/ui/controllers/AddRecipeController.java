package ui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.Recipe;
import file.RecipeFilehandler;
import file.UserFilehandler;
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

public class AddRecipeController extends SuperController {
    private Recipe newRecipe;
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");
    private Alert alert;

    @FXML
    private Button addName, removeStep, backButton, removeIngredient, addIngredientButton, saveRecipeButten,
            ingredientsNextButton, addStepNext, addDescriptionButton, addStepButton;

    @FXML
    private Pane recipeNamePane, addIngredientPane, descriptionPane, portionAndConfirmPane, addStepPane;

    @FXML
    private TextField recipeName, ingredientNameInput, ingredientAmount, addStepText, addDescriptionText;

    @FXML
    private MenuButton ingredientPropertyMenu, numberOfPortionsMenu;

    @FXML
    private Text nameError, descriptionError, propertyError, noAddedPortionsError, amountError, notValidStepError,
            ingredientNameError, noAddedIngredientError, noAddedStepError;

    @FXML
    private Label description, name, ingredientAndSteps;

    /**
     * This method is called when the user clicks on the AddName button
     * The method takes the userinput and creates a new recipe with the given name
     * if the name is not empty
     * 
     * If the name is empty the user will get feedback to add a name
     * 
     * When the recipe is created the RecipeNamePane will dissapear and the
     * DescriptionPane will show up
     * 
     * @throws IllegalArgumentException if the createNewRecipe method throws an
     *                                  illegal argument exception if the name is
     *                                  empty
     * 
     * @see Recipe #Recipe(String, int, Profile)
     */
    public void createRecipe() throws IllegalArgumentException {
        if (validateRecipeName()) {
            this.newRecipe = new Recipe(recipeName.getText(), 1, currentProfile);
            recipeNamePane.setVisible(false);
            name.setText(newRecipe.getName());
            descriptionPane.setVisible(true);
        } else {
            nameError.setVisible(true);
        }
    }

    public void setFileHander(RecipeFilehandler rf, UserFilehandler uf) {
        this.recipeFilehandler = rf;
        this.userFilehandler = uf;
    }

    /**
     * This method is called when the user clicks on the AddName button
     * the method validates if the user has added a name to the recipe
     * 
     * @return true if the user has added a name and false if not
     * 
     */
    public boolean validateRecipeName() {
        if (!recipeName.getText().isBlank() && recipeName.getText().length() <= 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method adds the user given description to the recipe and show it to
     * the preview
     * 
     * If there is no added description the user get feedback to add an description
     * 
     * @see Recipe #setDescription(String)
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
     * This method checks if there is added an descrition and checks that the
     * descition is a string
     * 
     * @return true if the descrition meets the requierments and false if it does
     *         not
     */
    private boolean validateDescrition() {
        return !addDescriptionText.getText().isEmpty();
    }

    /**
     * This method removes the AddDescritionPane and show the AddIngredientPane
     */
    private void cleanDescriptionButtons() {
        descriptionPane.setVisible(false);
        addIngredientPane.setVisible(true);
    }

    /**
     * This method validates if an added ingredient follow the requierments:
     *
     * If one or more off the inputs does not mett the requierments the user will
     * recive feedback
     * 
     * If the requirements are met, the ingredient will be added to the recipe
     * 
     * @see Recipe #addIngredient(String, Double, String)
     *      When the ingredient is added the inputs are cleaned and ready to add
     *      more ingredients
     */
    public void addIngredient() {
        if (validateIngredient()) {
            newRecipe.addIngredient(ingredientNameInput.getText(), Double.parseDouble(ingredientAmount.getText()),
                    ingredientPropertyMenu.getText());
            newRecipe.setIngredientUnit(ingredientNameInput.getText(), ingredientPropertyMenu.getText());
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
     * This method validates if the added ingredient meets the requierments
     * 
     * @return true if the added ingredient meets the requierments and false if it
     *         does not
     */
    private boolean validateIngredient() {
        if (!ingredientNameInput.getText().isEmpty() && ingredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")
                && !ingredientPropertyMenu.getText().equals("Property")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method creats a string representation of the added string
     */
    private void showIngredientPreview() {
        String s = ingredientAndSteps.getText();
        s += newRecipe.getIngredientAmount(ingredientNameInput.getText()) + ingredientPropertyMenu.getText() + "\t"
                + "\t" + ingredientNameInput.getText() + "\n";
        ingredientAndSteps.setText(s);
    }

    /**
     * This method is called when the added ingredient does not meet the
     * requierments and gives feedback to the user
     */
    private void ingredientError() {
        if (ingredientNameInput.getText().isEmpty()
                || ingredientNameInput.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")) {
            ingredientNameError.setVisible(true);
        } else {
            ingredientNameError.setVisible(false);
        }

        if (!ingredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$") || ingredientAmount.getText().isEmpty()) {
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
     * property
     * 
     * @param event Event when the user selects a property
     */
    public void selectProperty(ActionEvent event) {
        MenuItem selectedProperty = (MenuItem) event.getSource();
        String PropertyAsText = selectedProperty.getText();
        ingredientPropertyMenu.setText(PropertyAsText);
    }

    /**
     * This method cleans the input variables where the user has added an ingredient
     * so the user can add more ingredients
     */
    private void cleanIngredientButtons() {
        ingredientNameInput.deleteText(0, ingredientNameInput.getText().length());
        ingredientNameInput.setPromptText("Ingredient Name");
        ingredientAmount.deleteText(0, ingredientAmount.getText().length());
        ingredientAmount.setPromptText("Amount");
        ingredientPropertyMenu.setText("Property");
    }

    /**
     * This methos checks if there is added an ingredient
     * - If there is added an ingredient the AddIngredient pane will dissapear and
     * the AddStepPane will show up
     * - If there is not added an ingredient to the recipe the user will get
     * feedback to add an ingredient first
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
     * This method removes the last added ingredient from the recipe and the preview
     */
    public void removeIngredient() {
        List<String> ingredients = new ArrayList<>(newRecipe.getIngredients());
        String ingredient = ingredients.get(0);
        newRecipe.removeIngredient(ingredient);
        String[] s = ingredientAndSteps.getText().split("\n");
        if (s.length >= 2) {
            String newIngredientAndSteps = "";
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndSteps += s[i] + "\n";
            }
            ingredientAndSteps.setText(newIngredientAndSteps);
        } else {
            ingredientAndSteps.setText("");
            removeIngredient.setVisible(false);
        }
    }

    /**
     * This method add the input step to the recipe and show it in the prewiew if
     * the step meets the requierment
     * 
     * If the added step doe not meet the requierments, the user gets feedback.
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
     * This method creates a string representation of the added step that will show
     * up in the preview
     */
    private void showStepsInPreview() {
        String s = "Step " + (newRecipe.getSteps().size()) + ":" + "\t"
                + newRecipe.getSteps().get(newRecipe.getSteps().size() - 1) + "\n";
        ingredientAndSteps.setText(ingredientAndSteps.getText() + s);
    }

    /**
     * This method checks if the added step meets the requierments
     * 
     * @return true if added step meets the requierment and false if it does not
     */
    private boolean validateStep() {
        if (!addStepText.getText().isEmpty() && addStepText.getText().matches("[a-zA-Z ,.0-9!?]+")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clean the input variables after a step is added so the user can
     * add more steps
     */
    private void cleanStepInput() {
        addStepText.deleteText(0, addStepText.getText().length());
        addStepText.setPromptText("Step " + (newRecipe.getSteps().size() + 1) + ":" + "\t");
    }

    /**
     * This method validates if there is added a step or not
     * Gives feedback if there is not added any step
     */
    public void addedAllSteps() {
        if (!newRecipe.getSteps().isEmpty()) {
            addStepsNext();
        } else if (newRecipe.getSteps().isEmpty()) {
            noAddedStepError.setVisible(true);
        }
    }

    /**
     * This method hides the AddStepPane and shows the PortionAndConfirmPane
     */
    private void addStepsNext() {
        addStepPane.setVisible(false);
        portionAndConfirmPane.setVisible(true);
    }

    /**
     * This method removes the last added step from the recipe and the preview
     */
    public void removeStep() {
        List<String> steps = newRecipe.getSteps();
        String step = steps.get(0);
        newRecipe.removeStep(step);

        String[] s = ingredientAndSteps.getText().split("\n");

        if (s.length >= 2) {
            String newIngredientAndSteps = "";
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndSteps += s[i] + "\n";
            }
            ingredientAndSteps.setText(newIngredientAndSteps);

            if (newRecipe.getSteps().isEmpty()) {
                removeStep.setVisible(false);
            }

            cleanStepInput();
        }
    }

    /**
     * This method validates if the user has selectet an amount of portions and
     * saves the recipe to file if the amount is selected
     */
    public void saveRecipe() {
        if (valdatePortions()) {
            newRecipe.setPortions(Integer.parseInt(numberOfPortionsMenu.getText()));
            portionAndConfirmPane.setVisible(false);
            newRecipe.setSaved(true);
            saveRecipeToLibrary();
        } else {
            noAddedPortionsError.setVisible(true);
        }
    }

    /**
     * This method uses the RecipeFilehandler to save the Recipe to file
     * It also saves the recipe to the logged in profile
     * 
     * @see RecipeFilehandler #writeRecipe(Recipe)
     * @see UserFilehandler #writeProfile(Profile)
     */
    private void saveRecipeToLibrary() {
        recipeFilehandler.writeRecipe(newRecipe);
        userFilehandler.writeProfile(currentProfile);
    }

    /**
     * This method checks if the user has selected an amount of portions.
     * 
     * @return boolean true if portions is selected and false if not
     */
    private boolean valdatePortions() {
        if (numberOfPortionsMenu.getText().matches("[1-9]")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method changes the text on the seletedPortions MenuItem when the user
     * selects an amount of portions
     * 
     * @param event event when user clicks on an amount of portions
     */
    public void selectPortions(ActionEvent event) {
        MenuItem selectedPortions = (MenuItem) event.getSource();
        String portionsAsText = selectedPortions.getText();
        numberOfPortionsMenu.setText(portionsAsText);
    }

    /**
     * This method is called when the user clicks on the back button
     * 
     * If the recipe is saved the user will be sent back to the mainscreen
     * 
     * If the recipe is not saved the user will get a pop up window where the user
     * can choose to go back without saving the recipe or to go back and complete
     * the recipe
     * 
     * @param event event when the user clicks on the back button
     * @throws IOException if the switchSceneWithInfo method throws an IOException
     * @see #showPopUp(ActionEvent)
     * @see SuperController #switchSceneWithInfo(ActionEvent, String, Profile)
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        if (newRecipe == null || newRecipe.isSaved()) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
        } else {
            showAlert(event);
        }
    }

    public Alert getAlert() {
        return this.alert;
    }


    /**
     * This method shows a pop up window where the user can choose to go back
     * to the mainscreen without saving the recipe or keep working on the recipe
     * 
     * @param event
     */
    @FXML
    public void showAlert(ActionEvent event) {
        this.alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Your recipe is not saved!");
        alert.setHeaderText("Delete on-going recipe");
        alert.setContentText("If you go back now your recipe will not be saved. Do you want to go back?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProfile.removeRecipe(newRecipe);
            try {
                switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.alert = null;
    }
}
