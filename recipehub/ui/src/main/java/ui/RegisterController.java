package ui;

import core.Profile;
import file.RecipeHubAccess;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * The RegisterController class handles user registration and navigation between
 * screens.
 * Manages user registration, switching to the login screen, and transitioning
 * to the main screen.
 *
 * This class extends the SuperController class.
 *
 * @see SuperController
 */
public class RegisterController extends SuperController {

    @FXML
    private Label registerMessageLabel;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Parent root;

    /**
     * This method switches to the login screen.
     *
     * @param event the ActionEvent that triggers the screen switch
     * @throws IOException if the switchSceneWithInfo method throws an exception
     * @see SuperController#switchSceneWithInfo(ActionEvent, String)
     */
    public void switchToLoginScreen(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "UserLogin.fxml");
    }

    /**
     * This method registers the user if the information is correct.
     *
     * @param event the ActionEvent triggered by a register button click
     * @throws IOException if the switchSceneMain method throws an exception
     * @see SuperController#switchSceneMain(ActionEvent)
     */
    public void register(ActionEvent event) throws IOException {
        if (validateRegister(usernameField.getText(), passwordField.getText())) {
            setProfile(currentProfile);
            switchSceneMain(event);
        }
    }

    /**
     * This method validates the register information.
     * Checks if the username and password are valid and if the username already
     * exists.
     * If the information is valid, a new profile is created and saved.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the register information is correct, false otherwise
     *
     * @see Profile#isValidUsername(String)
     * @see Profile#isValidPassword(String)
     * @see RecipeHubAccess#loadProfile(String)
     * @see RecipeHubAccess#saveProfile(Profile)
     */
    public boolean validateRegister(String username, String password) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || confirmPasswordField.getText().isBlank()) {
            registerMessageLabel.setText("Please enter a username and password");
            return false;
        }
        try {
            Profile.isValidUsername(username);
            Profile.isValidPassword(password);
        } catch (IllegalArgumentException e) {
            registerMessageLabel.setText(e.getMessage());
            return false;
        }
        if (currentRecipeHubAccess.loadProfile(username) != null) {
            registerMessageLabel.setText("Username already exists");
            return false;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            registerMessageLabel.setText("Passwords do not match");
            return false;
        } else {
            currentProfile = new Profile(username, password);
            currentRecipeHubAccess.saveProfile(currentProfile);
            return true;
        }
    }

    /**
     * This method tries to register the user if the enter key is pressed.
     *
     * @param e the KeyEvent associated with the a key press
     */
    public void keyPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            registerButton.fire();
        }
    }
}
