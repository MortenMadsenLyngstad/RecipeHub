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
    private Label registerMessageLabel;
    private Hyperlink loginLink;
    private UserFilehandler mockUserFileHandler = mock(UserFilehandler.class);
    private Profile mockProfile = mock(Profile.class);

    /**
     * This method will set up the application for headless mode (tests will run
     * without GUI).
     *
     * @see App#supportHeadless()
     */
    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    /**
     * This method will start the application.
     *
     * @param stage the stage to start
     * @throws IOException if the FXMLLoader.load() method throws an exception
     */
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
        registerButton = lookup("#registerButton").query();
        RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(new RecipeLibrary());
        controller.setCurrentRecipeHubAccess(
                new DirectRecipeHubAccess(mockUserFileHandler, mockRecipeFilehandler));
        controller.setProfile(mockProfile);
    }

    /**
     * This method tests the register button with valid data.
     *
     * @see RegisterController#validateRegister(String, String)
     */
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

    /**
     * This method tests the register button with invalid password.
     *
     * @see RegisterController#validateRegister(String, String)
     */
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

    /**
     * This method tests the register button with invalid username.
     *
     * @see RegisterController#validateRegister(String, String)
     */
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

    /**
     * This method tests the register button with blank fields.
     *
     * @see RegisterController#validateRegister(String, String)
     */
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

    /**
     * This method tests the register button with passwords not matching.
     *
     * @see RegisterController#validateRegister(String, String)
     */
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

    /**
     * This method tests the click on login hyperlink.
     *
     * @see RegisterController#switchToLoginScreen()
     */
    @Test
    public void testClickOnLoginHyperlink() {
        loginLink = lookup("#loginLink").query();
        clickOn(loginLink);

        assertEquals("UserLogin.fxml", controller.getFileName());
    }
}
