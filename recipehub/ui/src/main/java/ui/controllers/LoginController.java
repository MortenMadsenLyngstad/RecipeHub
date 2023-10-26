package ui.controllers;

import core.PasswordHasher;
import core.Profile;
import file.UserFilehandler;
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
 * This controller class is used to connect the loginscreen to the logic in
 * core.
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
     * Logs the user in if the login information is correct.
     * 
     * @param event The ActionEvent triggered by a login button click
     * @throws Exception   if the validateLogin method throws an exception
     * @throws IOException if the SwitchController.switchToMainScreen method throws
     *                     an exception
     * @see SuperController#switchSceneWithInfo(ActionEvent, String, Profile)
     */
    public void login(ActionEvent event) throws Exception {
        if (validateLogin(usernameField.getText(), passwordField.getText(), userFilehandler)) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
        }
    }

    /**
     * Switches to the register screen.
     * 
     * @param event The ActionEvent that triggers the screen switch
     * @throws IOException if the SwitchController.switchSceneMain method throws an
     *                     exception
     * @see SuperController#switchSceneMain(ActionEvent, String)
     */
    public void switchToRegisterScreen(ActionEvent event) throws IOException {
        switchSceneMain(event, "RegisterScreen.fxml");
    }

    /**
     * Validates the login information.
     * 
     * @param uname The username to validate
     * @param pword The password to validate
     * @return true if the login information is correct, false otherwise
     * @throws Exception if the userFilehandler.getUserinfo() method throws an
     *                   exception
     * @see UserFilehandler#getUserinfo()
     */
    public boolean validateLogin(String uname, String pword, UserFilehandler userFilehandler) {
        String storedPassword = userFilehandler.readUsernamesAndPasswords().get(uname);
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            loginMessageLabel.setText("Please enter a username and password");
            return false;
        } else if (userFilehandler.readUsernamesAndPasswords().get(uname) == null) {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        } else if (PasswordHasher.verifyPassword(pword, storedPassword)) {
            loadProfile(uname, PasswordHasher.hashPassword(pword));
            return true;
        } else {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        }
    }

    /**
     * This method will load currentProfile with it's username, password and all.
     * it's recipes
     * 
     * @param uname - String with the username for the profile
     * @param pword - String with the password for the profile
     */
    public void loadProfile(String uname, String pword) {
        for (Profile profile : userFilehandler.readProfiles()) {
            if (profile.getUsername().equals(uname)) {
                currentProfile = profile;
            }
        }
    }

    /**
     * This method will try to log the user in if the enter key is pressed.
     * 
     * @param e - KeyEvent
     * @throws Exception if the validateLogin method throws an exception
     * @see #validateLogin(String, String, UserFilehandler)
     */
    public void keyPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            loginButton.fire();
        }
    }
}
