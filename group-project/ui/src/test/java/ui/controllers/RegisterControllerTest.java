package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Hashtable;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import file.User_filehandler;


public class RegisterControllerTest extends ApplicationTest {

    private RegisterController controller;
    private Parent root;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Label registerMessageLabel;
    private Hashtable<String, String> userInfo = new Hashtable<>();
    private User_filehandler mockUserFileHandler = mock(User_filehandler.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RegisterScreen.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setFile("/test.csv");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        usernameField = lookup("#usernameField").query();
        passwordField = lookup("#passwordField").query();
        confirmPasswordField = lookup("#confirmPasswordField").query();
        registerMessageLabel = lookup("#registerMessageLabel").query();
    }

    @Test
    public void testValidateRegisterWithValidData() {
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        Platform.runLater(() -> {
            usernameField.setText("newuser");
            passwordField.setText("Password123");
            confirmPasswordField.setText("Password123");
        });

        Platform.runLater(() -> {
            Assertions.assertTrue(controller.validateRegister("newuser", "Password123", controller.file, mockUserFileHandler));
            Assertions.assertEquals("Enter username and password", registerMessageLabel.getText());
        });
    }
        

    @Test
    public void testValidateRegisterWithInvalidPassword() {
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        Platform.runLater(() -> {
            usernameField.setText("testuser");
            passwordField.setText("weak");
            confirmPasswordField.setText("weak");
        });

        Platform.runLater(() -> {
            Assertions.assertFalse(controller.validateRegister("testuser", "weak", controller.file, mockUserFileHandler));
            Assertions.assertEquals("Password is too short", registerMessageLabel.getText());
        });
    }

    @Test
    public void testValidateRegisterWithUsernameExists() {
        userInfo.put("existinguser", "Password123");
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        // Set a username that already exists
        Platform.runLater(() -> {
            usernameField.setText("existinguser");
            passwordField.setText("Password123");
            confirmPasswordField.setText("Password123");
        });

        // Use Platform.runLater() for Assertions as well
        Platform.runLater(() -> {
            assertFalse(controller.validateRegister("existinguser", "Password123", controller.file, mockUserFileHandler));
            assertEquals("Username already exists", registerMessageLabel.getText());
        });
    }
}
