package bg.sit.ui.controllers;

import bg.sit.business.ValidationUtil;
import bg.sit.business.entities.User;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.UserService;
import bg.sit.ui.MessagesUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
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
import javax.validation.ConstraintViolation;

public class Page_UsersController implements Initializable {

    UserService userService;
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
        ComboBoxRoleType.setItems(FXCollections.observableArrayList("Админ", "МОЛ"));
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
        txtName.clear();
        txtUserName.clear();
        txtPassword.clear();
        ComboBoxDelete.valueProperty().set(null);
        ComboBoxRoleType.valueProperty().set(null);
    }

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        Role_Type.setCellValueFactory(new PropertyValueFactory<User, RoleType>("roleType"));
    }

    private void setTable() {
        initTable();
        userService = new UserService();
        table.getItems().addAll(FXCollections.observableArrayList(userService.getUsers()));
    }

    private void clearForm() {
        table.getItems().clear();
    }

    public void ADD(ActionEvent event) throws IOException {
        if (txtName.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            MessagesUtil.showMessage("Невалидни или празни дании!", Alert.AlertType.ERROR);
            return;
        }
        User userValidation = new User();

        userValidation.setName(txtName.getText());
        userValidation.setUsername(txtUserName.getText());
        userValidation.setPassword(txtPassword.getText());

        Set<ConstraintViolation<User>> validations = ValidationUtil.getValidator().validate(userValidation);

        if (!validations.isEmpty()) {
            ValidationUtil.ShowErrors(validations);
        } else {

            clearForm();
            userService = new UserService();
            User user = table.getSelectionModel().getSelectedItem();
            if (ComboBoxRoleType.getValue() != null) {
                int roleType = ComboBoxRoleType.getSelectionModel().getSelectedIndex();
                if (roleType == 0) {
                    userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.ADMIN);
                } else {
                    userService.addUser(txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.MOL);
                }
            } else {
            }
            setTable();
        }
    }

    public void EDIT(ActionEvent event) throws IOException {
        User userValidation = new User();

        try {
            userValidation.setName(txtName.getText());
            userValidation.setUsername(txtUserName.getText());
            userValidation.setPassword(txtPassword.getText());
        } catch (Exception e) {
            MessagesUtil.showMessage("Невалидни или празни дании!", Alert.AlertType.ERROR);
            return;
        }

        Set<ConstraintViolation<User>> validations = ValidationUtil.getValidator().validate(userValidation);

        if (!validations.isEmpty()) {
            ValidationUtil.ShowErrors(validations);
        } else {
            User user = table.getSelectionModel().getSelectedItem();
            if (user != null) {
                userService = new UserService();
                if (ComboBoxRoleType.getValue() != null) {
                    int roleType = ComboBoxRoleType.getSelectionModel().getSelectedIndex();
                    if (roleType == 0) {
                        userService.updateUser(user.getId(), txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.ADMIN);
                    } else {
                        userService.updateUser(user.getId(), txtName.getText(), txtUserName.getText(), txtPassword.getText(), RoleType.MOL);
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Опс...");
                    alert.setHeaderText("Моля, посочете тип потребител.");
                    alert.showAndWait();
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
    }

    public void DELETE(ActionEvent event) throws IOException {

        User user = table.getSelectionModel().getSelectedItem();
        if (user != null) {
            userService = new UserService();
            if (ComboBoxDelete.getSelectionModel().getSelectedIndex() == 2) {

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
