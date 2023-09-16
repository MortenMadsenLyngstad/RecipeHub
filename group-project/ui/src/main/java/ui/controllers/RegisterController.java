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
import javafx.stage.Stage;
import file.User_filehandler;

public class RegisterController extends AbstractController{
    private User_filehandler user_filehandler = new User_filehandler();

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

    public void switchToLoginScreen(ActionEvent event) throws IOException {
        switchSceneMain(event, "UserLogin.fxml");
    }

    public void register(ActionEvent event) throws IOException {
        if (validateRegister(usernameField.getText(), passwordField.getText())) {
            switchSceneWithInfo(event, "Mainscreen.fxml", currentProfile);
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
        } else if (usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || confirmPasswordField.getText().isBlank()) {
            registerMessageLabel.setText("Please enter a username and password");
            return false;
        } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            registerMessageLabel.setText("Passwords do not match");
            return false;
        } else {
            user_filehandler.writeUserinfo(uname, pword);
            user_filehandler.getUserinfo().put(uname, pword);
            currentProfile = new Profile(uname, pword);
            return true;
        }
    }

    @Override
    protected void currentProfile(Profile profile) {
        currentProfile = profile;
    }
}