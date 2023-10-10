package ui.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Hashtable;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.Profile;
import file.UserFilehandler;

public class RegisterControllerTest extends ApplicationTest {

    private RegisterController controller;
    private Parent root;

    private Button registerButton;
    private Hyperlink loginLink;

    private Label registerMessageLabel;
    private Hashtable<String, String> userInfo = new Hashtable<>();
    private UserFilehandler mockUserFileHandler = mock(UserFilehandler.class);
    private Profile mockProfile = mock(Profile.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RegisterScreen.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        registerMessageLabel = lookup("#registerMessageLabel").query();
        loginLink = findHyperlink(root);
        registerButton = lookup("#registerButton").query();
        controller.setUserFilehandler(mockUserFileHandler);
        controller.currentProfile = mockProfile;
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
    public void testValidateRegisterWithValidData() {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(this.userInfo);
        doNothing().when(mockUserFileHandler).writeProfile(any(Profile.class));


        write("newuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password123");

        clickOn(registerButton);

        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    @Test
    public void testValidateRegisterWithInvalidPassword() {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(this.userInfo);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("weak").push(javafx.scene.input.KeyCode.TAB);
        write("weak");

        clickOn(registerButton);

        Assertions.assertFalse(controller.validateRegister("testuser", "weak",  mockUserFileHandler));
        Assertions.assertEquals("Password is too short", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithUsernameExists() {
        userInfo.put("existinguser", "Password123");
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(this.userInfo);

        write("existinguser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.ENTER);

        assertFalse(controller.validateRegister("existinguser", "Password123", mockUserFileHandler));
        assertEquals("Username already exists", registerMessageLabel.getText());
    }

    @Test
    public void testValidateLoginWithBlankFields() {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(this.userInfo);

        write("").push(javafx.scene.input.KeyCode.TAB);
        write("").push(javafx.scene.input.KeyCode.TAB);
        write("");

        clickOn(registerButton);

        assertFalse(controller.validateRegister("", "", mockUserFileHandler));
        assertEquals("Please enter a username and password", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithPasswordsNotMatching() {
        when(mockUserFileHandler.readUsernamesAndPasswords()).thenReturn(this.userInfo);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password1234");

        clickOn(registerButton);

        assertFalse(controller.validateRegister("testuser", "Password123", mockUserFileHandler));
        assertEquals("Passwords do not match", registerMessageLabel.getText());
    }

    @Test
    public void testClickOnLoginHyperlink() {
        clickOn(loginLink);
        assertEquals("UserLogin.fxml", controller.getFileName());
    }
}
