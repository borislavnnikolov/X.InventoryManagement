package bg.sit.ui.controllers;

import bg.sit.session.SessionHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPage1Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane rootPaneAll;
    @FXML
    private Label UserName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionHelper.getCurrentUser();
        UserName.setText("Добре дошъл, " + SessionHelper.getCurrentUser().getName());
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(MainPage1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void LogOut(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        rootPaneAll.getChildren().setAll(pane);
    }

    public void Settings(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }

    public void Exit(ActionEvent event) throws IOException {
        System.exit(0);
    }

}
