package ui;

import file.DirectRecipeHubAccess;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The App class starts the application with direct access.
 */
public class App extends Application {

    /**
     * This helper method is used by tests needing to run in headless mode. 
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
     * This method starts the application.
     *
     * @param stage stage object
     * @throws IOException if the FXMLLoader.load method throws an exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SuperController.class.getResource("UserLogin.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        Image logo = new Image(
                getClass().getResource("images/recipehub_logo_no_text.png").toExternalForm());
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.setTitle("RecipeHub");
        SuperController controller = fxmlLoader.getController();
        controller.setCurrentRecipeHubAccess(new DirectRecipeHubAccess());
        stage.show();
    }

    /**
     * This method launches the application.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
