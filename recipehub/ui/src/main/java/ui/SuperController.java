package ui;

import core.Profile;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This controller class is a superclass which the other controllers extends.
 * It contains methods needed by all the controllers
 */
public class SuperController {

    protected Scene scene;
    protected Stage stage;
    protected Parent root;
    static Profile currentProfile;
    private String fileName;
    // exchange with remote or direct access
    static RecipeHubModelAccess recipeHubModelAccess;
    static RecipeFilehandler recipeFilehandler;
    static UserFilehandler userFilehandler;

    protected void switchSceneMain(ActionEvent event, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(SuperController.class.getResource(file));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        MainscreenController controller = loader.getController();
        controller.loadLibrary();
        setFileName(file);
    }

    protected void switchSceneWithInfo(ActionEvent event, String file)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(SuperController.class.getResource(file));
        root = loader.load();
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

    public void setRecipeHubModelAccess(RecipeHubModelAccess recipeHubModelAccess) {
        SuperController.recipeHubModelAccess = recipeHubModelAccess;
    }
}
