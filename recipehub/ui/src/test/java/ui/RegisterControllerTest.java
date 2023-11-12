package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.Profile;
import core.RecipeLibrary;
import file.DirectRecipeHubAccess;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * This is a test class for RegisterController.
 */
public class RegisterControllerTest extends ApplicationTest {

    private RegisterController controller;
    private Parent root;

    private Button registerButton;
    private Hyperlink loginLink;

    private Label registerMessageLabel;
    private UserFilehandler mockUserFileHandler = mock(UserFilehandler.class);
    private Profile mockProfile = mock(Profile.class);

    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RegisterScreen.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        registerMessageLabel = lookup("#registerMessageLabel").query();
        loginLink = findHyperlink(root);
        registerButton = lookup("#registerButton").query();
        RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(new RecipeLibrary());
        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFileHandler, mockRecipeFilehandler));
        controller.setProfile(mockProfile);
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
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile("testuser")).thenReturn(profile);
        when(mockUserFileHandler.writeProfile(any(Profile.class))).thenReturn(true);

        write("newuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password123");

        clickOn(registerButton);

        assertEquals("Mainscreen.fxml", controller.getFileName());
    }

    @Test
    public void testValidateRegisterWithInvalidPassword() {
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile("testuser")).thenReturn(profile);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("weak").push(javafx.scene.input.KeyCode.TAB);
        write("weak");

        clickOn(registerButton);

        Assertions.assertFalse(controller.validateRegister("testuser", "weak"));
        Assertions.assertEquals("Password is too short", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithUsernameExists() {
        Profile profile = new Profile("Existinguser", "Password123");
        when(mockUserFileHandler.loadProfile(any())).thenReturn(profile);

        write("Existinguser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.ENTER);

        assertFalse(controller.validateRegister(profile.getUsername(), profile.getPassword()));
        assertEquals("Username already exists", registerMessageLabel.getText());
    }

    @Test
    public void testValidateLoginWithBlankFields() {
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile("testuser")).thenReturn(profile);

        write("").push(javafx.scene.input.KeyCode.TAB);
        write("").push(javafx.scene.input.KeyCode.TAB);
        write("");

        clickOn(registerButton);

        assertFalse(controller.validateRegister("", ""));
        assertEquals("Please enter a username and password", registerMessageLabel.getText());
    }

    @Test
    public void testValidateRegisterWithPasswordsNotMatching() {
        when(mockUserFileHandler.loadProfile("testuser")).thenReturn(null);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.TAB);
        write("Password1234");

        clickOn(registerButton);

        assertFalse(controller.validateRegister("testuser", "Password123"));
        assertEquals("Passwords do not match", registerMessageLabel.getText());
    }

    @Test
    public void testClickOnLoginHyperlink() {
        clickOn(loginLink);

        assertEquals("UserLogin.fxml", controller.getFileName());
    }
}
