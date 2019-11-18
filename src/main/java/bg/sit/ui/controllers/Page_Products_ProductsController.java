package bg.sit.ui.controllers;

import bg.sit.business.entities.Product;
import bg.sit.business.entities.ProductType;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.ProductService;
import bg.sit.business.services.ProductTypeService;
import bg.sit.session.SessionHelper;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
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

public class Page_Products_ProductsController implements Initializable {

    ProductService productService;
    ProductTypeService productTypeService;

    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> numColumn;
    //@FXML
    //private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<ProductType, String> nameColumn;
    @FXML
    private TableColumn<Product, Color> colorColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, String> armortizationColumn;
    @FXML
    private TableColumn<Product, Boolean> brakColumn;
    @FXML
    private TableColumn<Product, Date> dateColumn;
    @FXML
    private ComboBox<String> prType;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<String> CBDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productTypeService = new ProductTypeService();
        productService = new ProductService();
        setTable();
        prType.setItems(FXCollections.observableArrayList("асд", "дса", "евя"));
        CBDelete.setItems(FXCollections.observableArrayList("Деактивиране", "Принудително изтриване"));
    }

    private void initTable() {
        productTypeService = new ProductTypeService();
        numColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("inventoryNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductType, String>("name"));
        //nameColumn.setCellValueFactory(new PropertyValueFactory<Product, List<ProductType>>(productTypeService.));
        //colorColumn.setCellValueFactory(new PropertyValueFactory<Product, Color>("color"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("isDMA"));
        armortizationColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("amortization"));
        brakColumn.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("isAvailable"));
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
        clearForm();
        int index = prType.getSelectionModel().getSelectedIndex();
        productService = new ProductService();
        productService.addProduct(index, true, Integer.parseInt(txtPrice.getText()), 0);
        setTable();
    }

    public void DELETE(ActionEvent event) throws IOException {

        Product product = table.getSelectionModel().getSelectedItem();
        if (product != null) {
            productService = new ProductService();
            if (CBDelete.getValue() == "Принудително изтриване") {

                productService.forceDeleteProductType(product.getId());
            } else {
                productService.deleteProduct(product.getId());
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
}
