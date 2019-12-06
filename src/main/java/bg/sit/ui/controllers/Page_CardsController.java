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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private TableView<Product> productTableAvailable;
    @FXML
    private TableColumn<Product, String> nameColumnAvailable;
    @FXML
    private TableColumn<Product, Double> priceColumnAvailable;
    @FXML
    private TableView<Product> productTableClient;
    @FXML
    private TableColumn<Product, String> nameColumnClient;
    @FXML
    private TableColumn<Product, Double> priceColumnClient;
    @FXML
    private Button btnActon;
    @FXML
    private Button btnAddToCard;
    @FXML
    private Button btnRemoveFromCard;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnRefresh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerService = new CustomerService();
        productService = new ProductService();
        initClientComboBox();
        setVisability();
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

    private void clearTableAvailable() {
        productTableAvailable.getItems().clear();
    }

    private void initProductTableAvailable() {
        nameColumnAvailable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumnAvailable.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    private void setAvailableProducts() {
        initProductTableAvailable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
        productTableAvailable.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(customerID, true)));
    }

    public void availableProducts(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableAvailable();
        if (CustomerComboBox.getValue() != null) {
            setAvailableProducts();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете клиент ...");
            alert.showAndWait();
        }
    }

    private void clearTableClient() {
        productTableClient.getItems().clear();
    }

    private void initProductTableClient() {
        nameColumnClient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        priceColumnClient.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    private void setClientProducts() {
        initProductTableClient();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
        productTableClient.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(customerID, false)));
    }

    public void Refresh(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableClient();
        setClientProducts();
        clearTableAvailable();
        setAvailableProducts();
    }

    public void addProductToClientCard(ActionEvent event) throws IOException {
        productService = new ProductService();
        customerService = new CustomerService();
        Product product = productTableAvailable.getSelectionModel().getSelectedItem();
        if (product != null) {
            int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
            customerService.addCustomerCard(customerID, product.getId());
            clearTableAvailable();
            setAvailableProducts();
            clearTableClient();
            setClientProducts();
        } else {
            clearTableAvailable();
            setAvailableProducts();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете продукт ...");
            alert.showAndWait();
        }
    }

    public void removeProductFromClientCard(ActionEvent event) throws IOException {
        productService = new ProductService();
        customerService = new CustomerService();
        Product product = productTableClient.getSelectionModel().getSelectedItem();
        if (product != null) {
            int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
            customerService.removeCustomerCard(customerID, product.getId());
            clearTableAvailable();
            setAvailableProducts();
            clearTableClient();
            setClientProducts();
        } else {
            clearTableClient();
            setClientProducts();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете продукт ...");
            alert.showAndWait();
        }
    }

    public void Action(ActionEvent event) throws IOException {

        if (CustomerComboBox.getValue() != null) {
            clearTableClient();
            setClientProducts();
            setAvailableProducts();
            btnAddToCard.setVisible(true);
            btnRemoveFromCard.setVisible(true);
            productTableAvailable.setVisible(true);
            productTableClient.setVisible(true);
            btnBack.setVisible(true);
            btnActon.setVisible(false);
            btnRefresh.setVisible(true);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете клиент...");
            alert.showAndWait();
        }
    }

    public void setVisability() {
        btnActon.setVisible(true);
        btnAddToCard.setVisible(false);
        btnRemoveFromCard.setVisible(false);
        productTableAvailable.setVisible(false);
        productTableClient.setVisible(false);
        btnBack.setVisible(false);
        btnRefresh.setVisible(false);
    }

    public void Back(ActionEvent event) throws IOException {
        setVisability();
        clearTableAvailable();
        clearTableClient();
        CustomerComboBox.valueProperty().set(null);
    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
