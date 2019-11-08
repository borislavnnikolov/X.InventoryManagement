package bg.sit.ui.controllers;

import bg.sit.business.services.UsersService;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnEXIT;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.setDefaultButton(true);
    }

    public void btnLogin(ActionEvent event) throws IOException {
        usersService = new UsersService();
        if (usersService.login(txtUsernameField.getText(), txtPasswordField.getText())) {
            SessionHelper.setCurrentUser(usersService.getUserByUsername(txtUsernameField.getText(), true));
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage1.fxml"));
            rootPane.getChildren().setAll(pane);

        } else {
            lblStatus.setText("Login Failed");
        }
    }

    public void EXIT(ActionEvent event) throws IOException {
        System.exit(0);
    }

}
