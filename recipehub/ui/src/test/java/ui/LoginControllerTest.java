package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.Profile;
import core.RecipeLibrary;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import java.util.Hashtable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * This is a test class for LoginController.
 */
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

    /**
     * This method sets up the test environment.
     */
    @BeforeEach
    public void setUp() {
        loginMessageLabel = lookup("#loginMessageLabel").query();
        loginButton = lookup("#loginButton").query();
        controller.setProfile(mockProfile);
        RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(new RecipeLibrary());
        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFileHandler, mockRecipeFilehandler));
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
        Profile profile = new Profile("Testuser", "Password123");
        when(mockUserFileHandler.loadProfile(any())).thenReturn(profile);

        write("Testuser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123");

        assertEquals("Enter username and password", loginMessageLabel.getText());
        assertTrue(controller.validateLogin(profile.getUsername(), profile.getPassword()));

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertEquals("Mainscreen.fxml", controller.getFileName());
        });
    }

    @Test
    public void testValidateLoginWithInvalidUsername() throws Exception {
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile(p -> p.getUsername().equals("testuser")))
                .thenReturn(profile);

        write("invaliduser").push(javafx.scene.input.KeyCode.TAB);
        write("Password123").push(javafx.scene.input.KeyCode.ENTER);

        Platform.runLater(() -> {
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });

    }

    @Test
    public void testValidateLoginWithIncorrectPassword() throws Exception {
        userInfo.put("testuser", "Password123");
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile(p -> p.getUsername().equals("testuser")))
                .thenReturn(profile);

        write("testuser").push(javafx.scene.input.KeyCode.TAB);
        write("wrongpassword");

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("testuser", "wrongpassword"));
            assertEquals("Incorrect username or password", loginMessageLabel.getText());
        });
    }

    @Test
    public void testValidateLoginWithBlankFields() throws Exception {
        Profile profile = new Profile("testuser", "Password123");
        when(mockUserFileHandler.loadProfile(p -> p.getUsername().equals("testuser")))
                .thenReturn(profile);

        write("").push(javafx.scene.input.KeyCode.TAB);
        write("");

        clickOn(loginButton);

        Platform.runLater(() -> {
            assertFalse(controller.validateLogin("", ""));
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
