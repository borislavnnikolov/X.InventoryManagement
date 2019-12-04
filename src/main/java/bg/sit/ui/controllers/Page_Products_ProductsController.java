package bg.sit.ui.controllers;

import bg.sit.business.ValidationUtil;
import bg.sit.business.entities.Amortization;
import bg.sit.business.entities.Product;
import bg.sit.business.entities.ProductType;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.AmortizationService;
import bg.sit.business.services.ProductService;
import bg.sit.business.services.ProductTypeService;
import bg.sit.session.SessionHelper;
import bg.sit.ui.MessagesUtil;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.validation.ConstraintViolation;

public class Page_Products_ProductsController implements Initializable {

    ProductService productService;
    ProductTypeService productTypeService;
    AmortizationService amortizationService;

    @FXML
    private TableView<Product> table;
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
    private ComboBox<ProductType> prType;
    @FXML
    private ComboBox<Amortization> prAmortization;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<String> CBDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productTypeService = new ProductTypeService();
        productService = new ProductService();
        amortizationService = new AmortizationService();
        setTable();
        initProductTypeCombobox();
        initAmortizationCombobox();
        CBDelete.setItems(FXCollections.observableArrayList("Деактивиране", "Принудително изтриване"));
    }

    private void initTable() {
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

    private void setTable() {
        initTable();
        SessionHelper.getCurrentUser();
        productService = new ProductService();
        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            table.getItems().addAll(FXCollections.observableArrayList(productService.getProducts()));
        } else {
            table.getItems().addAll(FXCollections.observableArrayList(productService.getProducts(SessionHelper.getCurrentUser().getId())));
        }
    }

    private void clearForm() {
        table.getItems().clear();
    }

    public void ADD(ActionEvent event) throws IOException {
        Product productValidation = new Product();

        try {
            productValidation.setPrice(Double.parseDouble(txtPrice.getText()));
        } catch (Exception e) {
            MessagesUtil.showMessage("Невалидни или празни дании!", Alert.AlertType.ERROR);
            return;
        }

        Set<ConstraintViolation<Product>> validations = ValidationUtil.getValidator().validate(productValidation);

        if (!validations.isEmpty()) {
            ValidationUtil.ShowErrors(validations);
        } else {
            clearForm();
            productService = new ProductService();
            if (prType.getValue() != null && prAmortization.getValue() != null) {
                int product = prType.getSelectionModel().getSelectedItem().getId();
                int amortization = prAmortization.getSelectionModel().getSelectedItem().getId();
                productService.addProduct(product, true, Double.parseDouble(txtPrice.getText()), amortization);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Опс...");
                alert.setHeaderText("Моля, посочете продукт и амортизация..");
                alert.showAndWait();
            }

            setTable();

        }
    }

    public void DELETE(ActionEvent event) throws IOException {

        Product product = table.getSelectionModel().getSelectedItem();
        if (product != null) {
            productService = new ProductService();
            if (CBDelete.getSelectionModel().getSelectedIndex() == 2) {

                productService.forceDeleteProduct(product.getId());
            } else {
                productService.deleteProduct(product.getId());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете какво искате да изтриете..");
            alert.showAndWait();
        }
        clearForm();
        setTable();
    }

    private void initProductTypeCombobox() {
        prType.setConverter(new StringConverter<ProductType>() {

            @Override
            public String toString(ProductType object) {
                return object.getName();
            }

            @Override
            public ProductType fromString(String string) {
                for (ProductType pt : prType.getItems()) {
                    if (pt.getName().equals(string)) {
                        return pt;
                    }
                }

                return null;
            }
        });

        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            prType.setItems(FXCollections.observableArrayList(productTypeService.getProductTypes()));
        } else {
            prType.setItems(FXCollections.observableArrayList(productTypeService.getProductTypes(SessionHelper.getCurrentUser().getId())));
        }
    }

    private void initAmortizationCombobox() {
        prAmortization.setConverter(new StringConverter<Amortization>() {

            @Override
            public String toString(Amortization object) {
                return object.getName();
            }

            @Override
            public Amortization fromString(String string) {
                for (Amortization am : prAmortization.getItems()) {
                    if (am.getName().equals(string)) {
                        return am;
                    }
                }

                return null;
            }
        });

        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            prAmortization.setItems(FXCollections.observableArrayList(amortizationService.getAmortizations()));
        } else {
            prAmortization.setItems(FXCollections.observableArrayList(amortizationService.getAmortizations(SessionHelper.getCurrentUser().getId())));
        }
    }
}
