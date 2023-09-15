package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RecipeController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ListView<String> ingredientsView;
    @FXML
    private TextArea stepsArea;

    private Scene scene;
    private Stage stage;
    private Recipe r;

    public void backButtonClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameField.setText(r.getName());
        descriptionArea.setText("Posted by: " + r.getAuthor().getUsername() + "\n" + r.getDescription());
        for (String ingredient : r.getIngredients()) {
            ingredientsView.getItems().add(
                    r.getIngredientAmount(ingredient) + " " + r.getIngredientUnit(ingredient) + " : " + ingredient);
        }
        int i = 1;
        String s = "";
        for (String step : r.getSteps()) {
            s += i + ".  " + step + "\n";
            i++;
        }
        stepsArea.setText(s);
    }
}
