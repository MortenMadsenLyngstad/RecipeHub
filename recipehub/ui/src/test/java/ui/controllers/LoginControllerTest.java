package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import file.UserFilehandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.App;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.PasswordHasher;
import core.Profile;
import core.RecipeLibrary;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;
    private Parent root;

    private Button loginButton;
    private Hyperlink registerLink;

    private Label loginMessageLabel;
    private Hashtable<String, String> userInfo = new Hashtable<>();
    private UserFilehandler mockUserFileHandler = mock(UserFilehandler.class);
    private Profile mockProfile = mock(Profile.class);

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("UserLogin.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        loginMessageLabel = lookup("#loginMessageLabel").query();
        loginButton = lookup("#loginButton").query();
        controller.currentProfile = mockProfile;
        controller.setUserFilehandler(mockUserFileHandler);
        registerLink = findHyperlink(root);
    }

    private Hyperlink findHyperlink(Parent parent) {
        if (parent instanceof Hyperlink) {
            return (Hyperlink) parent;
        }
        
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof Parent) {
                Hyperlink hyperlink = findHyperlink((Parent) child);
                if (hyperlink != null) {
                    return hyperlink;
                }
            }
        }
        
        return null;
    }

    @Test
    public void testValidateLoginWithValidCredentials() throws Exception {
        userInfo.put("testuser", PasswordHasher.hashPassword("Password123"));
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(userInfo);
        when(mockProfile.getUsername()).thenReturn("testuser");
        when(mockProfile.getFavorites()).thenReturn(new RecipeLibrary());
        List<Profile> profiles = new ArrayList<>();
        profiles.add(mockProfile);
        when(mockUserFileHandler.readProfiles()).thenReturn(profiles);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123");

        assertTrue(controller.validateLogin("testuser", "Password123", mockUserFileHandler));
        assertEquals("Enter username and password", loginMessageLabel.getText());

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertEquals("Mainscreen.fxml", controller.getFileName());
        });
    }

    @Test
    public void testValidateLoginWithInvalidUsername() throws Exception {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(userInfo);

        write("invaliduser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.ENTER);

        Platform.runLater(() -> {
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });
        
    }

    @Test
    public void testValidateLoginWithIncorrectPassword() throws Exception {
        userInfo.put("testuser", "Password123");
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(userInfo);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("wrongpassword");

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("testuser", "wrongpassword", mockUserFileHandler));
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });
    }

    @Test
    public void testValidateLoginWithBlankFields() throws Exception {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(userInfo);

        write("").push(javafx.scene.input.KeyCode.TAB);
        write("");

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("", "", mockUserFileHandler));
            assertEquals("Please enter a username and password", loginMessageLabel.getText());  
        });
    }

    @Test
    public void testClickOnRegisterHyperlink() throws IOException {
        clickOn(registerLink);

        Platform.runLater(() -> {
            assertEquals("RegisterScreen.fxml", controller.getFileName());
        });
    }
}
