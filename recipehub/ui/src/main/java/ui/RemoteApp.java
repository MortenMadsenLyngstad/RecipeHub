package ui;


import file.RemoteRecipeHubAccess;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class starts the application with remote access.
 */
public class RemoteApp extends Application {
    /**
     * Helper method used by tests needing to run headless.
     * Taken from todo-list-example
     */
    public static void supportHeadless() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    /**
     * Starts the application.
     * 
     * @param stage - Stage object
     * @throws IOException if the FXMLLoader.load method throws an exception
     * @throws URISyntaxException if the URI is invalid
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SuperController.class.getResource("UserLogin.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        SuperController controller = fxmlLoader.getController();
        try {
            controller.setCurrentRecipeHubAccess(new RemoteRecipeHubAccess(new URI("http://localhost:8080/recipehub/")));
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        stage.show();
    }

    /**
     * Launches the application.
     * 
     * @param args - arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
