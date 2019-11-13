package bg.sit.ui.controllers;

import bg.sit.business.enums.RoleType;
import bg.sit.business.services.UserService;
import bg.sit.session.SessionHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainPageController implements Initializable {

    UserService userService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane rootPaneAll;
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
    @FXML
    private Button btnUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new UserService();
        SessionHelper.getCurrentUser();
        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            btnUser.setVisible(true);
        } else {
            btnUser.setVisible(false);
        }
    }

    public void Page_Products(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Products.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void Page_Clients(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Clients.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void Page_Cards(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Cards.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void Page_Reference(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Reference.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void Page_Users(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Page_Users.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
