package ui.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.RecipeFilehandler;
import file.UserFilehandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.App;

public class RecipeControllerTest extends ApplicationTest {

    private RecipeController controller;
    private Parent root;

    private List<Profile> profiles = new ArrayList<>();
    private RecipeLibrary recipes = new RecipeLibrary();
    private Hashtable<String, String> userInfo = new Hashtable<>();

    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    private Button backButton;
    private TextArea descriptionText, stepsText, ingredientsText;
    private FontAwesomeIconView deleteButton, heartButton;

    private Profile mockProfile = mock(Profile.class);
    private RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
    private UserFilehandler mockUserFilehandler = mock(UserFilehandler.class);

    /**
     * This method will set up the application for headless mode (tests will run without GUI)
     * @see App#supportHeadless()
     */
    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    /**
     * This method will start the application
     * 
     * @param stage
     * @throws IOException if the FXMLLoader.load() method throws an exception
     * @see MainscreenControllerTest#setUp()
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Recipe.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        setUp();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method will set up the necessary variables for the test
     */
    private void setUp() {
        Profile profile1 = new Profile("Username", "Password1");
        profiles.add(profile1);

        userInfo.put("Username", "Password1");

        Recipe recipe = new Recipe("Pizza", 2, profile1);
        recipe.setName("Pizza");
        recipe.setDescription("Best way to start the weekend");
        recipe.addStep("Make the dough");
        recipe.addStep("Add toppings");
        recipe.addIngredient("Flour", 100.0, "g");
        recipe.addIngredient("Tomato sauce", 100.0, "g");
        recipe.addIngredient("Cheese", 100.0, "g");
        recipe.addIngredient("Pepperoni", 100.0, "g");
        recipe.setPortions(4);
        recipes.addRecipe(recipe);

        doNothing().when(mockUserFilehandler).writeProfile(profile1);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(recipes);

        // Makes it so that the controller uses testfiles instead of the main ones
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);

        controller.setRecipe(recipe);
        controller.setProfile(profile1);
        controller.populate();
    }

    /**
     * This method will set up the buttons for the test
     */
    @BeforeEach
    public void setUpButtons() {
        nameField = lookup("#nameField").query();
        authorLabel = lookup("#authorLabel").query();
        descriptionLabel = lookup("#descriptionLabel").query();
        stepsLabel = lookup("#stepsLabel").query();
        portionsLabel = lookup("#portionsLabel").query();
        backButton = lookup("#backButton").query();
        descriptionText = lookup("#descriptionText").query();
        stepsText = lookup("#stepsText").query();
        ingredientsText = lookup("#ingredientsText").query();
        deleteButton = lookup("#deleteButton").query();
        heartButton = lookup("#heartButton").query();
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);
        controller.currentProfile = profiles.get(0);
    }

    /**
     * This method will test if the set up is correct
     */
    @Test
    public void testPopulate() {
        when(mockProfile.getUsername()).thenReturn("Username");
        assertEquals("Pizza", nameField.getText());
        assertEquals("Posted by: Username", authorLabel.getText());
        assertEquals("Description", descriptionLabel.getText());
        assertEquals("Steps", stepsLabel.getText());
        assertEquals("Portions: 4", portionsLabel.getText());
        assertEquals("Back", backButton.getText());
        assertEquals("Best way to start the weekend", descriptionText.getText());
        assertEquals("Step 1:  Make the dough\nStep 2:  Add toppings\n", stepsText.getText());
        assertEquals("100.0 g : Tomato sauce\n100.0 g : Cheese\n100.0 g : Flour\n100.0 g : Pepperoni\n", ingredientsText.getText());
        assertEquals("TRASH", deleteButton.getGlyphName());
        assertEquals("HEART", heartButton.getGlyphName());
        assertTrue(deleteButton.isVisible());
    }

    /**
     * This method will test if the back button works
     */
    @Test
    public void backButtonTest() {
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(recipes);
        when(mockProfile.getUsername()).thenReturn("Username");
        clickOn(backButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test if the delete button changes strokewidth when hovered over
     */
    @Test
    public void testHoverOverDelete() {
        moveTo(deleteButton);
        assertEquals(0.7, deleteButton.getStrokeWidth(),
                "The StrokeWidth of the delete button should increase to 0.7 when moving you mouse over it");
    }

    /**
     * This method will test if a recipe can be deleted, and switched back to the mainscreen
     * if the user confirms the deletion
     */
    @Test
    public void testDeleteRecipe() {
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        when(mockProfile.getUsername()).thenReturn("Username");
        doNothing().when(mockProfile).removeRecipe(recipes.getRecipe(0));
        clickOn(deleteButton);
        Button okButton = (Button) controller.getAlert().getDialogPane().lookupButton(ButtonType.OK);
        clickOn(okButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test if a recipe can be deleted, and switched back to the mainscreen
     * if the user confirms the deletion
     */
    @Test
    public void testDeleteRecipe2() {
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        doNothing().when(mockProfile).removeRecipe(recipes.getRecipe(0));
        clickOn(deleteButton);
        Button okButton = (Button) controller.getAlert().getDialogPane().lookupButton(ButtonType.OK);
        clickOn(okButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test if a recipe can be deleted, and check if
     * the user is sent back to recipe.fxml if the user cancels the deletion
     */
    @Test
    public void testDeleteRecipeCancel() {
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);
        doNothing().when(mockProfile).removeRecipe(recipes.getRecipe(0));
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        when(mockProfile.getUsername()).thenReturn("Username");
        clickOn(deleteButton);
        Button cancelButton = (Button) controller.getAlert().getDialogPane().lookupButton(ButtonType.CANCEL);
        clickOn(cancelButton);
        Platform.runLater(() -> {
            assertNotEquals("Mainscreen.fxml", controller.getFileName());
        });
    }

    /**
     * This method will test if the heart button changes strokewidth when hovered over
     */
    @Test
    public void testHoverOverFavorite() {
        moveTo(heartButton);
        assertEquals(2, heartButton.getStrokeWidth(),
                "The StrokeWidth of the heart should increase to 2 when moving you mouse over it");
    }

    /**
     * This method will test if the heart button changes color when clicked
     */
    @Test
    public void testClickOnFavoriteButton() {
        doNothing().when(mockUserFilehandler).writeProfile(profiles.get(0));
        clickOn(heartButton);
        assertEquals(Color.RED, heartButton.getFill(),
                "The heart should be red when you click on it");

        clickOn(heartButton);
        assertEquals(Color.WHITE, heartButton.getFill(),
                "The heart should be black when you click on it again");
    }
}
