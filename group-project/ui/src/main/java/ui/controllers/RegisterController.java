package ui.controllers;

import java.io.IOException;

import core.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.User_filehandler;

public class RegisterController {
    private User_filehandler user_filehandler = new User_filehandler();
    private SwitchController SwitchController = new SwitchController();
    public Profile currentProfile;

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

    public void switchToLoginScreen(ActionEvent event) throws IOException {
        SwitchController.switchSceneMain(event, "UserLogin.fxml");
    }

    public void register(ActionEvent event) throws IOException {
        if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false
                && confirmPasswordField.getText().isBlank() == false) {
            if (passwordField.getText().equals(confirmPasswordField.getText()) 
            && validateRegister(usernameField.getText(), passwordField.getText())) {
                SwitchController.switchToMainScreen(event);
            }
        } else {
            registerMessageLabel.setText("Please enter a username and password");
        }
    }

    private boolean validateRegister(String uname, String pword) {
        try {
            Profile.isValidPassword(pword);
        } catch (IllegalArgumentException e) {
            registerMessageLabel.setText(e.getMessage());
            return false;
        }

        if (user_filehandler.getUserinfo().get(uname) != null) {
            registerMessageLabel.setText("Username already exists");
            return false;
        }
        user_filehandler.writeUserinfo(uname, pword);
        user_filehandler.getUserinfo().put(uname, pword);
        currentProfile(uname, pword);
        System.out.println(user_filehandler.getUserinfo());
        return true;

    }

    public Profile currentProfile(String uname, String pword) {
        try {
            currentProfile = new Profile(usernameField.getText(),
                    passwordField.getText());
            return currentProfile;
        } catch (IllegalArgumentException e) {
            registerMessageLabel.setText(e.getMessage());
        }
        return null;
    }
}