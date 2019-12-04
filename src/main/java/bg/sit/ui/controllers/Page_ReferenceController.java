package bg.sit.ui.controllers;

import bg.sit.business.entities.Amortization;
import bg.sit.business.entities.Customer;
import bg.sit.business.entities.Product;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.AmortizationService;
import bg.sit.business.services.CustomerService;
import bg.sit.business.services.ProductService;
import bg.sit.business.services.ProductTypeService;
import bg.sit.session.SessionHelper;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, String> amortizationColumn;
    @FXML
    private TableColumn<Product, String> brakColumn;
    @FXML
    private TableColumn<Product, String> avaibleColumn;
    @FXML
    private TableColumn<Product, Date> dateColumn;
    @FXML
    private ComboBox<Customer> CustomerComboBox;
    @FXML
    private ComboBox wasteComboBox;
    @FXML
    private ComboBox availabilityComboBox;
    @FXML
    private ComboBox degreeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerService = new CustomerService();
        initClientComboBox();
        wasteComboBox.setItems(FXCollections.observableArrayList("Бракуван", "Не бракуван"));
        availabilityComboBox.setItems(FXCollections.observableArrayList("В наличност", "Изчерпан"));
        degreeComboBox.setItems(FXCollections.observableArrayList("ДМА", "МА"));
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

        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                if (param.getValue().getIsDMA()) {
                    return new SimpleObjectProperty<>("ДМА");

                }
                return new SimpleObjectProperty<>("МА");
            }
        });

        amortizationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                Amortization amort = param.getValue().getAmortization();

                if (amort == null) {
                    return new SimpleObjectProperty<>("Няма");
                }

                return new SimpleObjectProperty<>(amort.getName()
                );

            }
        });

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
        dateColumn.setCellValueFactory(new PropertyValueFactory<Product, Date>("dateCreated"));
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

}
