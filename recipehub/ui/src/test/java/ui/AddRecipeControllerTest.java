package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.PasswordHasher;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.DirectRecipeHubAccess;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * This is a test class for AddRecipeController.
 */
public class AddRecipeControllerTest extends ApplicationTest {
    private AddRecipeController controller;
    private Parent root;

    private TextField recipeNameField, addDescriptionField, testIngredientNameInputField,
            testIngredientAmounInputtField, addStepField;

    private Button addRecipeNameButton, addDescriptionButton, addIngredientButton, removeIngredient,
            ingredientsNextButton, addStepButton,
            stepsNextButton, removeStepButton, saveRecipeButton, backButton;

    private Text testNameError, testDescriptionError, testIngredientNameError, testAmountError,
            testPropertyError, noAddedIngredientError, noAddedStepError, notValidStepError,
            noAddedPortionsError;

    private Pane addRecipePane, addDescriptionPane,
            addIngredientPane, addStepPane, portionAndConfirmPane;

    private Label name, description, testIngredientAndSteps;

    private MenuButton testIngredientPropertyMenu, testAddPortionMenu;

    private MenuItem testGrams, testPortion1;
    private RecipeFilehandler mockRecipeFileHandler = mock(RecipeFilehandler.class);
    private UserFilehandler mockUserFileHandler = mock(UserFilehandler.class);
    private Profile mockProfile = mock(Profile.class);
    private Recipe mockRecipe = mock(Recipe.class);

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("addRecipe.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        recipeNameField = lookup("#recipeName").query();
        testNameError = lookup("#nameError").query();
        addRecipeNameButton = lookup("#addName").query();
        name = lookup("#name").query();
        addRecipePane = lookup("#recipeNamePane").query();
        addDescriptionPane = lookup("#descriptionPane").query();
        addDescriptionField = lookup("#addDescriptionText").query();
        description = lookup("#description").query();
        addDescriptionButton = lookup("#addDescriptionButton").query();
        testDescriptionError = lookup("#descriptionError").query();
        addIngredientPane = lookup("#addIngredientPane").query();
        testIngredientNameInputField = lookup("#ingredientNameInput").query();
        testIngredientAmounInputtField = lookup("#ingredientAmount").query();
        testIngredientAndSteps = lookup("#ingredientAndSteps").query();
        testIngredientNameError = lookup("#ingredientNameError").query();
        testAmountError = lookup("#amountError").query();
        testPropertyError = lookup("#propertyError").query();
        addIngredientButton = lookup("#addIngredientButton").query();
        testIngredientPropertyMenu = lookup("#ingredientPropertyMenu").query();
        removeIngredient = lookup("#removeIngredient").query();
        ingredientsNextButton = lookup("#ingredientsNextButton").query();
        addStepPane = lookup("#addStepPane").query();
        noAddedIngredientError = lookup("#noAddedIngredientError").query();
        addStepField = lookup("#addStepText").query();
        addStepButton = lookup("#addStepButton").query();
        noAddedStepError = lookup("#noAddedStepError").query();
        addStepField = lookup("#addStepText").query();
        notValidStepError = lookup("#notValidStepError").query();
        stepsNextButton = lookup("#addStepNext").query();
        removeStepButton = lookup("#removeStep").query();
        portionAndConfirmPane = lookup("#portionAndConfirmPane").query();
        testAddPortionMenu = lookup("#numberOfPortionsMenu").query();
        saveRecipeButton = lookup("#saveRecipeButten").query();
        noAddedPortionsError = lookup("#noAddedPortionsError").query();
        backButton = lookup("#backButton").query();

        fixMockMenuItems();

        when(mockRecipeFileHandler.readRecipeLibrary()).thenReturn(new RecipeLibrary());

        controller.setProfile(mockProfile);
        mockProfile.setHashedPassword(PasswordHasher.hashPassword("testPassword"));
        controller.setCurrentRecipeHubAccess(
                 new DirectRecipeHubAccess(mockUserFileHandler, mockRecipeFileHandler));
    }

    /**
     * This method will test the functionality of the mockMenuItems.
     */
    private void fixMockMenuItems() {
        testIngredientPropertyMenu.getItems().forEach(item -> {
            if (item instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) item;
                if ("g".equals(menuItem.getText())) {
                    testGrams = menuItem;
                }
            }
        });

        testAddPortionMenu.getItems().forEach(item -> {
            if (item instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) item;
                if ("1".equals(menuItem.getText())) {
                    testPortion1 = menuItem;
                }
            }
        });

    }

    /**
     * This method will set up the recipeNameField and create a recipe.
     */
    private void setUpRecipeName() {
        Platform.runLater(() -> {
            recipeNameField.setText("testRecipe");
            controller.createRecipe();
        });
    }

    /**
     * This method will set up the recipeNameField, create a recipe and add a
     * description.
     */
    private void setUpDescription() {
        Platform.runLater(() -> {
            recipeNameField.setText("testRecipe");
            controller.createRecipe();
            addDescriptionField.setText("Enjoy the best test recipe ever!");
            controller.addDescription();
        });
    }

    /**
     * This method will set up the recipeNameField.
     * Creates a recipe, adds a description and adds an ingredient
     */
    private void setUpIngredient() {
        Platform.runLater(() -> {
            recipeNameField.setText("testRecipe");
            controller.createRecipe();
            addDescriptionField.setText("Enjoy the best test recipe ever!");
            controller.addDescription();
            testIngredientNameInputField.setText("testIngredient");
            testIngredientAmounInputtField.setText("1");
            testIngredientPropertyMenu.setText("g");
            controller.addIngredient();
        });
    }

    /**
     * This method will set up the recipeNameField.
     * Create a recipe, adds a description, adds an ingredient and adds a step
     */
    private void setUpStep() {
        Platform.runLater(() -> {
            recipeNameField.setText("testRecipe");
            controller.createRecipe();
            addDescriptionField.setText("Enjoy the best test recipe ever!");
            controller.addDescription();
            testIngredientNameInputField.setText("testIngredient");
            testIngredientAmounInputtField.setText("1");
            testIngredientPropertyMenu.setText("g");
            controller.addIngredient();
            controller.addedAllIngredients();
            addStepField.setText("Cook the testIngredient in boiling water for 10 minutes");
            controller.addStep();
            controller.addedAllSteps();
        });
    }

    /**
     * This method will test the functionality of the createRecipe method.
     */
    @Test
    public void testCreateRecipeWithBlankName() {
        recipeNameField.setText("");
        assertFalse(controller.validateRecipeName());
        clickOn(addRecipeNameButton);
        assertTrue(testNameError.isVisible());
    }

    /**
     * This method will test the functionality of the createRecipe method.
     */
    @Test
    public void testCreateRecipeWithTooLongName() {
        recipeNameField.setText("This string is too long to be accepted");
        assertFalse(controller.validateRecipeName());
        clickOn(addRecipeNameButton);
        assertTrue(testNameError.isVisible());
    }

    @Test
    public void testCreateRecipeWithValidName() {
        recipeNameField.setText("testRecipe");
        assertTrue(controller.validateRecipeName());
        clickOn(addRecipeNameButton);
        assertFalse(testNameError.isVisible());
        assertFalse(addRecipePane.isVisible());
        assertTrue(addDescriptionPane.isVisible());
        assertEquals("testRecipe", name.getText());
    }

    /**
     * This method will test the functionality of the addDescription method.
     */
    @Test
    public void testAddDescriptionWithInvalidDescription() {
        addRecipePane.setVisible(false);
        addDescriptionPane.setVisible(true);

        setUpRecipeName();
        addDescriptionField.setText("");
        clickOn(addDescriptionButton);
        assertTrue(testDescriptionError.isVisible());
        assertEquals("testRecipe", name.getText());

    }

    /**
     * This method will test the functionality of the addDescription method.
     */
    @Test
    public void testAddDescriptionWithValidDescription() {
        addRecipePane.setVisible(false);
        addDescriptionPane.setVisible(true);

        setUpRecipeName();
        addDescriptionField.setText("Enjoy the best test recipe ever!");
        clickOn(addDescriptionButton);
        assertFalse(testDescriptionError.isVisible());
        assertFalse(addDescriptionPane.isVisible());
        assertTrue(addIngredientPane.isVisible());
        assertEquals("testRecipe", name.getText());
        assertEquals("Enjoy the best test recipe ever!", description.getText());
    }

    /**
     * This method will test the functionality of the addIngredient method.
     */
    @Test
    public void testAddInvalidIngredient() {
        addRecipePane.setVisible(false);
        addIngredientPane.setVisible(true);

        setUpDescription();
        testIngredientNameInputField.setText("");
        clickOn(addIngredientButton);
        assertTrue(testIngredientNameError.isVisible());
        assertTrue(testAmountError.isVisible());
        assertTrue(testPropertyError.isVisible());

        testIngredientNameInputField.setText("testIngredient");
        testIngredientAmounInputtField.setText("");
        clickOn(addIngredientButton);
        assertFalse(testIngredientNameError.isVisible());
        assertTrue(testAmountError.isVisible());

        testIngredientNameInputField.setText("");
        testIngredientAmounInputtField.setText("1");
        clickOn(addIngredientButton);
        assertTrue(testIngredientNameError.isVisible());
        assertFalse(testAmountError.isVisible());

        testIngredientNameInputField.setText("");
        testIngredientAmounInputtField.setText("");
        clickOn(testIngredientPropertyMenu);
        Platform.runLater(() -> {
            testGrams.fire();
        });
        clickOn(addIngredientButton);
        assertTrue(testIngredientNameError.isVisible());
        assertTrue(testAmountError.isVisible());
        assertFalse(testPropertyError.isVisible());
        clickOn(ingredientsNextButton);
        assertTrue(noAddedIngredientError.isVisible());
    }

    /**
     * This method will test the functionality of the addIngredient method.
     */
    @Test
    public void testAddValidIngredient() {
        addRecipePane.setVisible(false);
        addIngredientPane.setVisible(true);
        setUpIngredient();

        Platform.runLater(() -> {
            assertFalse(testIngredientNameError.isVisible());
            assertFalse(testAmountError.isVisible());
            assertFalse(testPropertyError.isVisible());
            assertEquals("1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n",
                    testIngredientAndSteps.getText());
            assertTrue(addIngredientPane.isVisible());
            assertTrue(removeIngredient.isVisible());
            assertFalse(addStepPane.isVisible());
        });

        clickOn(ingredientsNextButton);
        assertTrue(addStepPane.isVisible());
        assertFalse(addIngredientPane.isVisible());
    }

    /**
     * This method will test the functionality of the addIngredient method.
     */
    @Test
    public void testRemoveIngredient() {
        addRecipePane.setVisible(false);
        addIngredientPane.setVisible(true);
        setUpIngredient();

        Platform.runLater(() -> {
            testIngredientNameInputField.setText("testIngredient2");
            testIngredientAmounInputtField.setText("1");
            clickOn(testIngredientPropertyMenu);
            Platform.runLater(() -> {
                testGrams.fire();
            });
        });
        clickOn(addIngredientButton);
        assertEquals("1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n" + "1.0" + "g" + "\t" + "\t"
                + "testIngredient2" + "\n", testIngredientAndSteps.getText());

        clickOn(removeIngredient);
        assertEquals("1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n",
                testIngredientAndSteps.getText());
        clickOn(removeIngredient);
        assertEquals("", testIngredientAndSteps.getText());
        assertFalse(removeIngredient.isVisible());
    }

    /**
     * This method will test the functionality of the addStep method.
     */
    @Test
    public void testAddInvalidStep() {
        addRecipePane.setVisible(false);
        addStepPane.setVisible(true);
        setUpIngredient();

        Platform.runLater(() -> {
            controller.addedAllIngredients();
        });

        assertTrue(addStepPane.isVisible());
        clickOn(stepsNextButton);
        assertTrue(noAddedStepError.isVisible());
        addStepField.setText("ø");
        clickOn(addStepButton);
        assertTrue(notValidStepError.isVisible());
    }

    /**
     * This method will test the functionality of the addStep method.
     */
    @Test
    public void testAddValidStep() {
        addRecipePane.setVisible(false);
        addStepPane.setVisible(true);
        setUpIngredient();

        Platform.runLater(() -> {
            controller.addedAllIngredients();
        });
        addStepField.setText("Cook the testIngredient in boiling water for 10 minutes");
        clickOn(addStepButton);
        assertFalse(notValidStepError.isVisible());
        assertFalse(noAddedStepError.isVisible());
        assertEquals(
                "1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n" + "\n" + "\n"
                        + "Step 1:" + "\t"
                        + "Cook the testIngredient in boiling water for 10 minutes"
                        + "\n",
                testIngredientAndSteps.getText());
        clickOn(stepsNextButton);
        assertFalse(addStepPane.isVisible());
        assertTrue(portionAndConfirmPane.isVisible());

    }

    /**
     * This method will test the functionality of the addStep method.
     */
    @Test
    public void testRemoveStep() {
        addRecipePane.setVisible(false);
        addStepPane.setVisible(true);
        setUpIngredient();

        Platform.runLater(() -> {
            controller.addedAllIngredients();
            addStepField.setText("Cook the testIngredient in boiling water for 10 minutes");
            controller.addStep();
            addStepField.setText(
                    "Let the testIngredient cool down for 5 minutes, "
                            + "and make this step as unnecessarily "
                            + "long so that it will be too long "
                            + "to fit in the text area and will be split into two lines");
            controller.addStep();
        });

        clickOn(removeStepButton);
        assertEquals(
                "1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n" + "\n" + "\n"
                        + "Step 1:" + "\t"
                        + "Cook the testIngredient in boiling water for 10 minutes"
                        + "\n",
                testIngredientAndSteps.getText());
        clickOn(removeStepButton);
        assertFalse(removeStepButton.isVisible());
        assertEquals(
                "1.0" + "g" + "\t" + "\t" + "testIngredient" + "\n" + "\n" + "\n",
                testIngredientAndSteps.getText());

    }

    /**
     * This method will test the functionality of the addStep method.
     */
    @Test
    public void testNotValidPortions() {
        addRecipePane.setVisible(false);
        portionAndConfirmPane.setVisible(true);
        setUpStep();

        clickOn(saveRecipeButton);
        assertTrue(noAddedPortionsError.isVisible());
    }

    /**
     * This method will test the functionality of the addStep method.
     */
    @Test
    public void testValidPortions() {
        when(mockRecipeFileHandler.writeRecipe(mockRecipe)).thenReturn(true);
        when(mockUserFileHandler.writeProfile(mockProfile)).thenReturn(true);
        addRecipePane.setVisible(false);
        portionAndConfirmPane.setVisible(true);
        setUpStep();

        Platform.runLater(() -> {
            clickOn(testAddPortionMenu);
            testPortion1.fire();
        });
        clickOn(saveRecipeButton);
        assertFalse(noAddedPortionsError.isVisible());
        assertFalse(portionAndConfirmPane.isVisible());
    }

    /**
     * This method will test the functionality of the backButton.
     */
    @Test
    public void testBackButtonClick() {
        when(mockProfile.getFavorites()).thenReturn(new RecipeLibrary());
        clickOn(backButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test the functionality of the backButton.
     */
    @Test
    public void testAlert() {
        controller.setFileName("addRecipe.fxml");
        when(mockProfile.getFavorites()).thenReturn(new RecipeLibrary());
        setUpRecipeName();
        clickOn(backButton);
        Button cancelButton = (Button) controller.getAlert().getDialogPane()
                .lookupButton(ButtonType.CANCEL);
        clickOn(cancelButton);
        assertEquals("addRecipe.fxml", controller.getFileName());
        clickOn(backButton);
        Button okButton = (Button) controller.getAlert().getDialogPane()
                .lookupButton(ButtonType.OK);
        clickOn(okButton);
        Platform.runLater(() -> {
            assertEquals("Mainscreen.fxml", controller.getFileName());
        });
    }

}