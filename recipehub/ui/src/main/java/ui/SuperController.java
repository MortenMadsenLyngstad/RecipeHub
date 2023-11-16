package ui;

import core.Profile;
import file.RecipeHubAccess;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SuperController class is a superclass which the other controllers
 * extends.
 * It contains methods needed by all the controllers
 */
public class SuperController {
    static RecipeHubAccess currentRecipeHubAccess;
    static Profile currentProfile;

    protected Scene scene;
    protected Stage stage;
    protected Parent root;

    private String fileName;

    /**
     * This method switches to the main screen.
     *
     * @param event the ActionEvent that triggers the screen switch
     * @throws IOException if the switchSceneMain method throws an exception
     */
    protected void switchSceneMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SuperController.class.getResource("Mainscreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(SuperController.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        MainscreenController controller = loader.getController();
        controller.loadLibrary();
        setFileName("Mainscreen.fxml");
    }

    /**
     * This method switches to the screen with the given file name.
     *
     * @param event the ActionEvent that triggers the screen switch
     * @param file  the file name of the screen to switch to
     * @throws IOException if the switchSceneWithInfo method throws an exception
     */
    protected void switchSceneWithInfo(ActionEvent event, String file) throws IOException {
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

    /**
     * This method is used to set the current profile.
     *
     * @param profile the profile to set
     */
    protected void setProfile(Profile profile) {
        currentProfile = profile;
    }

    /**
     * This method is used to get the file name of the current screen.
     *
     * @return the file name of the current screen
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * This method is used to set the file name of the current screen.
     *
     * @param fileName the file name to set
     */
    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method is used to set the access used by the app.
     * Either direct or remote.
     *
     * @param recipeHubAccess the type of access to set
     */
    public void setCurrentRecipeHubAccess(RecipeHubAccess recipeHubAccess) {
        currentRecipeHubAccess = recipeHubAccess;
    }
}
