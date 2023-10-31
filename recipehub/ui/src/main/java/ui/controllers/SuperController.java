package ui.controllers;

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
    static RecipeFilehandler recipeFilehandler;
    static UserFilehandler userFilehandler;

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

    /**
     * This method will set make new filehandlers with new filenames.
     * The method is implemented so that we can run proper tests on the controller
     * 
     * @param recipeFilehandler - RecipeFilhandler for the MainscreenController to
     *                          use
     * @param userFilehandler   - UserFilhandler for the MainscreenController to use
     */
    public void setFilehandlers(RecipeFilehandler recipeFilehandler,
            UserFilehandler userFilehandler) {
        SuperController.recipeFilehandler = recipeFilehandler;
        SuperController.userFilehandler = userFilehandler;
    }

    /**
     * Changes the userFilehandler.
     * Used for unit tests.
     * 
     * @param userFilehandler - the new userFilehandler
     */
    public void setUserFilehandler(UserFilehandler userFilehandler) {
        SuperController.userFilehandler = userFilehandler;
    }

}
