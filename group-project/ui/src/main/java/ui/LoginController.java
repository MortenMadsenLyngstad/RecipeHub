package ui;

import java.io.IOException;

import core.User_filehandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private User_filehandler user_filehandler = new User_filehandler();
    private SwitchController SwitchController = new SwitchController();

    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private boolean validateLogin(String uname, String pword) throws Exception {
        if (user_filehandler.getUserinfo().get(uname) == null) {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        } else if (user_filehandler.getUserinfo().get(uname).equals(pword)) {
            return true;
        } else {
            loginMessageLabel.setText("Incorrect username or password");
            return false;
        }
    }

    public void login(ActionEvent event) throws Exception {
        if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
            if (validateLogin(usernameField.getText(), passwordField.getText())) {
                SwitchController.switchToMainScreen(event);
            }
        } else {
            loginMessageLabel.setText("Please enter a username and password");
        }
    }

    public void switchToRegisterScreen(ActionEvent event) throws IOException {
        System.out.println(user_filehandler.getUserinfo());

        SwitchController.switchSceneMain(event, "RegisterScreen.fxml");
    }
}
