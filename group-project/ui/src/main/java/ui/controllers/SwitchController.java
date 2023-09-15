package ui.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.App;
import javafx.scene.Node;

public class SwitchController {
  private Scene scene;
  private Stage stage;

  public void switchSceneMain(ActionEvent event, String file) throws IOException {
    Parent root = FXMLLoader.load(App.class.getResource(file));
    scene = new Scene(root);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
