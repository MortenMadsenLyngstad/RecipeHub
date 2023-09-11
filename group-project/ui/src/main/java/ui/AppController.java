package ui;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


public class AppController {
  private Scene scene;
  private Stage stage;

    public void switchToLoginScreen(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
    scene = new Scene(root);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public void switchToRegisterScreen(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("RegisterScreen.fxml"));
    scene = new Scene(root);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
