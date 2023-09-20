package ui.controllers;

import java.io.IOException;

import core.Recipe;
import file.AddRecipeFilehandler;
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
    private AddRecipeFilehandler addRecipe_filehandler = new AddRecipeFilehandler("/addedRecipes.ser");

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
     * Takes userinput when a user wants to create a new recipe.
     * Validates if the name is a string and gives feedback to the user if the input
     * is empty.
     * If the input is not empty, a new recipe is created with the given input name.
     * When the new recipe is created the recipe pane will dissapear, so its not
     * possible to give the
     * recipe a new name.
     * 
     * Then the given input name will show up on the right side where there will
     * show up a preview of the recipe
     * Then the AddDescriptionPane will show up
     *
     * @throws IllegalArgumentException if the Reciep.setName method throws
     *                                  anexception
     * 
     * @see Recipe #setName(String)
     */

    public void validateNewRecipeName() throws IllegalArgumentException {
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

    /**
     * This method creates a new recipe with the name the user have set to the
     * recipe.
     * Also registers the new recipe to the logged in profile.
     * 
     * @param newRecipeName - The string which is set as name by the user, cannot be
     *                      empty
     * 
     * @throws IllegalArgumentException if the Reciep.setName method throws
     *                                  anexception
     * 
     * @see Recipe #setName(String)
     */
    private void createNewRecipe(String newRecipeName) throws IllegalArgumentException {
        this.newRecipe = new Recipe(newRecipeName, 1, currentProfile);
    }

    /**
     * This method validates if an added ingredient follow the requierments:
     *  - The ingredient name cannot be empty
     *  - The amount must be an int
     *  - There must be a selected property
     * 
     * If one or more off the inputs does not mett the requierments the user will recive feedback
     * 
     * If the requirements are met, the ingredient will be added to the recipe
     * @see Recipe.addIngredient(String, Double, String)
     * When the ingredient is added the inputs are cleaned and ready to add more ingredients
     * 
     * 
     */
    public void validateAddedIngredient() {
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

    /**
     * This method creats a string representation of the added string
     */
    private void createIngredientString() {
        String s = Ingredients.getText();
        s += IngredientNameInput.getText() + "\t" + IngredientAmount.getText() + IngredientPropertyMenu.getText();
        s += "\n";
        Ingredients.setText(s);
    }
    /**
     * This methos checks if there is added an ingredient
     * - If there is added an ingredient the AddIngredient pane will dissapear and the AddStepPane will show up
     * - If there is not added an ingredient to the recipe the user will get feedback to add an ingredient first
     */
    public void addedAllIngredients() {
        if (newRecipe.getIngredients().isEmpty()) {
            NoAddedIngredientError.setVisible(true);
        } else {
            NoAddedIngredientError.setVisible(false);
            AddIngredientPane.setVisible(false);
            AddStepPane.setVisible(true);
            Ingredients.setText(Ingredients.getText() + "\n");
        }

    }

    /**
     * This method changes the text on the menubutton where the user selects a property
     * 
     * @param event Event when the user selects a property
     */
    public void selectProperty(ActionEvent event) {
        MenuItem selectedProperty = (MenuItem) event.getSource();
        String PropertyAsText = selectedProperty.getText();
        IngredientPropertyMenu.setText(PropertyAsText);
    }

    /**
     * This method cleans the input variables where the user has added an ingredient
     * so the user can add more ingredients
     */
    private void cleanIngredientButtons() {
        IngredientNameInput.deleteText(0, IngredientNameInput.getText().length());
        IngredientNameInput.setPromptText("Ingredient Name");
        IngredientAmount.deleteText(0, IngredientAmount.getText().length());
        IngredientAmount.setPromptText("Amount");
        IngredientPropertyMenu.setText("Property");
    }

    /**
     * This method adds the user given description to the recipe and show it to 
     * the preview
     * 
     * If there is no added description the user get feedback to add an description
     */
    public void AddDescription()  {
        if (validateDescrition()) {
            newRecipe.setDescription(AddDescriptionText.getText());
            Description.setText(AddDescriptionText.getText());
            cleanDescriptionButtons();
        } else {
            DescriptionError.setVisible(true);
        }
    }

    /**
     * this method checks if there is adden an descrition and checks that the descition is a string
     * @return true if the descrition meets the requierments and false if it does not
     */
    private boolean validateDescrition() {
        if (!AddDescriptionText.getText().isEmpty() && AddDescriptionText.getText().matches("[A-Za-z]+")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method removes the AddDescritionPane and show the AddIngredientPane
     */
    private void cleanDescriptionButtons() {
        DescriptionPane.setVisible(false);
        AddIngredientPane.setVisible(true);
    }

    /**
     * This method add the input step to the recipe and show it in the prewiew if the step meets the requierment
     * 
     * If the added step doe not meet the requierments, the user gets feedback.
     */
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

    /**
     * This method creates a string representation of the added step that will show up in the preview
     */
    private void createStepString() {
        String s = Ingredients.getText();
        s += "Step " + (newRecipe.getSteps().size()) + ":" + "\t"
                + newRecipe.getSteps().get(newRecipe.getSteps().size() - 1) + "\n";
        Ingredients.setText(s);
    }

    /**
     * This method checks if the added step meets the requierments
     * 
     * @return true if added step meets the requierment and false if it does not
     */
    private boolean validateStep() {
        if (!AddStepText.getText().isEmpty() && !AddStepText.getText().matches("-?\\d+(\\.\\d+)?")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method clean the input variables after a step is added so the user can add more steps
     */
    private void cleanStepInput() {
        AddStepText.deleteText(0, AddStepText.getText().length());
        AddStepText.setPromptText("Step " + (newRecipe.getSteps().size() + 1) + ":" + "\t");
    }

    /**
     * This method validates if there is added a step or not
     * Gives feedback if there is not added any step
     */
    public void addedAllSteps() {
        if (!newRecipe.getSteps().isEmpty()) {
            addStepsNext();
        } else if (newRecipe.getSteps().isEmpty()) {
            NoAddedStepError.setVisible(true);
        }
    }

    /**
     *This method hides the AddStepPane and shows the PortionAndConfirmPane
     */
    private void addStepsNext() {
        AddStepPane.setVisible(false);
        PortionAndConfirmPane.setVisible(true);
    }

    /**
     * This method validates if the user has selectet an amount of portions and saves the recipe to file if the amount is selected 
     */
    public void SaveRecipe() {
        if (valdatePortions()) {
            newRecipe.setPortions(Integer.parseInt(NumberOfPortionsMenu.getText()));
            PortionAndConfirmPane.setVisible(false);
            saveRecipeToLibrary();
        } else {
            NoAddedPortionsError.setVisible(true);
        }
    }

    /**
     * This method uses the AddReciepFilehandler to save the Recipe to file
     * 
     * @see AddRecipeFilehander.SaveRecipe(Recipe)
     */
    private void saveRecipeToLibrary() {
        addRecipe_filehandler.SaveRecipe(newRecipe);
    }


    /**
     * This method checks if the user has selected an amount of portions.
     * @return boolean true if portions is selected and false if not
     */
    private boolean valdatePortions() {
        if (NumberOfPortionsMenu.getText().matches("[1-9]")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method changes the text on the seletedPortions MenuItem when the user selects an amount of portions
     * @param event event when user clicks on an amount of portions
     */
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
