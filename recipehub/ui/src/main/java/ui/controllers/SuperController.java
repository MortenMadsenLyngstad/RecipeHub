package ui.controllers;

import core.Profile;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Path;

/**
 * This controller class is a superclass which the other controllers extends.
 * It contains methods needed by all the controllers
 */
public class SuperController {

    protected Scene scene;
    protected Stage stage;
    protected Parent root;
    protected Profile currentProfile;
    protected Path userPath = Path.of("user.home" + System.getProperty("file.separator") + "userinfo.json");
    protected Path recipePath = Path.of("user.home" + System.getProperty("file.separator") + "recipes.json");
    private String fileName;

    protected void switchSceneMain(ActionEvent event, String file) throws IOException {
        Parent root = FXMLLoader.load(SuperController.class.getResource(file));
        scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        setFileName(file);
    }

    protected void switchSceneWithInfo(ActionEvent event, String file, Profile profile)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(SuperController.class.getResource(file));
        root = loader.load();

        SuperController controller = loader.getController();
        controller.setProfile(profile);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        setFileName(file);
    }

    protected void setProfile(Profile profile) {
        currentProfile = profile;
    }

    public String getFileName() {
        return this.fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
