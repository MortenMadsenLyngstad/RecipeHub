package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import file.User_filehandler;


public class RegisterControllerTest extends ApplicationTest {

    private RegisterController controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RegisterScreen.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    

    @Test
    public void testValidateRegisterWithValidData() {
        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        PasswordField confirmPasswordField = lookup("#confirmPasswordField").query();
        Label registerMessageLabel = lookup("#registerMessageLabel").query();

        // Set valid data in the fields
        interact(() -> {
            usernameField.setText("testuser");
            passwordField.setText("Password123");
            confirmPasswordField.setText("Password123");
        });

        Assertions.assertTrue(controller.validateRegister("testuser", "Password123"));
        Assertions.assertEquals("", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithInvalidPassword() {
        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        PasswordField confirmPasswordField = lookup("#confirmPasswordField").query();
        Label registerMessageLabel = lookup("#registerMessageLabel").query();

        // Set an invalid password
        interact(() -> {
            usernameField.setText("testuser");
            passwordField.setText("weak");
            confirmPasswordField.setText("weak");
        });

        Assertions.assertFalse(controller.validateRegister("testuser", "weak"));
        Assertions.assertEquals("Password is too short", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithUsernameExists() {
        TextField usernameField = lookup("#usernameField").query();
        PasswordField passwordField = lookup("#passwordField").query();
        PasswordField confirmPasswordField = lookup("#confirmPasswordField").query();
        Label registerMessageLabel = lookup("#registerMessageLabel").query();

        // Mock User_filehandler to return an existing username
        User_filehandler mockUserFileHandler = mock(User_filehandler.class);
        when(mockUserFileHandler.getUserinfo()).thenReturn((Hashtable<String, String>) Map.of("existinguser", "Password123"));

        // Set a username that already exists
        interact(() -> {
            usernameField.setText("existinguser");
            passwordField.setText("Password123");
            confirmPasswordField.setText("Password123");
        });

        assertFalse(controller.validateRegister("existinguser", "Password123"));
        assertEquals("Username already exists", registerMessageLabel.getText());
    }
}
