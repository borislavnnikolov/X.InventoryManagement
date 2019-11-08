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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    private TextField txtName;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnAdd1;
    @FXML
    private Button ADD;
    @FXML
    private Button btnDelete1;
    @FXML
    private Button DELETE;
    @FXML
    private Button btnEdit1;
    @FXML
    private Button EDIT;
    @FXML
    private Button btnBackAdd;
    @FXML
    private Button btnBackEdit;
    @FXML
    private Button btnBackDel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTable();
        setVisability();
        ComboBoxRoleType.setItems(FXCollections.observableArrayList("ADMIN", "MOL", "NONE"));
        ComboBoxDelete.setItems(FXCollections.observableArrayList("Деактивиране", "Принудително изтриване"));
    }

    public void btnADD(ActionEvent event) throws IOException {
        ADD.setVisible(false);
        EDIT.setVisible(false);
        DELETE.setVisible(false);
        txtName.setVisible(true);
        txtUserName.setVisible(true);
        txtPassword.setVisible(true);
        ComboBoxRoleType.setVisible(true);
        btnBackAdd.setVisible(true);
        btnAdd1.setVisible(true);
    }

    public void btnEDIT(ActionEvent event) throws IOException {
        ADD.setVisible(false);
        EDIT.setVisible(false);
        DELETE.setVisible(false);
        txtName.setVisible(true);
        txtUserName.setVisible(true);
        txtPassword.setVisible(true);
        ComboBoxRoleType.setVisible(true);
        btnBackEdit.setVisible(true);
        btnEdit1.setVisible(true);
    }

    public void btnDELETE(ActionEvent event) throws IOException {
        ADD.setVisible(false);
        EDIT.setVisible(false);
        DELETE.setVisible(false);
        ComboBoxDelete.setVisible(true);
        btnBackDel.setVisible(true);
        btnDelete1.setVisible(true);
    }

    public void btnBack(ActionEvent event) throws IOException {
        setVisability();
    }

    private void setVisability() {
        txtName.setVisible(false);
        txtUserName.setVisible(false);
        txtPassword.setVisible(false);
        btnAdd1.setVisible(false);
        ADD.setVisible(true);
        btnDelete1.setVisible(false);
        DELETE.setVisible(true);
        btnEdit1.setVisible(false);
        EDIT.setVisible(true);
        btnBackAdd.setVisible(false);
        btnBackEdit.setVisible(false);
        btnBackDel.setVisible(false);
        ComboBoxRoleType.setVisible(false);
        ComboBoxDelete.setVisible(false);
    }

    private void initTable() {
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
        User user = table.getSelectionModel().getSelectedItem();
        if (ComboBoxRoleType.getValue() == RoleType.ADMIN.toString()) {
            userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.ADMIN);
        } else {
            userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.MOL);
        }

        setTable();
    }

    public void EDIT(ActionEvent event) throws IOException {
        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            userService = new UsersService();
            if (ComboBoxRoleType.getValue() == RoleType.ADMIN.toString()) {
                userService.updateUser(user.getId(), txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.ADMIN);
            } else if (ComboBoxRoleType.getValue() == RoleType.MOL.toString()) {
                userService.updateUser(user.getId(), txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.MOL);
            } else {
                userService.updateUser(user.getId(), txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.NONE);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете потребителя който искате да промените.");
            alert.showAndWait();
        }
        clearForm();
        setTable();
    }

    public void DELETE(ActionEvent event) throws IOException {

        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            userService = new UsersService();
            if (ComboBoxDelete.getValue() == "Принудително изтриване") {

                userService.forceDeleteUser(user.getId());
            } else {
                userService.forceDeleteUser(user.getId());
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете потребителя който искате да изтриете.");
            alert.showAndWait();
        }
        clearForm();
        setTable();
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
