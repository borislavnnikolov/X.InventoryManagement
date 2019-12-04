package bg.sit.ui.controllers;

import bg.sit.business.entities.Customer;
import bg.sit.business.entities.Product;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.CustomerService;
import bg.sit.business.services.ProductService;
import bg.sit.session.SessionHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Page_ReferenceController implements Initializable {

    CustomerService customerService;
    ProductService productService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ListView<String> productList;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerService = new CustomerService();
        productService = new ProductService();
        initClientComboBox();
        setTableAvaiable();
    }

    private void initClientComboBox() {

        CustomerComboBox.setConverter(new StringConverter<Customer>() {

            public String toString(Customer object) {
                return object.getName();
            }

            @Override
            public Customer fromString(String string) {
                for (Customer pt : CustomerComboBox.getItems()) {
                    if (pt.getName().equals(string)) {
                        return pt;
                    }
                }

                return null;
            }
        });

        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            CustomerComboBox.setItems(FXCollections.observableArrayList(customerService.getCustomers()));
        } else {
            CustomerComboBox.setItems(FXCollections.observableArrayList(customerService.getCustomers(SessionHelper.getCurrentUser().getId())));
        }
    }

    /*private void initProductList(){
            productList.setConverter(new StringConverter<Customer>() {

            public String toString(Customer object) {
                return object.getName();
            }

            @Override
            public Customer fromString(String string) {
                for (Customer pt : productList.getItems()) {
                    if (pt.getName().equals(string)) {
                        return pt;
                    }
                }

                return null;
            }
        });
    
    }*/
    private void initProductTable() {
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    private void setTableClient() {
        initProductTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();

        productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(0, false)));//0 id clienta selected model//true-avaiabale/false-samo na klienta

    }

    private void setTableAvaiable() {
        initProductTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();

        productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(0, true)));//0 id clienta selected model//true-avaiabale/false-samo na klienta

    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
