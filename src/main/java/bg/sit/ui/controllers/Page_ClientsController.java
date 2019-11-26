package bg.sit.ui.controllers;

import bg.sit.business.entities.Customer;
import bg.sit.business.services.CustomerService;
import bg.sit.session.SessionHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Page_ClientsController implements Initializable {

    CustomerService customersService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Customer> table;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> locationColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox<String> ComboBoxDel;
    @FXML
    private Button btnADD1;
    @FXML
    private Button btnADD;
    @FXML
    private Button btnDELETE1;
    @FXML
    private Button btnDELETE;
    @FXML
    private Button btnEDIT1;
    @FXML
    private Button btnEDIT;
    @FXML
    private Button btnBackADD;
    @FXML
    private Button btnBackEDIT;
    @FXML
    private Button btnBackDEL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionHelper.getCurrentUser();
        setTable();
        setVisability();
        ComboBoxDel.setItems(FXCollections.observableArrayList("Деактивиране", "Принудително изтриване"));
    }

    private void setVisability() {
        txtName.setVisible(false);
        txtLocation.setVisible(false);
        txtPhone.setVisible(false);
        btnADD1.setVisible(false);
        btnADD.setVisible(true);
        btnDELETE1.setVisible(false);
        btnDELETE.setVisible(true);
        btnEDIT1.setVisible(false);
        btnEDIT.setVisible(true);
        btnBackADD.setVisible(false);
        btnBackEDIT.setVisible(false);
        btnBackDEL.setVisible(false);
        ComboBoxDel.setVisible(false);
    }

    public void btnADD(ActionEvent event) throws IOException {
        btnADD.setVisible(false);
        btnEDIT.setVisible(false);
        btnDELETE.setVisible(false);
        txtName.setVisible(true);
        txtLocation.setVisible(true);
        txtPhone.setVisible(true);
        btnBackADD.setVisible(true);
        btnADD1.setVisible(true);
    }

    public void btnEDIT(ActionEvent event) throws IOException {
        btnADD.setVisible(false);
        btnEDIT.setVisible(false);
        btnDELETE.setVisible(false);
        txtName.setVisible(true);
        txtLocation.setVisible(true);
        txtPhone.setVisible(true);
        btnBackEDIT.setVisible(true);
        btnEDIT1.setVisible(true);
    }

    public void btnDELETE(ActionEvent event) throws IOException {
        btnADD.setVisible(false);
        btnEDIT.setVisible(false);
        btnDELETE.setVisible(false);
        ComboBoxDel.setVisible(true);
        btnBackDEL.setVisible(true);
        btnDELETE1.setVisible(true);
    }

    public void btnBack(ActionEvent event) throws IOException {
        setVisability();
    }

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("location"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
    }

    private void setTable() {
        initTable();
        customersService = new CustomerService();
        table.getItems().addAll(FXCollections.observableArrayList(customersService.getCustomers()));
    }

    private void clearForm() {
        table.getItems().clear();
    }

    public void ADD(ActionEvent event) throws IOException {
        clearForm();
        customersService = new CustomerService();
        customersService.addCustomer(txtName.getText(), txtLocation.getText(), txtPhone.getText());
        setTable();
    }

    public void EDIT(ActionEvent event) throws IOException {
        Customer customer = table.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customersService = new CustomerService();
            customersService.updateCustomer(customer.getId(), txtName.getText(), txtLocation.getText(), txtPhone.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете потребителя който искате да промените.");
            alert.showAndWait();
        }
        clearForm();
        setTable();
    }

    public void DELETE(ActionEvent event) throws IOException {

        Customer customer = table.getSelectionModel().getSelectedItem();
        if (customer != null) {
            customersService = new CustomerService();
            if (ComboBoxDel.getSelectionModel().getSelectedIndex() == 2) {
                customersService.forceDeleteCustomer(customer.getId());
            } else {
                customersService.deleteCustomer(customer.getId());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
