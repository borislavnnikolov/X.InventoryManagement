/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.ui.controllers;

import bg.sit.business.services.UsersService;
import bg.sit.session.SessionHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author perel
 */
public class LoginPageController implements Initializable {

    UsersService usersService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtUsernameField;
    @FXML
    private TextField txtPasswordField;
    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void btnLogin(ActionEvent event) throws Exception {
        usersService = new UsersService();
        if (usersService.login(txtUsernameField.getText(), txtPasswordField.getText())) {
            SessionHelper.setCurrentUser(usersService.getUserByUsername(txtUsernameField.getText(), true));
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            rootPane.getChildren().setAll(pane);

        } else {
            lblStatus.setText("Login Failed");
        }
    }

}
