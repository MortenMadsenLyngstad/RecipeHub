package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainscreenController extends AbstractController{
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button addBtn, allBtn, myBtn;

    @FXML
    private Label titleLabel;

    private GridPane gridPane;

    /**
     * This method 
     */
    @FXML
    public void initialize() {
        titleLabel.setText(allBtn.getText());

        gridPane = new GridPane();
        gridPane.setPrefWidth(scrollPane.getPrefWidth()-37); // Trekker fra scrollbar (17px) og padding (20px)
        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i < 4; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPercentWidth(25);
            gridPane.getColumnConstraints().add(columnConstraint);
        }

        scrollPane.setContent(gridPane);
        loadGrid(makeRecipeLibrary());
    }

    /**
     * This method will load the gridPane with all recipes when the All Recipes-button is clicked on
     */
    @FXML
    public void loadAll() {
        if (titleLabel.getText() ==  allBtn.getText()) {
            return;
        }
        titleLabel.setText(allBtn.getText());
        // Uses makeRecipeLibrary temporarly
        // Supposed to read all recipes from file
        loadGrid(makeRecipeLibrary());

    }

    /**
     * This method will load the gridPane with the logged in profile's recipes 
     * when the All Recipes-button is clicked on
     */
    @FXML
    public void loadMy() {
        if (titleLabel.getText() ==  myBtn.getText()) {
            return;
        }
        titleLabel.setText(myBtn.getText());
        // Uses makeRecipeLibrary temporarly
        //Supposed to be the RecipeLibrary from the logged in profile
        loadGrid(makeRecipeLibrary());
    }

    /**
     * This method will fill the grid with the recipes in the given recipeLibrary
     * @param recipeLibrary - RecipeLibrary with with recipes to fill the grid with
     */
    public void loadGrid(RecipeLibrary recipeLibrary) {
        gridPane.getChildren().clear();

        for (int i = 0; i < recipeLibrary.getSize(); i++) {
            SplitPane splitPane = makeSplitPane(recipeLibrary.getRecipe(i));
            gridPane.add(splitPane, i % 4, i / 4);
        }
    }

    /**
     * This method will make a SplitPane for the given recipe.
     * The top half will contain the recipe name, while the lower half will contain a way 
     * to go to the recipes own page
     * @param recipe - The recipe to make the SplitPane for
     * @return Splitpane customized for the given recipe
     */
    private SplitPane makeSplitPane(Recipe recipe) {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();

        Label label = new Label(recipe.getName());
        Button btn = new Button("Read more");

        vBox1.getChildren().add(label);
        vBox2.getChildren().add(btn);

        vBox1.setMinHeight(50);
        vBox2.setMinHeight(50);
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(0, 5, 0, 5));
        vBox2.setPadding(new Insets(0, 5, 0, 5));

        splitPane.getItems().addAll(vBox1, vBox2);
        splitPane.setDividerPositions(0.5);

        return splitPane;
    }

    /**
     * Temporary method which makes a RecipeLibrary we can use
     * @return RecipeLibrary filles with similar recipes
     */
    public RecipeLibrary makeRecipeLibrary() {
        Profile profile = new Profile("Username", "Password");

        RecipeLibrary recipeLibrary = new RecipeLibrary();
        for (int i = 0; i < 15; i++) {
            recipeLibrary.addRecipe(new Recipe("Sjokoladekake", 5, profile));
        }
        return recipeLibrary;
    }

    /**
     * This method will switch screen to addRecipe.fxml when the addRecipe button is clicked on
     * @param event - The event of the Add Recipe-button beign clicked on
     * @throws IOException
     */
    @FXML
    public void addRecipe(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "addRecipe.fxml",currentProfile);
    }

    /**
     * This method will switch screen to UserLogin.fxml when the addRecipe button is clicked on
     * @param event - The event of the Log Out-button beign clicked on
     * @throws IOException
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "UserLogin.fxml", null);
    }

    protected void currentProfile(Profile profile) {
        currentProfile = profile;
    }
}
