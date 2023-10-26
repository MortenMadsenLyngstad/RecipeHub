package ui;

import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.SuperController;

/**
 * This class starts the application.
 */
public class App extends Application {

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
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SuperController.class.getResource("UserLogin.fxml"));
        SuperController controller = fxmlLoader.getController();
        controller.setFilehandlers(new RecipeFilehandler("recipes.json"),
                new UserFilehandler("userInfo.json"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
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
