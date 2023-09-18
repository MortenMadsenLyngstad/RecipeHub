package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import file.User_filehandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label loginMessageLabel;
    private Hashtable<String, String> userInfo = new Hashtable<>();
    private User_filehandler mockUserFileHandler = mock(User_filehandler.class);


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("UserLogin.fxml"));
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
        loginMessageLabel = lookup("#loginMessageLabel").query();
    }


    protected void deleteFile() {
        try {
            String filePath = Path.of(System.getProperty("user.home")).toString() + "/test.csv";
            Files.delete(Paths.get(filePath));
        }
        catch (Exception e) {
            System.out.println("Error deleting file");
        }
    }

    @Test
    public void testValidateLoginWithValidCredentials() throws Exception {
        userInfo.put("testuser", "Password123");
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        Platform.runLater(() -> {
            usernameField.setText("testuser");
            passwordField.setText("Password123");
        });

        Platform.runLater(() -> {
            assertTrue(controller.validateLogin("testuser", "Password123", controller.file, mockUserFileHandler));
            assertEquals("Enter username and password", loginMessageLabel.getText());
        });
        this.deleteFile();
    }

    @Test
    public void testValidateLoginWithInvalidUsername() throws Exception {
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        Platform.runLater(() -> {
            usernameField.setText("invaliduser");
            passwordField.setText("Password123");
        });

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("invaliduser", "Password123", controller.file, mockUserFileHandler));
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });
        this.deleteFile();
    }

    @Test
    public void testValidateLoginWithIncorrectPassword() throws Exception {
        userInfo.put("testuser", "Password123");
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn((Hashtable<String, String>) userInfo);

        Platform.runLater(() -> {
            usernameField.setText("testuser");
            passwordField.setText("wrongpassword");
        });

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("testuser", "wrongpassword", controller.file, mockUserFileHandler));
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });
        this.deleteFile();
    }

    @Test
    public void testValidateLoginWithBlankFields() throws Exception {
        when(mockUserFileHandler.getUserinfo(controller.file)).thenReturn(userInfo);

        Platform.runLater(() -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("", "", controller.file, mockUserFileHandler));
            assertEquals("Incorrect username or password", loginMessageLabel.getText());  
        });
        this.deleteFile();
    }
}
