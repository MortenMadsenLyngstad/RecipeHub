package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainscreenControllerTest extends ApplicationTest {

    private MainscreenController controller;
    private Parent root;

    private List<Profile> profiles = new ArrayList<>();
    private RecipeLibrary recipes = new RecipeLibrary();

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
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Mainscreen.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        setUp();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method will set up the mockFileHandlers and other needed data
     */
    private void setUp() {
        Profile profile1 = new Profile("Username1", "Password1");
        Profile profile2 = new Profile("Username2", "Password2");

        Recipe recipe = new Recipe("Pizza", 2, profile1);
        profile1.addFavorite(recipe);

        recipes.addRecipe(recipe);
        recipes.addRecipe(new Recipe("Taco", 4, profile1));
        recipes.addRecipe(new Recipe("Pasta Carbonara", 1, profile2));
        recipes.addRecipe(new Recipe("Hamburger", 2, profile2));

        profiles.add(profile1);
        profiles.add(profile2);

        doNothing().when(mockUserFilehandler).writeProfile(profile1);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(recipes);

        // Makes it so that the controller uses testfiles instead of the main ones
        controller.setFilehandlers(mockRecipeFilehandler, mockUserFilehandler);

        controller.setProfile(profile1);
    }

    /**
     * This method will test if the GridPane loads properly when first loading the
     * Mainscreen
     * This includes checking if everything is built correct, but also that all
     * recipes are shown when
     * the mainscreen is first loaded
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    @DisplayName("Gridloading test")
    public void testLoadGrid() {
        ScrollPane scrollPane = lookup("#scrollPane").query();
        assertNotNull(scrollPane.getContent(), "The ScrollPane should not be empty");
        assertTrue(scrollPane.getContent() instanceof GridPane, "The ScrollPane should contain a GridPane");

        GridPane gridPane = (GridPane) scrollPane.getContent();
        assertFalse(gridPane.getChildren().isEmpty(), "The GridPane should not be empty");
        assertTrue(gridPane.getChildren().get(0) instanceof SplitPane, "The GridPane should contain SplitPanes");

        SplitPane splitPane = (SplitPane) gridPane.getChildren().get(0);
        assertEquals(2, splitPane.getItems().size(), "The SplitPane should have three children");
        assertTrue(splitPane.getItems().get(0) instanceof HBox, "The SplitPane should contain Hboxes");

        HBox hBox1 = (HBox) splitPane.getItems().get(0);
        HBox hBox2 = (HBox) splitPane.getItems().get(1);
        assertEquals(1, hBox1.getChildren().size(), "The first HBox should have one child");
        assertTrue(hBox1.getChildren().get(0) instanceof Label,
                "The first HBox should contain a label with the name of a recipe");

        assertEquals(3, hBox2.getChildren().size(), "The second HBox should have three children");
        assertTrue(hBox2.getChildren().get(0) instanceof VBox, "The second HBox should contain VBoxes");
        VBox vBox1 = (VBox) hBox2.getChildren().get(0);
        VBox vBox2 = (VBox) hBox2.getChildren().get(1);
        VBox vBox3 = (VBox) hBox2.getChildren().get(2);
        assertFalse(vBox1.getChildren().isEmpty(), "The first VBox should not be empty");
        assertTrue(vBox1.getChildren().get(0) instanceof FontAwesomeIconView,
                "There should be a FontAwesomeIconView in the first VBox");

        assertFalse(vBox2.getChildren().isEmpty(), "The second VBox should not be empty");
        assertTrue(vBox2.getChildren().get(0) instanceof Button, "There should be a Button in the second VBox");

        assertTrue(vBox3.getChildren().isEmpty(), "The third VBox should be empty");

        // Checking that all recipes are showing at initial load
        List<String> recipeNames = getRecipeNames();
        assertEquals(4, recipeNames.size(), "There should be four recipes showing");
        assertEquals(4, recipeNames.size(), "There should be 4 recipes showing");
        assertEquals("Hamburger", recipeNames.get(0), "The name of the first recipe should be 'Hamburger'");
        assertEquals("Pasta Carbonara", recipeNames.get(1),
                "The name of the second recipe should be 'Pasta Carbonara'");
        assertEquals("Taco", recipeNames.get(2), "The name of the third recipe should be 'Taco'");
        assertEquals("Pizza", recipeNames.get(3), "The name of the fourth recipe should be 'Pizza'");
    }

    /**
     * This method will test if the "Log out"-button logs you out and sends you to
     * UserLogin.fxml
     */
    @Test
    @DisplayName("Log out test")
    public void testLogOutButton() {
        clickOn("#logoutBtn");
        assertEquals("UserLogin.fxml", controller.getFileName(), "Should have switched to UserLogin.fxml");

    }

    /**
     * This method will test if the "+ Add Recipe"-button will send you to
     * addRecipe.fxml
     */
    @Test
    @DisplayName("Add recipe test")
    public void testAddRecipeButton() {
        clickOn("#addBtn");
        assertEquals("addRecipe.fxml", controller.getFileName(), "Should have switched to addRecipe.fxml");

    }

    /**
     * This method will test if the "My Recipes"-button will make the GridPane only
     * show the logged in
     * profile's recipes
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    @DisplayName("My recipes test")
    public void testMyRecipesButton() {
        clickOn("#myBtn");
        List<String> recipeNames = getRecipeNames();
        assertEquals(2, recipeNames.size(), "There should be 2 recipes showing");
        assertEquals("Taco", recipeNames.get(0), "The name of the first recipe should be 'Taco'");
        assertEquals("Pizza", recipeNames.get(1), "The name of the second recipe should be 'Pizza'");

        // Checks that the GridPane is not reloaded when you click the "My
        // Recipes"-button after the profile's recipes are already loaded
        ScrollPane scrollPane = lookup("#scrollPane").query();
        GridPane gridPaneBefore = (GridPane) scrollPane.getContent();
        clickOn("#myBtn");
        GridPane gridPaneAfter = (GridPane) scrollPane.getContent();
        assertTrue(gridPaneBefore == gridPaneAfter,
                "When clicking on a sorting option which is already shown, the grid should not be reloaded");
    }

    /**
     * This method will test if the "All Recipes"-button will make the GridPane show
     * all recipes
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    @DisplayName("All recipes test")
    public void testAllRecipesButton() {
        // Since the app start with all the recipes, we have to click on "My recipes"
        // and then on "All Recipes" again
        clickOn("#myBtn");
        clickOn("#allBtn");
        List<String> recipeNames = getRecipeNames();
        assertEquals(4, recipeNames.size(), "There should be 4 recipes showing");
        assertEquals("Hamburger", recipeNames.get(0), "The name of the first recipe should be 'Hamburger'");
        assertEquals("Pasta Carbonara", recipeNames.get(1),
                "The name of the second recipe should be 'Pasta Carbonara'");
        assertEquals("Taco", recipeNames.get(2), "The name of the third recipe should be 'Taco'");
        assertEquals("Pizza", recipeNames.get(3), "The name of the forth recipe should be 'Pizza'");

        // Checks that the GridPane is not reloaded when you click the "All
        // Recipes"-button after all the recipes already loaded
        ScrollPane scrollPane = lookup("#scrollPane").query();
        GridPane gridPaneBefore = (GridPane) scrollPane.getContent();
        clickOn("#allBtn");
        GridPane gridPaneAfter = (GridPane) scrollPane.getContent();
        assertTrue(gridPaneBefore == gridPaneAfter,
                "When clicking on a sorting option which is already shown, the grid should not be reloaded");
    }

    /**
     * This method will test if the "Favorites"-button will make the GridPane show
     * all favorites
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    @DisplayName("Favorites test")
    public void testFavoritesButton() {
        clickOn("#favoritesBtn");
        List<String> recipeNames = getRecipeNames();
        assertEquals(1, recipeNames.size(), "There should only be one recipe shown");
        assertEquals("Pizza", recipeNames.get(0), "The name of the recipe should be 'Pizza'");
        HBox hBox1 = getBottomHBox();
        VBox vBox = (VBox) hBox1.getChildren().get(0);
        FontAwesomeIconView heartBtn = (FontAwesomeIconView) vBox.getChildren().get(0);
        assertEquals(Color.RED, heartBtn.getFill(), "The heart should be red");

        // Checks that the GridPane is not reloaded when you click the
        // "Favorites"-button after the favorites are already loaded
        ScrollPane scrollPane = lookup("#scrollPane").query();
        GridPane gridPaneBefore = (GridPane) scrollPane.getContent();
        clickOn("#favoritesBtn");
        GridPane gridPaneAfter = (GridPane) scrollPane.getContent();
        assertTrue(gridPaneBefore == gridPaneAfter,
                "When clicking on a sorting option which is already shown, the grid should not be reloaded");
    }

    /**
     * This method will test if the Heart-buttons work to add and remove favorites
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    public void heartButtonTest() {
        // Finding a Heart-button
        HBox hBox = getBottomHBox();
        VBox vBox = (VBox) hBox.getChildren().get(0);
        FontAwesomeIconView heartBtn = (FontAwesomeIconView) vBox.getChildren().get(0);

        // Checks if heart-button and adding favorite works
        assertEquals(Color.WHITE, heartBtn.getFill(),
                "The heart should be white before being clicked for the first time");
        moveTo(heartBtn);
        assertEquals(2, heartBtn.getStrokeWidth(),
                "The StrokeWidth of the heart should increase to 2 when moving you mouse over it");
        moveTo("#titleLabel");
        assertEquals(1, heartBtn.getStrokeWidth(),
                "The StrokeWidth of the heart should return to 1 when moving you mouse away from it");
        clickOn(heartBtn);
        assertEquals(Color.RED, heartBtn.getFill(), "The heart should be red after being clicked");
        assertEquals(2, profiles.get(0).getFavorites().getSize(), "The profile should now have two favorites");
        assertEquals("Hamburger", profiles.get(0).getFavorites().getRecipe(1).getName(),
                "The last added favorite should be 'Hamburger'");

        // Checking if removing favorite works
        clickOn(heartBtn);
        assertEquals(Color.WHITE, heartBtn.getFill(),
                "The heart should be white after being clicked for the second time");
        assertEquals(1, profiles.get(0).getFavorites().getSize(), "The profile should now have one favorite");

        // Checking that you can re-add favorite
        clickOn(heartBtn);
        assertEquals(Color.RED, heartBtn.getFill(), "The heart should be red after being clicked");
        assertEquals(2, profiles.get(0).getFavorites().getSize(), "The profile should have two favorites");
        assertEquals("Hamburger", profiles.get(0).getFavorites().getRecipe(1).getName(),
                "The last added favorite should be 'Hamburger'");

        // Checks if it shows up correctly when sorting for favorites
        clickOn("#favoritesBtn");
        List<String> recipeNames = getRecipeNames();
        assertEquals(2, recipeNames.size(), "There should be two recipe shown");
        recipeNames = getRecipeNames();
        assertEquals("Hamburger", recipeNames.get(0), "There first recipe amongst the favorites should be 'Hamburger'");

        // Finds the same heart-button again
        HBox hBox2 = getBottomHBox();
        VBox vBox2 = (VBox) hBox2.getChildren().get(0);
        FontAwesomeIconView heartBtn2 = (FontAwesomeIconView) vBox2.getChildren().get(0);
        assertEquals(Color.RED, heartBtn2.getFill(),
                "The heart should still be red after switching to only showing favorites");

        // Checks if removing favorite while sorting for favorites will update the
        // GridPane
        clickOn(heartBtn2);
        recipeNames = getRecipeNames();
        assertEquals(1, recipeNames.size(), "There should be two recipe shown");
    }

    /**
     * This supportmethod will go through the GridPane and collect all the recipe
     * names shown
     * The method is built upon the fact that the loading of the grid is already
     * tested in testLoadGrid(),
     * so there is no need to check it twice
     * 
     * @return List with all the recipe names in the GridPane
     */
    private List<String> getRecipeNames() {
        ScrollPane scrollPane = lookup("#scrollPane").query();
        GridPane gridPane = (GridPane) scrollPane.getContent();
        return gridPane.getChildren().stream()
                .map(node -> (SplitPane) node)
                .map(sp -> sp.getItems().get(0))
                .map(node -> (HBox) node)
                .map(hBox -> hBox.getChildren().get(0))
                .map(node -> (Label) node)
                .map(label -> label.getText())
                .collect(Collectors.toList());
    }

    /**
     * This method will test if searching for recipes works properly
     * 
     * @see MainscreenControllerTest#getRecipeNames()
     */
    @Test
    @DisplayName("Search test")
    public void testSearch() {
        TextField searchBar = lookup("#txtField").query();

        // Testing if the basics work
        Platform.runLater(() -> {
            searchBar.setText("Taco");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(1, recipeNames.size(), "There should only be one recipe (Taco) showing");
            assertEquals("Taco", recipeNames.get(0), "The recipe 'Taco' should be showing");
        });

        // Testing if the basics work
        Platform.runLater(() -> {
            searchBar.setText("A");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(4, recipeNames.size(), "There should be four recipes showing");
            assertEquals("Hamburger", recipeNames.get(0), "The recipe 'Hamburger' should be showing first");
            assertEquals("Pasta Carbonara", recipeNames.get(1),
                    "The recipe 'Pasta Carbonara' should be showing second");
            assertEquals("Taco", recipeNames.get(2), "The recipe 'Taco' should be showing third");
            assertEquals("Pizza", recipeNames.get(3), "The recipe 'Pizza' should be showing third");
        });

        // Testing if the basics work
        Platform.runLater(() -> {
            searchBar.setText("");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(4, recipeNames.size(), "There should be 4 recipes showing");
            assertEquals("Hamburger", recipeNames.get(0), "The name of the first recipe should be 'Hamburger'");
            assertEquals("Pasta Carbonara", recipeNames.get(1), "The name of the second recipe should be 'Pasta Carbonara'");
            assertEquals("Taco", recipeNames.get(2), "The name of the third recipe should be 'Taco'");
            assertEquals("Pizza", recipeNames.get(3), "The name of the forth recipe should be 'Pizza'");
        });

        // Testing if if lower case matters
        Platform.runLater(() -> {
            searchBar.setText("taco");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(1, recipeNames.size(), "There should only be one recipe (Taco) showing");
        });

        // Testing if if upper case matters
        Platform.runLater(() -> {
            searchBar.setText("TACO");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(1, recipeNames.size(), "There should only be one recipe (Taco) showing");
        });

        // Testing if search still works when switching sorting
        Platform.runLater(() -> {
            searchBar.setText("Taco");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            clickOn("#myBtn");
            assertEquals("Taco", searchBar.getText(),
                    "The text in the searchbar should not change when switching to new sorting");
            List<String> recipeNames = getRecipeNames();
            assertEquals(1, recipeNames.size(),
                    "There should still only be one recipe showing after switching to new sorting");
            assertEquals("Taco", recipeNames.get(0), "The recipe 'Taco' should be showing");
        });

        // Testing if search works with multiple matches
        Platform.runLater(() -> {
            searchBar.setText("p");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            List<String> recipeNames = getRecipeNames();
            assertEquals(2, recipeNames.size(), "There should be two recipes showing");
            assertEquals("Pasta Carbonara", recipeNames.get(0), "The recipe 'Pasta Carbonara' should be first");
            assertEquals("Pizza", recipeNames.get(1), "The recipe 'Pizza' should be first");
        });

        // Testing if search works when there are no matches
        Platform.runLater(() -> {
            searchBar.setText("Tomato soup");
            searchBar.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "", "", null, false, false, false, false));
            ScrollPane scrollPane = lookup("#scrollPane").query();
            assertTrue(scrollPane.getContent() instanceof Label,
                    "There should be a label if there are no recipes matching with the search");
            Label label = (Label) scrollPane.getContent();
            assertEquals("No recipes found", label.getText(), "The label should have the text 'No recipes found'");
        });
    }

    /**
     * This method will test if the "Read More"-buttons sends you to Recipe.fxml
     * (the dedicated page for the
     * given recipe)
     * 
     * @see MainscreenControllerTest#getBottomHBox()
     */
    @Test
    @DisplayName("Read More test")
    public void testReadMoreRecipeButton() {
        // Finding a "Read More"-button
        HBox hBox = getBottomHBox();
        VBox vBox = (VBox) hBox.getChildren().get(1);
        Button readMoreBtn = (Button) vBox.getChildren().get(0);

        clickOn(readMoreBtn);
        assertEquals("Recipe.fxml", controller.getFileName(), "Should have switched to Recipe.fxml");

    }

    /**
     * This supportmethod will return the bottom HBox in the first SplitPane in the
     * GridPane
     * The method is built upon the fact that the loading of the grid is already
     * tested in testLoadGrid(),
     * so there is no need to check it twice
     * 
     * @return HBox at the bottom of the SplitPane
     */
    private HBox getBottomHBox() {
        ScrollPane scrollPane = lookup("#scrollPane").query();
        GridPane gridPane = (GridPane) scrollPane.getContent();
        SplitPane splitPane = (SplitPane) gridPane.getChildren().get(0);
        HBox hBox = (HBox) splitPane.getItems().get(1);
        return hBox;
    }
}
