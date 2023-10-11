package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.SuperController;

public class App extends Application {

    /**
     * Starts the application
     * 
     * @param stage
     * @throws IOException if the FXMLLoader.load method throws an exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SuperController.class.getResource("UserLogin.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
