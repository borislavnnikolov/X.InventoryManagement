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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Page_CardsController implements Initializable {

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
    @FXML
    private TableView<Product> productTableA;
    @FXML
    private TableColumn<Product, String> nameColumnA;
    @FXML
    private TableColumn<Product, Double> priceColumnA;
    @FXML
    private TableView<Product> productTableB;
    @FXML
    private TableColumn<Product, String> nameColumnB;
    @FXML
    private TableColumn<Product, Double> priceColumnB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerService = new CustomerService();
        productService = new ProductService();
        initClientComboBox();
        setTable();
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

    private void initProductTable() {//vsichki produkti
        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    private void setTable() {//table with all products
        initProductTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProducts()));
        } else {
            productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProducts(SessionHelper.getCurrentUser().getId())));
        }
    }

    private void clearFormA() {
        productTableA.getItems().clear();
    }

    private void clearFormB() {
        productTableB.getItems().clear();
    }

    private void initProductTableA() {//produktite na klienta
        nameColumnA.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumnA.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    public void setTableClient(ActionEvent event) throws IOException {//products of the client
        clearFormA();
        initProductTableA();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (CustomerComboBox.getValue() != null) {
            int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
            productTableA.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(customerID, false)));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете клиент ...");
            alert.showAndWait();
        }
    }

    private void initProductTableB() {//produktite koito moje da vzeme
        nameColumnB.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumnB.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    public void availableProducts(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearFormB();
        initProductTableB();
        SessionHelper.getCurrentUser();
        productService = new ProductService();

        if (CustomerComboBox.getValue() != null) {
            int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
            productTableB.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(customerID, true)));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете клиент ...");
            alert.showAndWait();
        }
    }

    public void addToClient(ActionEvent event) throws IOException {//add products to the customer card
        initProductTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        customerService = new CustomerService();
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            if (CustomerComboBox.getValue() != null) {
                int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
                customerService.addCustomerCard(customerID, product.getId());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Опс...");
                alert.setHeaderText("Моля, посочете клиент ...");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете продукт...");
            alert.showAndWait();
        }
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
