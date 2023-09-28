package ui.controllers;

import java.io.IOException;

import core.Profile;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import file.AddRecipeFilehandler;
import file.UserFilehandler;

public class LoginController extends SuperController {
    private UserFilehandler userFilehandler = new UserFilehandler("userinfo.json");

    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private AddRecipeFilehandler addRecipeFilehandler = new AddRecipeFilehandler("recipes.json");

    /**
     * Logs the user in if the login information is correct
     * 
     * @param event
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
     * Switches to the register screen
     * 
     * @param event
     * @throws IOException if the SwitchController.switchSceneMain method throws an
     *                     exception
     * @see SuperController#switchSceneMain(ActionEvent, String)
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
     * @throws Exception if the userFilehandler.getUserinfo() method throws an
     *                   exception
     * @see UserFilehandler#getUserinfo()
     */
    public boolean validateLogin(String uname, String pword, UserFilehandler userFilehandler) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            loginMessageLabel.setText("Please enter a username and password");
            return false;
        } else if (userFilehandler.readUsernamesAndPasswords().get(uname) == null) {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        } else if (userFilehandler.readUsernamesAndPasswords().get(uname).equals(pword)) {
            loadProfile(uname, pword);
            return true;
        } else {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        }
    }

    /**
     * This method will load currentProfile with it's username, password and all it's recipes
     * @param uname - String with the username for the profile
     * @param pword - String with the password for the profile
     */
    // ! This is not a optimal way to get the recipes for the profile
    // ! Find a way to save the recipes in the profile-file and load them from there
    public void loadProfile(String uname, String pword) {
        currentProfile = new Profile(uname, pword);
        for (Recipe r : addRecipeFilehandler.readRecipeLibrary()) {
            if (r.getAuthor().equals(uname)) {
                currentProfile.addRecipe(r);
            }
        }
    }
}
