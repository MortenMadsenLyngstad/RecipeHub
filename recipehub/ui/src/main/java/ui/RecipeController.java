package ui;

import core.Profile;
import core.Recipe;
import core.Review;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

/**
 * Controller for displaying the information of a recipe on a RecipeSreen.
 */
public class RecipeController extends SuperController {
    private MainscreenController mainscreenController = new MainscreenController();
    private Recipe recipe;
    private Boolean flag = false;
    private Alert alert;
    private Alert ratingAlert;
    private double scale = 1.0;
    private int portions = 0;

    @FXML
    private Label nameField, authorLabel, descriptionLabel, stepsLabel, portionsLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextArea descriptionText, stepsText, ingredientsText;
    @FXML
    private TextField portionsField;
    @FXML
    private FontAwesomeIconView deleteButton, heartButton, minusButton, plusButton;
    @FXML
    private Rating recipeRating;
    @FXML
    private Text averageRating;
    @FXML
    private Hyperlink numberOfRaters, numberOfComments;
    @FXML
    private VBox scrollPaneBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private SplitPane commentsPane;

    /**
     * Handels the click of the backButton, sends the user back to the mainscreen.
     * 
     * @param event The click of the backButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void backButtonClick(ActionEvent event) throws IOException {
        switchSceneMain(event, "Mainscreen.fxml");
    }

    /**
     * Populates the RecipeScreen with the information from the recipe.
     */
    public void populate() {
        // If the recipe is the users own, show the delete button
        showDeleteButton();
        showRating();
        showComments();

        mainscreenController.setHeart(heartButton, recipe, currentProfile);
        nameField.setText(recipe.getName());
        authorLabel.setText("Posted by: " + recipe.getAuthor());
        portionsField.setText("" + portions);
        descriptionText.setText(recipe.getDescription());

        List<String> steps = recipe.getSteps();

        for (int i = 1; i < steps.size() + 1; i++) {
            stepsText.appendText("Step " + i + ":  " + steps.get(i - 1) + "\n");
        }
        for (String ingredient : recipe.getIngredients()) {
            String amount = String.format("%.1f", recipe.getIngredientAmount(ingredient) * scale);
            ingredientsText.appendText(amount
                    + " " + recipe.getIngredientUnit(ingredient)
                    + " : " + ingredient + "\n");
        }
        descriptionText.positionCaret(0);
        stepsText.positionCaret(0);
        ingredientsText.positionCaret(0);

        descriptionText.setEditable(false);
        stepsText.setEditable(false);
        ingredientsText.setEditable(false);
    }

    /**
     * Handles the adding of portions through writing in the portionsField.
     * Portions must be an int larger than 0.
     */

    public void handlePortions() {
        int por = 0;
        String fault = "" + portions;
        try {
            por = Integer.parseInt(portionsField.getText());
        } catch (Exception e) {
            portionsField.setText(fault);
            portionsField.positionCaret((fault).length());
        }
        if (por <= 0 || por > 50) {
            portionsField.setText(fault);
            portionsField.positionCaret((fault).length());
        } else {
            portions = por;
            scale = scale();
            clearText();
            populate();
        }
    }

    /**
     * Clears the text from stepsText and ingredientsText to avoid dupliucates.
     * Intended use with scale operations.
     */
    private void clearText() {
        ingredientsText.setText("");
        stepsText.setText("");
    }

    /**
     * Handles the click of the plus button to add a portion to the recipe.
     */
    public void plusButtonClick() {
        portions += 1;
        scale = scale();
        clearText();
        populate();
    }

    /**
     * Handles the click of the minus button to remove a portion from the recipe.
     * Portions may not be 0 or less.
     */
    public void minusButtonClick() {
        if (portions == 1) {
            return;
        }
        portions -= 1;
        scale = scale();
        clearText();
        populate();
    }

    /**
     * Computes a scalar for scaling the recipe.
     * 
     * @return A double to set as the scalar
     */
    private double scale() {
        return ((double) portions / (double) recipe.getPortions());
    }

    /**
     * Sets the recipe for the controller.
     * 
     * @param recipe The recipe to be set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        portions = recipe.getPortions();
    }

    /**
     * Method for showing the delete button or not.
     * Will show the delete button if the user is the author of the recipe.
     */
    private void showDeleteButton() {
        if (recipe.getAuthor().equals(currentProfile.getUsername())) {
            deleteButton.setVisible(true);
            deleteButton.setOnMouseEntered(event -> {
                deleteButton.setStrokeWidth(0.7);
            });
            deleteButton.setOnMouseExited(event -> {
                deleteButton.setStrokeWidth(0.2);
                ;
            });
        } else {
            deleteButton.setVisible(false);
        }
    }

    /**
     * Method for showing the rating or not.
     * Will show the rating if the user already has rated the recipe.
     */
    private void showRating() {
        recipeRating.setRating(Math.round(recipe.getAverageRating()));
        averageRating.setText(String.valueOf(recipe.getAverageRating()));
        numberOfRaters.setText(String.valueOf("(" + recipe.getNumberOfReviewers()) + ")");

        numberOfComments.setText(String.valueOf("(" + recipe.getNumberOfcomments()) + ")");

        if (recipe.hasRated(currentProfile.getUsername())
                || this.recipe.getAuthor().equals(currentProfile.getUsername())) {
            numberOfRaters.setDisable(true);
        } else {
            return;
        }
    }

    private void showComments() {
        if (recipe.getNumberOfcomments() > 0) {
            if (scrollPaneBox.getChildren().size() > 1) {
                scrollPaneBox.getChildren().remove(1);
                scrollPaneBox.getChildren().remove(1);
            }
            Label reviewLabel = new Label("Reviews:");
            reviewLabel.setFont(new javafx.scene.text.Font("System", 24));
            scrollPaneBox.getChildren().add(1, reviewLabel);

            // Makes the splitpane for the comments
            commentsPane = new SplitPane();
            commentsPane.setMinSize(462, 370);
            commentsPane.setOrientation(Orientation.VERTICAL);
            

            // Sorts the list of comments so the newest is on top
            List<Review> reviews = new ArrayList<>(recipe.getReviews().stream()
                    .filter(r -> !r.getComment().equals("")).toList());
            Collections.reverse(reviews);

            double totalHeight = 0;
            // Makes a vbox for each comment
            for (Review r : reviews) {
                if (!r.getComment().equals("")) {
                    VBox vbox2 = new VBox();

                    // Adds the username, rating, and comment to the VBox
                    vbox2.getChildren().add(new Label(r.getReviewer() + ": "));
                    Rating rating = new Rating();
                    rating.setRating(1);
                    rating.setMax(1);
                    rating.setDisable(true);
                    vbox2.getChildren().add(rating);
                    vbox2.getChildren().add(new Label(String.valueOf(r.getRating())
                            .charAt(0) + "/5"));
                    vbox2.setPrefWidth(140);

                    // Adds the VBox to an HBox with the comment
                    HBox hbox = new HBox();
                    hbox.getChildren().add(vbox2);
                    HBox.setHgrow(vbox2, Priority.ALWAYS);
                    vbox2.setAlignment(Pos.CENTER);
                    TextArea textArea = new TextArea(r.getComment());
                    textArea.setWrapText(true);
                    textArea.setPrefHeight(40);
                    textArea.setPrefWidth(350);
                    textArea.setEditable(false);
                    textArea.setPrefHeight(65);
                    hbox.getChildren().add(textArea);
                    hbox.setScaleShape(false);
                    hbox.setMinHeight(65);
                    hbox.setMaxHeight(65);
                    commentsPane.getItems().add(hbox); 

                    totalHeight += vbox2.getPrefHeight();
                }
            }
            if (totalHeight <= 370 && commentsPane.getItems().size() > 0) {
                HBox hbox2 = new HBox();
                hbox2.setMinHeight(65);
                hbox2.setMaxHeight(65);
                commentsPane.getItems().add(hbox2);
            }
            scrollPaneBox.getChildren().add(2, commentsPane); 
        }
    }

    /**
     * Handels the click of the deleteButton.
     * 
     * If the user confirms the deletion, the recipe is deleted and the user is sent
     * back to the mainscreen.
     * 
     * @param event The click of the deleteButton
     * @throws IOException If there is an issue with loading Mainscreen.fxml
     */
    public void deleteRecipe(MouseEvent event) throws IOException {
        if (showAlert()) {
            ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
            try {
                switchSceneMain(actionEvent, "Mainscreen.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Returns the current Alert.
     * 
     * @return The current Alert
     */
    public Alert getAlert() {
        return this.alert;
    }

    /**
     * Shows an alert to the user to confirm the deletion of the recipe.
     * 
     * @return Boolean value for if the user confirmed the deletion
     */
    @FXML
    public boolean showAlert() {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete this recipe?");
        alert.setHeaderText("Delete recipe");
        alert.setContentText("If you delete this recipe, it will be gone forever. Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentRecipeHubAccess.removeRecipe(recipe);
            // If another profile has the removed recipe favorited it is also removed from
            // their favorites
            List<Profile> profiles = new ArrayList<>();
            for (Profile p : currentRecipeHubAccess.getProfiles()) {
                if (p.getUsername().equals(currentProfile.getUsername())) {
                    currentProfile.removeFavorite(recipe);
                    currentProfile.removeRecipe(recipe);
                    profiles.add(currentProfile);
                } else {
                    p.removeFavorite(recipe);
                    profiles.add(p);
                }
            }
            currentRecipeHubAccess.saveProfiles(profiles);
            flag = true;
        } else {
            flag = false;
        }
        this.alert = null;
        return flag;
    }

    /**
     * Shows a popup for the user to review the recipe.
     * The user can give a rating from 1 to 5 and write a comment.
     * If the gives a rating, the method addRating is called.
     */
    public void showRatingPopup() {
        ratingAlert = new Alert(AlertType.CONFIRMATION);
        ratingAlert.setTitle("Give your review on this recipe");

        Rating rating = new Rating();
        rating.setUpdateOnHover(true);
        rating.setMax(5);
        TextArea comment = new TextArea();
        comment.setPromptText("Write your comment here!");
        comment.setPrefWidth(200);
        comment.setWrapText(true);
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        VBox vbox1 = new VBox();
        vbox1.getChildren().add(rating);

        VBox vbox2 = new VBox();
        vbox2.getChildren().add(comment);

        splitPane.getItems().addAll(vbox1, vbox2);
        ratingAlert.setGraphic(splitPane);

        Optional<ButtonType> result = ratingAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addRating(rating.getRating(), comment.getText());
            ratingAlert.close();
        } else {
            ratingAlert.close();
        }
        this.ratingAlert = null;
    }

    /**
     * Adds a rating to the recipe.
     * 
     * @param rating  The rating to be added
     * @param comment The comment to be added
     * @throws IllegalArgumentException If the rating is not between 1 and 5,
     *                                  or if the user has already rated the recipe
     */
    public void addRating(double rating, String comment) throws IllegalArgumentException {
        recipe.addReview(new Review(rating, comment, currentProfile.getUsername()));
        currentRecipeHubAccess.saveRecipe(this.recipe);
        showRating();
        showComments();
    }

    /**
     * Shows a popup with the comments on the recipe.
     * The comments are shown in a scrollable list.
     */
    public void scrollDownToComments() {
        if (commentsPane == null) {
            return;
        }
        scrollPane.setVvalue(
                1.0 - (commentsPane.getHeight() - 385) / commentsPane.getHeight());
    }

    /**
     * Returns the current ratingAlert.
     * 
     * @return The ratingAlert
     */
    public Alert getRatingAlert() {
        return this.ratingAlert;
    }
}
