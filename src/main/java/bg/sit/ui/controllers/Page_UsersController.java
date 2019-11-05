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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private ComboBox<String> ComboBoxRoleType;
    @FXML
    private ComboBox<String> ComboBoxDelete;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTable();
        ComboBoxRoleType.setItems(FXCollections.observableArrayList("ADMIN", "MOL", "NONE"));
        ComboBoxDelete.setItems(FXCollections.observableArrayList("Soft_Delete", "Force_Delete"));

    }

    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        Role_Type.setCellValueFactory(new PropertyValueFactory<User, RoleType>("roleType"));
    }

    private void setTable() {
        initTable();
        userService = new UsersService();
        table.getItems().addAll(FXCollections.observableArrayList(userService.getUsers()));
    }

    private void clearForm() {
        table.getItems().clear();
    }

    public void ADD(ActionEvent event) throws IOException {
        clearForm();
        userService = new UsersService();
        if (ComboBoxRoleType.getValue() == RoleType.ADMIN.toString()) {
            userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.ADMIN);
        } else {
            userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.MOL);
        }

        setTable();
    }

    public void DELETE(ActionEvent event) throws IOException {
        clearForm();
        userService = new UsersService();
        if (ComboBoxDelete.getValue() == "Force_Delete") {
            userService.forceDeleteUser(Integer.parseInt(txtID.getText()));
        } else {
            userService.deleteUser(Integer.parseInt(txtID.getText()));
        }

        setTable();
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
