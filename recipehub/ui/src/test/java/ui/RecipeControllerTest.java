package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import core.Review;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.DirectRecipeHubAccess;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen.
 */
public class RecipeControllerTest extends ApplicationTest {
    private RecipeController controller;
    private Parent root;
    private List<Profile> profiles = new ArrayList<>();
    private RecipeLibrary recipes = new RecipeLibrary();
    private Hashtable<String, String> userInfo = new Hashtable<>();
    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    private Button backButton;
    private TextArea descriptionText, stepsText, ingredientsText;
    private FontAwesomeIconView deleteButton, heartButton, plusButton, minusButton;
    private TextField portionsField;
    private Rating rating;
    private Text averageRating;
    private Hyperlink numberOfRaters, numberOfcomments;
    private VBox scrollPaneBox;
    private Profile mockProfile = mock(Profile.class);
    private RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
    private UserFilehandler mockUserFilehandler = mock(UserFilehandler.class);

    /**
     * This method will set up the application for headless mode (tests will
     * runwithout GUI).
     *
     * @see App#supportHeadless()
     */
    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    /**
     * This method will start the application.
     *
     * @param stage the stage to start
     * @throws IOException if the FXMLLoader.load() method throws an exception
     * @see RecipeControllerTest#setUp()
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Recipe.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        setUp();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method will set up the necessary variables for the test.
     * We initialize the recipe with two revies.
     * This is done to test {@link RecipeController#showComments()} properly.
     * This implies that we don't test a recipe with no reviews - however, the logic
     * is the same.
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
        recipe.addReview(new Review(3.0, "This is a comment", "Username2"));
        recipe.addReview(new Review(3.0, "This is a comment", "Username3"));
        recipes.putRecipe(recipe);

        when(mockUserFilehandler.writeProfile(profile1)).thenReturn(true);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(recipes);

        // Makes it so that the controller uses testfiles instead of the main ones
        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFilehandler, mockRecipeFilehandler));

        controller.setRecipe(recipe);
        controller.setProfile(profile1);
        controller.populate();
    }

    /**
     * This method will test if the set up is correct.
     * It will check if the correct information is displayed.
     * It will also check if the correct buttons are visible.
     *
     * @see RecipeController#populate()
     */
    @Test
    public void testPopulate() {
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
        portionsField = lookup("#portionsField").query();
        rating = lookup("#recipeRating").query();
        averageRating = lookup("#averageRating").query();
        numberOfRaters = lookup("#numberOfRaters").query();

        when(mockProfile.getUsername()).thenReturn("Username");
        assertEquals("Pizza", nameField.getText());
        assertEquals("Posted by: Username", authorLabel.getText());
        assertEquals("Description", descriptionLabel.getText());
        assertEquals("Steps", stepsLabel.getText());
        assertEquals("Portion(s):", portionsLabel.getText());
        assertEquals("4", portionsField.getText());
        assertEquals("Back", backButton.getText());
        assertEquals("Best way to start the weekend", descriptionText.getText());
        assertEquals("Step 1:  Make the dough\nStep 2:  Add toppings\n", stepsText.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        assertEquals("TRASH", deleteButton.getGlyphName());
        assertEquals("HEART", heartButton.getGlyphName());
        assertEquals(3.0, rating.getRating());
        assertEquals("3.0", averageRating.getText());
        assertEquals("(2)", numberOfRaters.getText());
        assertTrue(numberOfRaters.isDisable());
        assertTrue(deleteButton.isVisible());
    }

    /**
     * This method tests scaling functionality.
     * It tests if clicking on the plus and minus buttons will scale the recipe.
     * It also tests if illegal input is ignored.
     *
     * @see RecipeController#scale()
     */
    @Test
    public void testScale() {
        ingredientsText = lookup("#ingredientsText").query();
        plusButton = lookup("#plusButton").query();
        minusButton = lookup("#minusButton").query();
        portionsField = lookup("#portionsField").query();

        // Checks setUp
        assertEquals("4", portionsField.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        // Checks that illegal input does not change the scaling
        clickOn(portionsField).eraseText(1).write("notAnInt")
                .type(javafx.scene.input.KeyCode.ENTER);
        assertEquals("4", portionsField.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        clickOn(portionsField).eraseText(1).write("12.4").type(javafx.scene.input.KeyCode.ENTER);
        assertEquals("4", portionsField.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        clickOn(portionsField).eraseText(1).write("51").type(javafx.scene.input.KeyCode.ENTER);
        assertEquals("4", portionsField.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        clickOn(portionsField).eraseText(1).write("-3").type(javafx.scene.input.KeyCode.ENTER);
        assertEquals("4", portionsField.getText());
        assertEquals(
                "100,0 g : Tomato sauce\n100,0 g : Cheese\n100,0 g : Flour\n100,0 g : Pepperoni\n",
                ingredientsText.getText());
        // Checks minusButton, legal text input and plusButton
        clickOn(minusButton);
        assertEquals("3", portionsField.getText());
        assertEquals("75,0 g : Tomato sauce\n75,0 g : Cheese\n75,0 g : Flour\n75,0 g : Pepperoni\n",
                ingredientsText.getText());
        portionsField.setText("");
        clickOn(portionsField).eraseText(1).write("1").type(javafx.scene.input.KeyCode.ENTER);
        assertEquals("1", portionsField.getText());
        assertEquals("25,0 g : Tomato sauce\n25,0 g : Cheese\n25,0 g : Flour\n25,0 g : Pepperoni\n",
                ingredientsText.getText());
        clickOn(minusButton);
        assertEquals("1", portionsField.getText());
        assertEquals("25,0 g : Tomato sauce\n25,0 g : Cheese\n25,0 g : Flour\n25,0 g : Pepperoni\n",
                ingredientsText.getText());
        clickOn(plusButton);
        assertEquals("2", portionsField.getText());
        assertEquals("50,0 g : Tomato sauce\n50,0 g : Cheese\n50,0 g : Flour\n50,0 g : Pepperoni\n",
                ingredientsText.getText());
    }

    /**
     * This method will test if the back button works.
     *
     * @see RecipeController#backButtonClick()
     */
    @Test
    public void backButtonTest() {
        backButton = lookup("#backButton").query();

        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(recipes);
        when(mockProfile.getUsername()).thenReturn("Username");
        clickOn(backButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test if the delete button changes strokewidth when hovered
     * over.
     *
     * @see RecipeController#showDeleteButton()
     */
    @Test
    public void testHoverOverDelete() {
        deleteButton = lookup("#deleteButton").query();
        moveTo(deleteButton);
        assertEquals(0.7, deleteButton.getStrokeWidth(),
                "The StrokeWidth of the delete button should "
                        + "increase to 0.7 when moving you mouse over it");
    }

    /**
     * This method will test if a recipe can be deleted, and switched back to the
     * mainscreen if the user confirms the deletion.
     *
     * @see RecipeController#deleteRecipe()
     */
    @Test
    public void testDeleteRecipe() {
        deleteButton = lookup("#deleteButton").query();

        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFilehandler, mockRecipeFilehandler));
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        doNothing().when(mockProfile).removeRecipe(recipes.getRecipe(0));
        clickOn(deleteButton);
        Button okButton = (Button) controller.getAlert().getDialogPane()
                .lookupButton(ButtonType.OK);
        clickOn(okButton);
        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    /**
     * This method will test if a recipe can be deleted, and check if
     * the user is sent back to recipe.fxml if the user cancels the deletion.
     *
     * @see RecipeController#deleteRecipe()
     */
    @Test
    public void testDeleteRecipeCancel() {
        deleteButton = lookup("#deleteButton").query();

        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFilehandler, mockRecipeFilehandler));
        doNothing().when(mockProfile).removeRecipe(recipes.getRecipe(0));
        when(mockUserFilehandler.readProfiles()).thenReturn(profiles);
        when(mockProfile.getUsername()).thenReturn("Username");
        clickOn(deleteButton);
        Button cancelButton = (Button) controller.getAlert().getDialogPane()
                .lookupButton(ButtonType.CANCEL);
        clickOn(cancelButton);
        Platform.runLater(() -> {
            assertNotEquals("Mainscreen.fxml", controller.getFileName());
        });
    }

    /**
     * This method will test if the heart button changes strokewidth when hovered
     * over.
     *
     * @see MainscreenController#setHeart(FontAwesomeIconView, Recipe, Profile)
     */
    @Test
    public void testHoverOverFavorite() {
        heartButton = lookup("#heartButton").query();

        moveTo(heartButton);
        assertEquals(2, heartButton.getStrokeWidth(),
                "The StrokeWidth of the heart should increase to 2 when moving you mouse over it");
    }

    /**
     * This method will test if the heart button changes color when clicked.
     * It will also test if the heart button changes back to white when clicked.
     *
     * @see MainscreenController#setHeart(FontAwesomeIconView, Recipe, Profile)
     */
    @Test
    public void testClickOnFavoriteButton() {
        heartButton = lookup("#heartButton").query();

        when(mockUserFilehandler.writeProfile(profiles.get(0))).thenReturn(true);
        clickOn(heartButton);
        assertEquals(Color.RED, heartButton.getFill(),
                "The heart should be red when you click on it");

        clickOn(heartButton);
        assertEquals(Color.WHITE, heartButton.getFill(),
                "The heart should be black when you click on it again");
    }

    /**
     * This method will test if a review with a comment is added correctly.
     * Also tests if the comment is displayed with the correct username and text.
     *
     * @see RecipeController#showRatingPopup()
     */
    @Test
    public void testComment() {
        numberOfRaters = lookup("#numberOfRaters").query();
        numberOfcomments = lookup("#numberOfComments").query();
        scrollPaneBox = lookup("#scrollPaneBox").query();

        controller.setProfile(new Profile("Username4", "Test1234"));
        when(mockRecipeFilehandler.writeRecipe(recipes.getRecipe(0))).thenReturn(true);
        numberOfRaters.setDisable(false);

        clickOn(numberOfRaters);
        Alert alert = controller.getRatingAlert();
        SplitPane sp = (SplitPane) alert.getGraphic();
        VBox vbox = (VBox) sp.getItems().get(0);
        Rating r = (Rating) vbox.getChildren().get(0);
        moveTo(r);

        VBox vbox2 = (VBox) sp.getItems().get(1);
        TextArea textArea = (TextArea) vbox2.getChildren().get(0);
        clickOn(textArea).write("This is a comment");
        Button okButton = (Button) controller.getRatingAlert().getDialogPane()
                .lookupButton(ButtonType.OK);
        clickOn(okButton);

        SplitPane splitPane = (SplitPane) scrollPaneBox.getChildren().get(2);
        HBox hbox = (HBox) splitPane.getItems().get(0);
        VBox vbox3 = (VBox) hbox.getChildren().get(0);
        Label l = (Label) vbox3.getChildren().get(0);
        TextArea comment = (TextArea) hbox.getChildren().get(1);
        assertEquals("Username4: ", l.getText());
        assertEquals("This is a comment", comment.getText());
        clickOn(numberOfcomments);
    }
}
