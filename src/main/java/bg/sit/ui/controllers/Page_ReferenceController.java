package bg.sit.ui.controllers;

import bg.sit.business.entities.Customer;
import bg.sit.business.entities.Product;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.AmortizationService;
import bg.sit.business.services.CustomerService;
import bg.sit.business.services.ProductService;
import bg.sit.business.services.ProductTypeService;
import bg.sit.business.services.ReportService;
import bg.sit.session.SessionHelper;
import java.awt.Color;
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

public class Page_ReferenceController implements Initializable {

    CustomerService customerService;
    ProductService productService;
    ProductTypeService productTypeService;
    AmortizationService amortizationService;
    ReportService reportService;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> numColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> colorColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ComboBox wasteComboBox;
    @FXML
    private ComboBox availabilityComboBox;
    @FXML
    private ComboBox degreeComboBox;
    @FXML
    private TableView<Product> productTableClient;
    @FXML
    private TableColumn<Product, String> numColumnClient;
    @FXML
    private TableColumn<Product, String> nameColumnClient;
    @FXML
    private TableColumn<Product, String> colorColumnClient;
    @FXML
    private TableColumn<Product, Double> priceColumnClient;
    @FXML
    private TableView<Product> productTableBrak;
    @FXML
    private TableColumn<Product, String> numColumnBrak;
    @FXML
    private TableColumn<Product, String> nameColumnBrak;
    @FXML
    private TableColumn<Product, String> colorColumnBrak;
    @FXML
    private TableColumn<Product, Double> priceColumnBrak;
    @FXML
    private TableColumn<Product, String> brakColumn;
    @FXML
    private TableView<Product> productTableAvailable;
    @FXML
    private TableColumn<Product, String> numColumnAvailable;
    @FXML
    private TableColumn<Product, String> nameColumnAvailable;
    @FXML
    private TableColumn<Product, String> colorColumnAvailable;
    @FXML
    private TableColumn<Product, Double> priceColumnAvailable;
    @FXML
    private TableColumn<Product, String> avaibleColumn;
    @FXML
    private TableView<Product> productTableDegree;
    @FXML
    private TableColumn<Product, String> numColumnDegree;
    @FXML
    private TableColumn<Product, String> nameColumnDegree;
    @FXML
    private TableColumn<Product, String> colorColumnDegree;
    @FXML
    private TableColumn<Product, Double> priceColumnDegree;
    @FXML
    private TableColumn<Product, String> degreeColumn;
    @FXML
    private Button btnRefProducts;
    @FXML
    private Button btnRefBrak;
    @FXML
    private Button btnRefAvailable;
    @FXML
    private Button btnRefDegree;
    @FXML
    private Button btnRefCard;
    @FXML
    private Button btnRefreshProducts;
    @FXML
    private Button btnRefreshBrak;
    @FXML
    private Button btnRefreshAvailable;
    @FXML
    private Button btnRefreshDegree;
    @FXML
    private Button btnRefreshCard;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnShowProducts;
    @FXML
    private Button btnShowBrak;
    @FXML
    private Button btnShowAvailable;
    @FXML
    private Button btnShowType;
    @FXML
    private Button btnShowCards;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerService = new CustomerService();
        initClientComboBox();
        wasteComboBox.setItems(FXCollections.observableArrayList("Бракуван", "Не бракуван"));
        availabilityComboBox.setItems(FXCollections.observableArrayList("Изчерпан", "Наличен"));
        degreeComboBox.setItems(FXCollections.observableArrayList("ДМА", "МА"));
        setVisablility();
    }

    private void clearTableProducts() {
        productTable.getItems().clear();
    }

    public void setProductTable(ActionEvent event) throws IOException {
        initTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProducts()));
        } else {
            productTable.getItems().addAll(FXCollections.observableArrayList(productService.getProducts(SessionHelper.getCurrentUser().getId())));
        }
    }

    public void initTable() {
        clearTableProducts();
        productTypeService = new ProductTypeService();
        numColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });

        colorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Color color = param.getValue().getProductType().getColor();

                return new SimpleObjectProperty<>(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        });

        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

    }

    public void GoBack(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        rootPane.getChildren().setAll(pane);
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

    private void clearTableClient() {
        productTableClient.getItems().clear();
    }

    private void initProductTableClient() {
        clearTableClient();
        numColumnClient.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));
        nameColumnClient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        colorColumnClient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Color color = param.getValue().getProductType().getColor();

                return new SimpleObjectProperty<>(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        });
        priceColumnClient.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

    public void setTableCard() {
        initProductTableClient();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (CustomerComboBox.getValue() != null) {
            int customerID = CustomerComboBox.getSelectionModel().getSelectedItem().getId();
            productTableClient.getItems().addAll(FXCollections.observableArrayList(productService.getProductsByCustomer(customerID, false)));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете клиент...");
            alert.showAndWait();
        }
    }

    public void setClientProducts(ActionEvent event) throws IOException {
        setTableCard();
    }

    public void RefreshCard(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableClient();
        setTableCard();
        CustomerComboBox.valueProperty().set(null);
    }

    private void clearTableBrak() {
        productTableBrak.getItems().clear();
    }

    private void initTableBrak() {
        clearTableBrak();
        numColumnBrak.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));
        nameColumnBrak.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        colorColumnBrak.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Color color = param.getValue().getProductType().getColor();

                return new SimpleObjectProperty<>(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        });
        priceColumnBrak.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        brakColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                if (param.getValue().getDiscardedProduct() == null) {
                    return new SimpleObjectProperty<>("Не");

                } else {
                    return new SimpleObjectProperty<>("Да");
                }
            }
        });
    }

    public void setTableBrak() {
        initTableBrak();
        SessionHelper.getCurrentUser();
        reportService = new ReportService();
        if (wasteComboBox.getValue() != null) {
            int waste = wasteComboBox.getSelectionModel().getSelectedIndex();
            if (waste == 0) {//selectiranoto e brakuvan
                productTableBrak.getItems().addAll(FXCollections.observableArrayList(reportService.getDiscardedProducts(false)));

            } else//selectiranoto e nebrakuvan
            {
                productTableBrak.getItems().addAll(FXCollections.observableArrayList(reportService.getDiscardedProducts(true)));
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете брак...");
            alert.showAndWait();
        }
    }

    public void setBrak(ActionEvent event) throws IOException {
        setTableBrak();
    }

    public void RefreshBrak(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableBrak();
        setTableBrak();
        wasteComboBox.valueProperty().set(null);
    }

    private void clearTableAvailable() {
        productTableAvailable.getItems().clear();
    }

    private void initTableAvailable() {
        clearTableAvailable();
        numColumnAvailable.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));
        nameColumnAvailable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        colorColumnAvailable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Color color = param.getValue().getProductType().getColor();

                return new SimpleObjectProperty<>(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        });
        priceColumnAvailable.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        avaibleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                if (param.getValue().getIsAvailable()) {
                    return new SimpleObjectProperty<>("Да");

                } else {
                    return new SimpleObjectProperty<>("Не");
                }
            }
        });

    }

    public void setTableAvailable() {
        initTableAvailable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (availabilityComboBox.getValue() != null) {
            int available = availabilityComboBox.getSelectionModel().getSelectedIndex();
            if (available == 0) {//selectiranoto ne e v nalichnost
                productTableAvailable.getItems().addAll(FXCollections.observableArrayList(reportService.getAvaliableProducts(true)));

            } else//selectiranoto e v nalichnost
            {
                productTableAvailable.getItems().addAll(FXCollections.observableArrayList(reportService.getAvaliableProducts(false)));
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете наличност...");
            alert.showAndWait();
        }
    }

    public void setAvailable(ActionEvent event) throws IOException {
        setTableAvailable();
    }

    public void RefreshAvailable(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableAvailable();
        setTableAvailable();
        availabilityComboBox.valueProperty().set(null);
    }

    private void clearTableDegree() {
        productTableDegree.getItems().clear();
    }

    private void initTableDegree() {
        clearTableDegree();
        numColumnDegree.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));
        nameColumnDegree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getProductType().getName());

            }
        });
        colorColumnDegree.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Color color = param.getValue().getProductType().getColor();

                return new SimpleObjectProperty<>(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        });
        priceColumnDegree.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        degreeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                if (param.getValue().getIsDMA()) {
                    return new SimpleObjectProperty<>("ДМА");

                }
                return new SimpleObjectProperty<>("МА");
            }
        });

    }

    public void setTableDegree() {
        initTableDegree();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (degreeComboBox.getValue() != null) {
            int degree = degreeComboBox.getSelectionModel().getSelectedIndex();
            if (degree == 0) {//selectiranoto e DMA
                productTableDegree.getItems().addAll(FXCollections.observableArrayList(reportService.getAllProductsByDMAorMA(true)));
            } else//selectiranoto e MA
            {
                productTableDegree.getItems().addAll(FXCollections.observableArrayList(reportService.getAllProductsByDMAorMA(false)));
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете ДМА/МА...");
            alert.showAndWait();
        }
    }

    public void setDegree(ActionEvent event) throws IOException {
        setTableDegree();
    }

    public void RefreshDegree(ActionEvent event) throws IOException {//produktite koito moje da vzeme
        clearTableDegree();
        setTableDegree();
        degreeComboBox.valueProperty().set(null);
    }

    public void setVisablility() {
        availabilityComboBox.setVisible(false);
        availabilityComboBox.valueProperty().set(null);
        degreeComboBox.setVisible(false);
        degreeComboBox.valueProperty().set(null);
        wasteComboBox.setVisible(false);
        wasteComboBox.valueProperty().set(null);
        CustomerComboBox.setVisible(false);
        CustomerComboBox.valueProperty().set(null);
        productTable.setVisible(false);
        productTableAvailable.setVisible(false);
        productTableBrak.setVisible(false);
        productTableClient.setVisible(false);
        productTableDegree.setVisible(false);
        btnRefAvailable.setVisible(false);
        btnRefBrak.setVisible(false);
        btnRefCard.setVisible(false);
        btnRefProducts.setVisible(false);
        btnRefDegree.setVisible(false);
        btnRefreshAvailable.setVisible(false);
        btnRefreshBrak.setVisible(false);
        btnRefreshCard.setVisible(false);
        btnRefreshDegree.setVisible(false);
        clearTableAvailable();
        clearTableBrak();
        clearTableClient();
        clearTableDegree();
        clearTableProducts();
    }

    public void showProducts(ActionEvent event) throws IOException {
        setVisablility();
        btnRefProducts.setVisible(true);
        productTable.setVisible(true);
    }

    public void showCard(ActionEvent event) throws IOException {
        setVisablility();
        btnRefCard.setVisible(true);
        btnRefreshCard.setVisible(true);
        productTableClient.setVisible(true);
        CustomerComboBox.setVisible(true);
    }

    public void showBrak(ActionEvent event) throws IOException {
        setVisablility();
        btnRefBrak.setVisible(true);
        btnRefreshBrak.setVisible(true);
        productTableBrak.setVisible(true);
        wasteComboBox.setVisible(true);
    }

    public void showType(ActionEvent event) throws IOException {
        setVisablility();
        btnRefDegree.setVisible(true);
        btnRefreshDegree.setVisible(true);
        productTableDegree.setVisible(true);
        degreeComboBox.setVisible(true);
    }

    public void showAvailable(ActionEvent event) throws IOException {
        setVisablility();
        btnRefAvailable.setVisible(true);
        btnRefreshAvailable.setVisible(true);
        productTableAvailable.setVisible(true);
        availabilityComboBox.setVisible(true);
    }

}
