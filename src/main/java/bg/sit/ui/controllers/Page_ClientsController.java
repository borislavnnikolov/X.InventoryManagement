package bg.sit.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class Page_ClientsController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane =FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
	rootPane.getChildren().setAll(pane);
    }
    
}
