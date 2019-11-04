package bg.sit.ui.controllers;

import bg.sit.business.entities.User;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.UsersService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Page_UsersController implements Initializable {

    UsersService userService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, RoleType> Role_Type;
    @FXML
    private TableColumn<User, Boolean> isDeletedColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new UsersService();
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        Role_Type.setCellValueFactory(new PropertyValueFactory<User, RoleType>("roleType"));
        table.getItems().addAll(FXCollections.observableArrayList(userService.getUsers()));
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
