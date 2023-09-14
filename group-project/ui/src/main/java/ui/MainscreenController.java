package ui;

import core.Recipe;
import core.RecipeLibrary;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class MainscreenController {


    @FXML
    private GridPane mainGrid;

    @FXML
    private Button addBtn, allBtn, myBtn;

    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        titleLabel.setText(allBtn.getText());
        loadGrid(makeRecipeLibrary());


    }

    @FXML
    public void loadAll() {
        if (titleLabel.getText() ==  allBtn.getText()) {
            return;
        }
        titleLabel.setText(allBtn.getText());
        loadGrid(makeRecipeLibrary());

    }

    @FXML
    public void loadMy() {
        if (titleLabel.getText() ==  myBtn.getText()) {
            return;
        }
        titleLabel.setText(myBtn.getText());
        loadGrid(makeRecipeLibrary());
    }

    public void loadGrid(RecipeLibrary recipeLibrary) {
        for (int i = 0; i < 4; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPercentWidth(25);
            mainGrid.getColumnConstraints().add(columnConstraint);
        }

        for (int i = 0; i < recipeLibrary.getSize(); i++) {
            SplitPane splitPane = makeSplitPane(recipeLibrary.getRecipe(i));
            mainGrid.add(splitPane, i % 4, 0);
        }
    }

    public SplitPane makeSplitPane(Recipe recipe) {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        Label label = new Label(recipe.getName());
        Button btn = new Button("Read more");
        splitPane.getItems().addAll(label, btn);
        return splitPane;
    }

    public RecipeLibrary makeRecipeLibrary() {
        RecipeLibrary recipeLibrary = new RecipeLibrary();
        Recipe r1 = new Recipe("Kake", 5);
        Recipe r2 = new Recipe("Melk", 5);
        Recipe r3 = new Recipe("Sjokolade", 4);
        Recipe r4 = new Recipe("Kanin", 5);
        Recipe r5 = new Recipe("Okse", 1);
        recipeLibrary.addRecipe(r1);
        recipeLibrary.addRecipe(r2);
        recipeLibrary.addRecipe(r3);
        recipeLibrary.addRecipe(r4);
        recipeLibrary.addRecipe(r5);
        return recipeLibrary;
    }




}
