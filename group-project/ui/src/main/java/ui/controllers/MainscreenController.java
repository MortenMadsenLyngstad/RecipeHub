package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.AddRecipeFilehandler;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private Button addBtn, allBtn, myBtn;

    @FXML
    private Label titleLabel;

    private GridPane gridPane;

    AddRecipeFilehandler addRecipeFilehandler = new AddRecipeFilehandler();

    /**
     * This method initializes the GridPane and the title
     */
    @FXML
    public void initialize() {
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
        loadAllRecipes();
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
        loadGrid(addRecipeFilehandler.loadRecipeLibrary());

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
        loadGrid(currentProfile.getRecipes());
    }

    /**
     * This method will fill the grid with the recipes in the given recipeLibrary
     * @param recipeLibrary - RecipeLibrary with with recipes to fill the grid with
     */
    public void loadGrid(RecipeLibrary recipeLibrary) {
        gridPane.getChildren().clear();
        if (recipeLibrary.getSize() == 0) {
            Label label = new Label("No recipes added yet");
            label.setFont(new Font(30));
            scrollPane.setContent(label);
            scrollPane.getContent().setTranslateX(194);
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
        btn.setOnAction(event -> {
            try {
                switchSceneRecipe(event, recipe, currentProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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
}
