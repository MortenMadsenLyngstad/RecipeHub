package ui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Recipe;
import file.RecipeFilehandler;
import file.UserFilehandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddRecipeController extends SuperController {
    private Recipe newRecipe;
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");

    @FXML
    private Button BackButton, AddName, AddIngredientButton, IngredientsNextButton, AddDescriptionButton, AddStepButton,
            AddStepNext, SaveRecipeButten, RemoveIngredient, RemoveStep;

    @FXML
    private Pane RecipeNamePane, AddIngredientPane, DescriptionPane, AddStepPane, PortionAndConfirmPane;

    @FXML
    private TextField RecipeName, IngredientNameInput, IngredientAmount, AddDescriptionText, AddStepText;

    @FXML
    private MenuButton IngredientPropertyMenu, NumberOfPortionsMenu;

    @FXML
    private Text NameError, DescriptionError, PropertyError, AmountError,
            IngredientNameError, NoAddedIngredientError, NoAddedStepError, NotValidStepError, NoAddedPortionsError;

    @FXML
    private Label Description, Name, IngredientAndSteps;


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
     * illegal argument exception if the name is empty
     * 
     * @see #setName(String)
     * */
    public void createRecipe() throws IllegalArgumentException {
        if (validateRecipeName()) {
            this.newRecipe = new Recipe(RecipeName.getText(), 1, currentProfile);
            RecipeNamePane.setVisible(false);
            Name.setText(newRecipe.getName());
            DescriptionPane.setVisible(true);
        } else {
            NameError.setVisible(true);
        } 
    }
    
    /**
     * This method is called when the user clicks on the AddName button
     * the method validates if the user has added a name to the recipe
     * 
     * @return true if the user has added a name and false if not
     * 
     */
    public boolean validateRecipeName() {
        if (!RecipeName.getText().isBlank()) {
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
     */
    public void AddDescription() {
        if (validateDescrition()) {
            newRecipe.setDescription(AddDescriptionText.getText());
            Description.setText(AddDescriptionText.getText());
            cleanDescriptionButtons();
        } else {
            DescriptionError.setVisible(true);
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
        return !AddDescriptionText.getText().isEmpty();
    }

    /**
     * This method removes the AddDescritionPane and show the AddIngredientPane
     */
    private void cleanDescriptionButtons() {
        DescriptionPane.setVisible(false);
        AddIngredientPane.setVisible(true);
    }

    /**
     * This method validates if an added ingredient follow the requierments:
     * - The ingredient name cannot be empty
     * - The amount must be an int
     * - There must be a selected property
     * 
     * If one or more off the inputs does not mett the requierments the user will
     * recive feedback
     * 
     * If the requirements are met, the ingredient will be added to the recipe
     * 
     * @see Recipe.addIngredient(String, Double, String)
     *      When the ingredient is added the inputs are cleaned and ready to add
     *      more ingredients
     */
    public void addIngredient() {
        if (validateIngredient()) {
            newRecipe.addIngredient(IngredientNameInput.getText(), Double.parseDouble(IngredientAmount.getText()),
                    IngredientPropertyMenu.getText());
            newRecipe.setIngredientUnit(IngredientNameInput.getText(), IngredientPropertyMenu.getText());
            showIngredientPreview();
            cleanIngredientButtons();
            AmountError.setVisible(false);
            IngredientNameError.setVisible(false);
            PropertyError.setVisible(false);
            NoAddedIngredientError.setVisible(false);
            RemoveIngredient.setVisible(true);
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
        if (!IngredientNameInput.getText().isEmpty() && IngredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")
                && !IngredientPropertyMenu.getText().equals("Property")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method creats a string representation of the added string
     */
    private void showIngredientPreview() {
        String s = IngredientAndSteps.getText();
        s +=  newRecipe.getIngredientAmount(IngredientNameInput.getText()) + IngredientPropertyMenu.getText() + "\t" + "\t" + IngredientNameInput.getText() + "\n";
        IngredientAndSteps.setText(s);
    }

    /**
     * This method is called when the added ingredient does not meet the
     * requierments and gives feedback to the user
     */
    private void ingredientError() {
        if (IngredientNameInput.getText().isEmpty() || IngredientNameInput.getText().matches("^[1-9][0-9]*([.][0-9]+)?$")) {
            IngredientNameError.setVisible(true);
        } else {
            IngredientNameError.setVisible(false);
        }

        if (!IngredientAmount.getText().matches("^[1-9][0-9]*([.][0-9]+)?$") || IngredientAmount.getText().isEmpty()) {
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


    /**
     * This method changes the text on the menubutton where the user selects a
     * property
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
     * This methos checks if there is added an ingredient
     * - If there is added an ingredient the AddIngredient pane will dissapear and
     * the AddStepPane will show up
     * - If there is not added an ingredient to the recipe the user will get
     * feedback to add an ingredient first
     */
    public void addedAllIngredients() {
        if (newRecipe.getIngredients().isEmpty()) {
            NoAddedIngredientError.setVisible(true);
        } else {
            NoAddedIngredientError.setVisible(false);
            AddIngredientPane.setVisible(false);
            AddStepPane.setVisible(true);
            IngredientAndSteps.setText(IngredientAndSteps.getText() + "\n" + "\n");
        }

    }

    /**
     * This method removes the last added ingredient from the recipe and the preview
     */
    public void RemoveIngredient() {
        List<String> ingredients =  new ArrayList<>(newRecipe.getIngredients());
        String ingredient = ingredients.get(0);
        newRecipe.removeIngredient(ingredient);
        String[] s = IngredientAndSteps.getText().split("\n");
        if (s.length >= 2) {
            String newIngredientAndSteps = "";
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndSteps += s[i] + "\n";
            }
            IngredientAndSteps.setText(newIngredientAndSteps);
        } else {
            IngredientAndSteps.setText("");
            RemoveIngredient.setVisible(false);
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
            newRecipe.addStep(AddStepText.getText());
            showStepsInPreview();
            cleanStepInput();
            NotValidStepError.setVisible(false);
            NoAddedStepError.setVisible(false);
            RemoveStep.setVisible(true);
        } else {
            NotValidStepError.setVisible(true);
        }
    }

    /**
     * This method creates a string representation of the added step that will show
     * up in the preview
     */
    private void showStepsInPreview() {
        String s = "Step " + (newRecipe.getSteps().size()) + ":" + "\t"
                + newRecipe.getSteps().get(newRecipe.getSteps().size() - 1) + "\n";
        IngredientAndSteps.setText(IngredientAndSteps.getText() + s);
    }

    /**
     * This method checks if the added step meets the requierments
     * 
     * @return true if added step meets the requierment and false if it does not
     */
    private boolean validateStep() {
        if (!AddStepText.getText().isEmpty() && AddStepText.getText().matches("[a-zA-Z ,.0-9!?.,]+")) {
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
     * This method hides the AddStepPane and shows the PortionAndConfirmPane
     */
    private void addStepsNext() {
        AddStepPane.setVisible(false);
        PortionAndConfirmPane.setVisible(true);
    }

    /**
     * This method removes the last added step from the recipe and the preview
     */
    public void removeStep() {
        List<String> steps = newRecipe.getSteps();
        String step = steps.get(0);
        newRecipe.removeStep(step);

        String[] s = IngredientAndSteps.getText().split("\n");

        System.out.println(s);
        if (s.length >= 2) {
            if (!s[s.length - 1].contains("Step")) {
                while (!s[s.length - 1].contains("Step")) {
                    for (int i = 0; i < s.length - 2; i++) {
                        s[i] = s[i];
                    }
                }
            }
            String newIngredientAndSteps = "";
            for (int i = 0; i < s.length - 1; i++) {
                newIngredientAndSteps += s[i] + "\n";
            }
            IngredientAndSteps.setText(newIngredientAndSteps);
        } else {
            IngredientAndSteps.setText("");
            RemoveStep.setVisible(false);
        }
    }

    /**
     * This method validates if the user has selectet an amount of portions and
     * saves the recipe to file if the amount is selected
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
     * This method uses the RecipeFilehandler to save the Recipe to file
     * It also saves the recipe to the logged in profile
     * @see RecipeFilehandler#writeRecipe(Recipe)
     * @see UserFilehandler#writeProfile(Profile)
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
        if (NumberOfPortionsMenu.getText().matches("[1-9]")) {
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
        if (newRecipe == null) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
        } else {
            showPopUp(event);
        }
    }

    /**
     * This method shows a pop up window when the user tries to go back without
     * saving the recipe
     * 
     * The pop up window gives the user the option to go back without saving the
     * recipe or to go back and complet the recipe
     * 
     * @param event event when the user clicks on the back button
     * @see #backButtonClick(event)
     */
    private void showPopUp(ActionEvent event) {
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Your recipe is not saved!");
        
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Label label = new Label("If you go back now your recipe will not be saved. Do you want to go back?");
        Button noButton = new Button("No");
        noButton.setOnAction(e -> popUpStage.close());

        Button yesButton = new Button("Yes");

        yesButton.setOnAction(e -> {
            try {
                //currentProfile.removeRecipe(newRecipe);
                switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
            } catch (IOException e1) {
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
