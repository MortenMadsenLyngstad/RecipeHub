package ui.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import core.Profile;

public abstract class AbstractController {

    protected Scene scene;
    protected Stage stage;
    protected Parent root;
    protected Profile currentProfile;

    protected void switchSceneMain(ActionEvent event, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    protected void switchSceneWithInfo(ActionEvent event, String file, Profile profile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        root = loader.load();

        AbstractController controller = loader.getController();
        controller.currentProfile(profile);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    protected abstract void currentProfile(Profile profile);
}
