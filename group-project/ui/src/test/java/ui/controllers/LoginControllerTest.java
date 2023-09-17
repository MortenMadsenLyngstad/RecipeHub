package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import file.User_filehandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("UserLogin.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testValidateLoginWithValidCredentials() throws Exception {
        // Mock the User_filehandler to return valid credentials
        User_filehandler mockUserFileHandler = mock(User_filehandler.class);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("testuser", "Password123");
        when(mockUserFileHandler.getUserinfo()).thenReturn((Hashtable<String, String>) userInfo);
        
        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        Label loginMessageLabel = lookup("#registerMessageLabel").query();
        // Set valid credentials in the fields
        interact(() -> {
            usernameField.setText("testuser");
            passwordField.setText("Password123");
        });

        assertTrue(controller.validateLogin("testuser", "Password123"));
        assertEquals("", loginMessageLabel.getText());
    }

    @Test
    public void testValidateLoginWithInvalidUsername() throws Exception {
        // Mock the User_filehandler to return null for the username
        User_filehandler mockUserFileHandler = mock(User_filehandler.class);
        Map<String, String> userInfo = new HashMap<>();
        when(mockUserFileHandler.getUserinfo()).thenReturn((Hashtable<String, String>) userInfo);

        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        Label loginMessageLabel = lookup("#registerMessageLabel").query();
        // Set an invalid username
        interact(() -> {
            usernameField.setText("invaliduser");
            passwordField.setText("Password123");
        });

        assertFalse(controller.validateLogin("invaliduser", "Password123"));
        assertEquals("Incorrect username or password", loginMessageLabel.getText());
    }

    @Test
    public void testValidateLoginWithIncorrectPassword() throws Exception {
        // Mock the User_filehandler to return valid credentials
        User_filehandler mockUserFileHandler = mock(User_filehandler.class);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("testuser", "Password123");
        when(mockUserFileHandler.getUserinfo()).thenReturn((Hashtable<String, String>) userInfo);

        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        Label loginMessageLabel = lookup("#registerMessageLabel").query();

        // Set valid username but incorrect password
        interact(() -> {
            usernameField.setText("testuser");
            passwordField.setText("wrongpassword");
        });

        assertFalse(controller.validateLogin("testuser", "wrongpassword"));
        assertEquals("Incorrect username or password", loginMessageLabel.getText());
    }

    @Test
    public void testValidateLoginWithBlankFields() throws Exception {
        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        Label loginMessageLabel = lookup("#registerMessageLabel").query();

        // Set blank fields
        interact(() -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        assertFalse(controller.validateLogin("", ""));
        assertEquals("Please enter a username and password", loginMessageLabel.getText());
    }
}
