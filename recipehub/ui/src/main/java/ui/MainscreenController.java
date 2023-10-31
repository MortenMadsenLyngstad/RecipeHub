package ui;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
 * This controller class is used to connect the mainscreeen to the logic in
 * core.
 * 
 * @author Adrian Haabpiht Solberg
 */
public class MainscreenController extends SuperController {

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

    /**
     * This method will load the gridPane with all recipes when the All
     * Recipes-button
     * is clicked on.
     */
    @FXML
    public void loadAllRecipes() {
        if (titleLabel.getText().equals(allBtn.getText())) {
            return;
        }
        titleLabel.setText(allBtn.getText());
        currentLibrary = allRecipes;
        load();
    }

    /**
     * This method will load the gridPane with the logged in profile's recipes
     * when the All Recipes-button is clicked on.
     */
    @FXML
    public void loadMyRecipes() {
        if (titleLabel.getText().equals(myBtn.getText())) {
            return;
        }
        titleLabel.setText(myBtn.getText());
        currentLibrary = currentProfile.getRecipes();
        load();
    }

    /**
     * This method will load the gridPane with the logged in profile's favorite
     * recipes
     * when the Favorites-button is clicked on.
     */
    @FXML
    public void loadFavoriteRecipes() {
        if (titleLabel.getText().equals(favoritesBtn.getText())) {
            return;
        }
        titleLabel.setText(favoritesBtn.getText());
        currentLibrary = currentProfile.getFavorites();
        load();
    }

    /**
     * This support method will decide how to load grid.
     * Made to hinder repetition of code
     */
    private void load() {
        if (txtField.getText().isEmpty()) {
            loadGrid(currentLibrary);
        } else {
            search();
        }
    }

    /**
     * This method will fill the grid with the recipes in the given recipeLibrary.
     * 
     * @param recipeLibrary - RecipeLibrary with with recipes to fill the grid with
     */
    public void loadGrid(RecipeLibrary recipeLibrary) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefWidth(scrollPane.getPrefWidth() - 37); // Scrollbar (17px), Padding (20px)
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
            int index = recipeLibrary.getSize() - i - 1;
            SplitPane splitPane = makeSplitPane(recipeLibrary.getRecipe(index));
            gridPane.add(splitPane, i % 4, i / 4);
        }

        gridPane.setFocusTraversable(true);
    }

    /**
     * This method will make a SplitPane for the given recipe.
     * The top half will contain the recipe name
     * The bottom half will contain a two buttons, one for going to the recipe's own
     * page
     * and one for marking the recipe as a favorite
     * 
     * @param recipe - The recipe to make the SplitPane for
     * @return Splitpane customized for the given recipe
     */
    private SplitPane makeSplitPane(Recipe recipe) {
        // Makes vertical SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        // Makes top half of SplitPane
        HBox hbox1 = new HBox();
        hbox1.setMinHeight(50);
        hbox1.setPadding(new Insets(0, 5, 0, 5));
        hbox1.setAlignment(Pos.CENTER);
        Label label = new Label(recipe.getName());
        hbox1.getChildren().add(label);

        // Makes "Read more"-button
        Button btn = new Button("Read more");
        btn.getStyleClass().add("green_button");
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
        setHeart(heart, recipe, currentProfile);

        // Makes bottom part of SplitPane
        HBox hbox2 = new HBox();
        hbox2.setMinHeight(50);

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

        hbox2.getChildren().addAll(subBox1, subBox2, subBox3);

        // Adds top- and bottom part to SplitPane
        splitPane.getItems().addAll(hbox1, hbox2);
        splitPane.setDividerPositions(0.5);

        return splitPane;
    }

    /**
     * This method will set the heart to red if the recipe is a favorite of the
     * current profile.
     * If not, it will set the heart to white and make it clickable
     * When clicked on, the heart will turn red and the recipe will be added to the
     * current
     * profile's favorites
     * 
     * This method is protected because it is used in RecipeController
     * 
     * @param heart          - The heart to be set
     * @param recipe         - The recipe to check if it is a favorite
     * @param currentProfile - The profile to check if the recipe is a favorite
     */
    protected void setHeart(FontAwesomeIconView heart, Recipe recipe, Profile currentProfile) {
        if (currentProfile.getFavorites().containsRecipe(recipe)) {
            heart.setFill(Color.RED);
        } else {
            heart.setFill(Color.WHITE);
        }

        heart.setOnMouseEntered(event -> {
            heart.setStrokeWidth(2);
        });
        heart.setOnMouseExited(event -> {
            heart.setStrokeWidth(1);
            ;
        });

        heart.setOnMouseClicked(event -> {
            if (heart.getFill().equals(Color.RED)) {
                heart.setFill(Color.WHITE);
                currentProfile.removeFavorite(recipe);
                recipeHubModelAccess.saveProfile(currentProfile);
                if (this.titleLabel != null) {
                    if (this.titleLabel.getText().equals("Favorites")) {
                        load();
                    }
                }

            } else {
                heart.setFill(Color.RED);
                currentProfile.addFavorite(recipe);
                recipeHubModelAccess.saveProfile(currentProfile);
            }
        });
    }

    /**
     * This method will switch screen to addRecipe.fxml when the addRecipe button is
     * clicked on.
     * 
     * @param event - The event of the Add Recipe-button beign clicked on
     * @throws IOException - if the switchSceneWithInfo method throws an exception
     */
    @FXML
    public void addRecipe(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "addRecipe.fxml", currentProfile);
    }

    /**
     * This method will switch screen to UserLogin.fxml when the addRecipe button is
     * clicked on.
     * 
     * @param event - The event of the Log Out-button beign clicked on
     * @throws IOException - if the switchSceneWithInfo method throws an exception
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "UserLogin.fxml", null);
    }

    /**
     * This method will switch scene to Recipe.fxml and give RecipeController the
     * given recipe.
     * 
     * @param event   - ActionEvent
     * @param recipe  - The recipe the user clicked on
     * @param profile - the current profile
     * @throws IOException if there are problems with the filehandling
     */
    protected void switchSceneRecipe(ActionEvent event, Recipe recipe, Profile profile)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
        root = loader.load();

        RecipeController controller = loader.getController();
        controller.setRecipe(recipe);
        controller.setProfile(profile);
        controller.populate();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        setFileName("Recipe.fxml");
    }

    /**
     * This method will handle searching for recipes.
     * It makes a modified RecipeLibrary, in accordance with the search, to be
     * loaded in the grid
     */
    @FXML
    public void search() {
        if (txtField.getText().isEmpty()) {
            loadGrid(currentLibrary);
            return;
        }

        String searchTerm = txtField.getText().trim().toLowerCase();

        List<Recipe> modifiedLibrary = currentLibrary.getRecipes().stream()
                .filter(recipe -> recipe.getName().trim().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        Collections.sort(modifiedLibrary, Comparator
                .comparingInt((Recipe r1) -> r1.getName().toLowerCase().indexOf(searchTerm))
                .reversed()
                .thenComparing((r1, r2) -> r2.getName().compareToIgnoreCase(r1.getName())));

        RecipeLibrary modifiedRecipeLibrary = new RecipeLibrary();
        modifiedRecipeLibrary.setRecipeLibrary(modifiedLibrary);

        loadGrid(modifiedRecipeLibrary);
    }

    /**
     * Custom setProfile-method.
     * This method will set currentProfile to the given profile, and then load all
     * recipes
     * Custom method needed because of favorites functionality
     * 
     * @param profile - The profile which is logged in
     */
    @Override
    protected void setProfile(Profile profile) {
        currentProfile = profile;
        allRecipes = recipeHubModelAccess.getRecipeLibrary();
        loadAllRecipes();
    }
}
