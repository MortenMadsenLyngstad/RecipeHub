package ui;

import core.PasswordHasher;
import core.Profile;
import file.RecipeHubAccess;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This controller class handles user login and navigation between screens. Manages user
 * authentication, switching to the main screen, and transitioning to the registration screen.
 */
public class LoginController extends SuperController {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    /**
     * This method logs the user in if the login information is correct.
     *
     * @param event the ActionEvent triggered by a login button click
     * @throws IOException if the switchSceneMain method throws an exception
     * @see SuperController#switchSceneMain(ActionEvent, String, Profile)
     */
    public void login(ActionEvent event) throws IOException {
        if (validateLogin(usernameField.getText(), passwordField.getText())) {
            setProfile(currentProfile);
            switchSceneMain(event, "Mainscreen.fxml");
        }
    }

    /**
     * This method switches to the register screen.
     *
     * @param event the ActionEvent that triggers the screen switch
     * @throws IOException if the switchSceneMain method throws an exception
     * @see SuperController#switchSceneMain(ActionEvent, String)
     */
    public void switchToRegisterScreen(ActionEvent event) throws IOException {
        switchSceneWithInfo(event, "RegisterScreen.fxml");
    }

    /**
     * This method validates the login information.
     *
     * Checks if the username or password fields are empty. Checks if the username exists in the
     * database and if the password matches the saved password.
     *
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the login information is correct, false otherwise
     * @see RecipeHubAccess#loadProfile(String)
     * @see PasswordHasher#verifyPassword(String, String)
     */
    public boolean validateLogin(String username, String password) {
        Profile profile = currentRecipeHubAccess.loadProfile(username);

        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            loginMessageLabel.setText("Please enter a username and password");
            return false;
        } else if (profile == null
                || !PasswordHasher.verifyPassword(password, profile.getHashedPassword())) {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        } else {
            currentProfile = profile;
            return true;
        }
    }

    /**
     * This method will try to log the user in if the enter key is pressed.
     *
     * @param event keyEvent
     */
    public void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            loginButton.fire();
        }
    }
}
