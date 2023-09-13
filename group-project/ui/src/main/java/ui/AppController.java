package ui;

import java.io.IOException;
import java.util.Hashtable;

import core.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;


public class AppController {
  private Scene scene;
  private Stage stage;

  private Label loginMessageLabel;

  private TextField usernameField;
  private PasswordField passwordField;

  public Hashtable<String, String> userinfo = new Hashtable();


  public Profile currentProfile(String uname, String pword) {
    try {
      Profile currentProfile = new Profile(usernameField.getText(), passwordField.getText());
    } catch (IllegalArgumentException e) {
    loginMessageLabel.setText(e.getMessage());
    }  
   return currentProfile(uname, pword);
  }

  



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
  
  private void switchToMainScreen(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("Mainscreen.fxml"));
    scene = new Scene(root);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public void login(ActionEvent event) throws Exception {
    if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
      if (validateLogin(usernameField.getText(), passwordField.getText())) {
        switchToMainScreen(event);
      }
    }
    else {
      loginMessageLabel.setText("Please enter a username and password");
    }
  }

  public void register(ActionEvent event) throws Exception {
    if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
      validateRegister(usernameField.getText(), passwordField.getText());
      switchToMainScreen(event);
    }
    else {
      loginMessageLabel.setText("Please enter a username and password");
    }
  }

  public boolean validateLogin(String uname, String pword) throws Exception {
    if (userinfo.get(uname) == null) {
      loginMessageLabel.setText("Username does not exist");
      return false;
    }
    else if (userinfo.get(uname).equals(pword)) {
      return true;
    }
    else {
      loginMessageLabel.setText("Incorrect password");
      return false;
    }
  }

  public void validateRegister(String uname, String pword) throws Exception {
    if (userinfo.get(uname) != null) {
      loginMessageLabel.setText("Username already exists");
    }
    else {
      currentProfile(uname, pword);
      userinfo.put(uname, pword);
    }
  }
}
