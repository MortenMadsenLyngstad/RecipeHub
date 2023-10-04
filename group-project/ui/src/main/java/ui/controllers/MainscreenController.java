package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import file.RecipeFilehandler;
import file.UserFilehandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This controller class is used to connect the mainscreeen to the logic in core
 * @author Adrian Haabpiht Solberg
 */
public class MainscreenController extends SuperController{

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button addBtn, allBtn, myBtn, favoritesBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField txtField;

    private RecipeLibrary allRecipes;
    private RecipeLibrary currentLibrary;
    private RecipeFilehandler recipeFilehandler = new RecipeFilehandler("recipes.json");
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");

    /**
     * This method initializes the GridPane and the title
     */
    @FXML
    public void initialize() {
        allRecipes = recipeFilehandler.readRecipeLibrary();
    }

    /**
     * This method will load the gridPane with all recipes when the All Recipes-button is clicked on
     */
    @FXML
    public void loadAllRecipes() {
        if (titleLabel.getText() ==  allBtn.getText()) {
            return;
        }
        titleLabel.setText(allBtn.getText());
        currentLibrary = allRecipes;
        load();
    }

    /**
     * This method will load the gridPane with the logged in profile's recipes 
     * when the All Recipes-button is clicked on
     */
    @FXML
    public void loadMyRecipes() {
        if (titleLabel.getText() ==  myBtn.getText()) {
            return;
        }
        titleLabel.setText(myBtn.getText());
        currentLibrary = currentProfile.getRecipes();
        load();
    }

    /**
     * This method will load the gridPane with the logged in profile's favorite recipes 
     * when the Favorites-button is clicked on
     */
    @FXML
    public void loadFavoriteRecipes() {
        if (titleLabel.getText() ==  favoritesBtn.getText()) {
            return;
        }
        titleLabel.setText(favoritesBtn.getText());
        currentLibrary = currentProfile.getFavorites();
        load();
    }

    /**
     * This support method will decide how to load grid
     * Made to hinder repetition of code
     */
    private void load() {
        if (txtField.getText().isEmpty()) {
            loadGrid(currentLibrary);
        }
        else {
            search();
        }
    }

    /**
     * This method will fill the grid with the recipes in the given recipeLibrary
     * @param recipeLibrary - RecipeLibrary with with recipes to fill the grid with
     */
    public void loadGrid(RecipeLibrary recipeLibrary) {
        GridPane gridPane = new GridPane();
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
        gridPane.getChildren().clear();
        if (recipeLibrary.getSize() == 0) {
            Label label = new Label("No recipes found");
            label.setFont(new Font(30));
            scrollPane.setContent(label);
            scrollPane.getContent().setTranslateX(230);
            scrollPane.getContent().setTranslateY(105);
            return;
        }
        for (int i = 0; i < recipeLibrary.getSize(); i++) {
            SplitPane splitPane = makeSplitPane(recipeLibrary.getRecipe(recipeLibrary.getSize()-i-1));
            gridPane.add(splitPane, i % 4, i / 4);
        }

        gridPane.setFocusTraversable(true);
    }

    /**
     * This method will make a SplitPane for the given recipe.
     * The top half will contain the recipe name
     * The bottom half will contain a two buttons, one for going to the recipe's own page 
     * and one for marking the recipe as a favorite
     * @param recipe - The recipe to make the SplitPane for
     * @return Splitpane customized for the given recipe
     */
    private SplitPane makeSplitPane(Recipe recipe) {
        // Makes vertical SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        // Makes top half of SplitPane
        HBox hBox1 = new HBox();
        hBox1.setMinHeight(50);
        hBox1.setPadding(new Insets(0, 5, 0, 5));
        hBox1.setAlignment(Pos.CENTER);
        Label label = new Label(recipe.getName());
        hBox1.getChildren().add(label);

        // Makes "Read more"-button
        Button btn = new Button("Read more");
        btn.setOnAction(event -> {
            try {
                switchSceneRecipe(event, recipe, currentProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Maked favorites/heart-button
        FontAwesomeIconView heart = new FontAwesomeIconView(FontAwesomeIcon.HEART);
        heart.setStroke(Color.RED);
        if (currentProfile.getFavorites().containsRecipe(recipe)) {
            heart.setFill(Color.RED);
        }
        else {
            heart.setFill(Color.WHITE);
            heart.setOnMouseClicked(event -> {
                heart.setFill(Color.RED);
                heart.setOnMouseClicked(null);
                heart.setOnMouseEntered(null);
                heart.setOnMouseExited(null);
                currentProfile.addFavorite(recipe);
                userFilehandler.writeProfile(currentProfile);
            });
            heart.setOnMouseEntered(event -> {
                heart.setStrokeWidth(2);
            });
            heart.setOnMouseExited(event -> {
                heart.setStrokeWidth(1);;
            });
        }

        // Makes bottom part of SplitPane
        HBox hBox2 = new HBox();
        hBox2.setMinHeight(50);

        VBox subBox1 = new VBox();
        VBox subBox2 = new VBox();
        VBox subBox3 = new VBox();
        HBox.setHgrow(subBox1, Priority.ALWAYS);
        HBox.setHgrow(subBox2, Priority.ALWAYS);
        HBox.setHgrow(subBox3, Priority.ALWAYS);
        subBox1.setAlignment(Pos.CENTER);
        subBox2.setAlignment(Pos.CENTER);
        subBox3.setAlignment(Pos.CENTER);
        subBox1.setMinWidth(25);
        subBox2.setFillWidth(true);
        subBox3.setMinWidth(25);

        subBox1.getChildren().add(heart);
        subBox2.getChildren().add(btn);

        hBox2.getChildren().addAll(subBox1, subBox2, subBox3);

        // Adds top- and bottom part to SplitPane
        splitPane.getItems().addAll(hBox1, hBox2);
        splitPane.setDividerPositions(0.5);

        return splitPane;
    }

    /**
     * This method will switch screen to addRecipe.fxml when the addRecipe button is clicked on
     * @param event - The event of the Add Recipe-button beign clicked on
     * @throws IOException
     */
    @FXML
    public void addRecipe(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "addRecipe.fxml", currentProfile);
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

    /**
     * This method will swich scene to Recipe.fxml and give RecipeController the given recipe
     * @param event - ActionEvent
     * @param file - The file you want to change to
     * @param recipe - The recipe the user clicked on
     * @throws IOException if there are problems with the filehandling
     */
    protected void switchSceneRecipe(ActionEvent event, Recipe recipe, Profile profile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
        root = loader.load();

        RecipeController controller = loader.getController();
        controller.setRecipe(recipe);
        controller.setProfile(profile);
        controller.populate();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method will handle searching for recipes
     * It makes a modified RecipeLibrary, in accordance with the search, to be loaded in the grid
     */
    @FXML
    public void search() {
        RecipeLibrary modifiedLibrary = new RecipeLibrary();
        for (Recipe recipe : currentLibrary) {
            if (recipe.getName().trim().toLowerCase().contains(txtField.getText().trim().toLowerCase())) {
                modifiedLibrary.addRecipe(recipe);
            }
        }
        loadGrid(modifiedLibrary);
    }

    @Override
    protected void setProfile(Profile profile) {
        currentProfile = profile;
        loadAllRecipes();
    }
}
