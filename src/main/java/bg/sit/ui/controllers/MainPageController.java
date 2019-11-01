package bg.sit.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class MainPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label UserName;
    @FXML
    private Label N_Products;
    @FXML
    private Label N_Clients;
    @FXML
    private Label N_Cards;
    @FXML
    private Label N_Users;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
     public void Page_Products(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/Page_Products.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void Page_Clients(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/Page_Clients.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void Page_Cards(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/Page_Cards.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void Page_Reference(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/Page_Reference.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void Page_Users(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/Page_Users.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void LogOut(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    public void Exit(ActionEvent event) throws IOException {
        System.exit(0);
    }
}
