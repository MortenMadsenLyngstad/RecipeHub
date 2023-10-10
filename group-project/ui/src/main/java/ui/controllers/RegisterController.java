package ui.controllers;

import java.io.IOException;

import core.Profile;
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
import file.UserFilehandler;

public class RegisterController extends SuperController {
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");

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
     * Switches to the login screen
     * 
     * @param event
     * @throws IOException if the switchSceneMain method throws an
     *                     exception
     * @see SuperController#switchSceneMain(ActionEvent, String)
     */
    public void switchToLoginScreen(ActionEvent event) throws IOException {
        switchSceneMain(event, "UserLogin.fxml");
    }

    /**
     * Registers the user if the register information is correct
     * 
     * @param event
     * @throws IOException if the switchceneWithInfo method throws
     *                     an exception
     * @see SuperController#switchSceneWithInfo(ActionEvent, String, Profile)
     */
    public void register(ActionEvent event) throws IOException {
        if (validateRegister(usernameField.getText(), passwordField.getText(), userFilehandler)) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
        }
    }

    /**
     * Changes the userFilehandler
     * 
     * @param userFilehandler - the new userFilehandler
     */
    public void setUserFilehandler(UserFilehandler userFilehandler) {
        this.userFilehandler = userFilehandler;
    }

    /**
     * Validates the register information
     * 
     * @param uname
     * @param pword
     * @return true if the register information is correct, false otherwise
     * 
     * @see Profile#isValidUsername(String)
     * @see Profile#isValidPassword(String) 
     * @see UserFilehandler#writeUserinfo(String, String)
     * @see UserFilehandler#getUserinfo()
     */
    public boolean validateRegister(String uname, String pword, UserFilehandler userFilehandler) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || confirmPasswordField.getText().isBlank()) {
            registerMessageLabel.setText("Please enter a username and password");
            return false;
        }
        try {
            Profile.isValidUsername(uname);
            Profile.isValidPassword(pword);
        } catch (IllegalArgumentException e) {
            registerMessageLabel.setText(e.getMessage());
            return false;
        }
        if (userFilehandler.readUsernamesAndPasswords().get(uname) != null) {
            registerMessageLabel.setText("Username already exists");
            return false;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            registerMessageLabel.setText("Passwords do not match");
            return false;
        } else {
            currentProfile = new Profile(uname, pword);
            userFilehandler.writeProfile(currentProfile);
            userFilehandler.readUsernamesAndPasswords().put(uname, pword);
            return true;
        }
    }

    /**
     * Tries to register the user if the enter key is pressed
     * 
     * @param e
     * @throws IOException if the register method throws an exception
     * @see RegisterController#register(ActionEvent)
     */
    public void keyPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER))
            registerButton.fire();
    }
}