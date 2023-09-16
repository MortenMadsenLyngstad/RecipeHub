package ui.controllers;

import java.io.IOException;

import core.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import file.User_filehandler;

public class LoginController extends AbstractController{
    private User_filehandler user_filehandler = new User_filehandler();

    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    /**
     * Logs the user in if the login information is correct
     * 
     * @param event
     * @throws Exception   if the validateLogin method throws an exception
     * @throws IOException if the SwitchController.switchToMainScreen method throws
     *                     an exception
     * @see SwitchController#switchSceneMain(ActionEvent, String)
     */
    public void login(ActionEvent event) throws Exception {
        if (validateLogin(usernameField.getText(), passwordField.getText())) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
        }
    }

    /**
     * Switches to the register screen
     * 
     * @param event
     * @throws IOException if the SwitchController.switchSceneMain method throws an
     *                     exception
     */
    public void switchToRegisterScreen(ActionEvent event) throws IOException {
        switchSceneMain(event, "RegisterScreen.fxml");
    }

    /**
     * Validates the login information
     * 
     * @param uname
     * @param pword
     * @return true if the login information is correct, false otherwise
     * @throws Exception if the user_filehandler.getUserinfo() method throws an
     *                   exception
     * @see User_filehandler#getUserinfo()
     */
    private boolean validateLogin(String uname, String pword) throws Exception {
        if (user_filehandler.getUserinfo().get(uname) == null) {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        } else if (user_filehandler.getUserinfo().get(uname).equals(pword)) {
            currentProfile = new Profile(uname, pword);
            return true;
        } else if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            loginMessageLabel.setText("Please enter a username and password");
            return false;
        } else {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        }
    }

    @Override
    protected void currentProfile(Profile profile) {
        currentProfile = null;
    }
}
