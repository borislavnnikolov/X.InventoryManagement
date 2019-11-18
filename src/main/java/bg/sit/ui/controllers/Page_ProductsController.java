package bg.sit.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class Page_ProductsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane SecondRootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Products_Products.fxml"));
            SecondRootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(Page_ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void Product(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Products_Products.fxml"));
        SecondRootPane.getChildren().setAll(pane);
    }

    public void ProductType(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Products_ProductType.fxml"));
        SecondRootPane.getChildren().setAll(pane);
    }

}
