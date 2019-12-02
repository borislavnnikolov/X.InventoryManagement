package bg.sit.ui.controllers;

import bg.sit.business.ValidationUtil;
import bg.sit.business.entities.ProductType;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.ProductTypeService;
import bg.sit.session.SessionHelper;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.validation.ConstraintViolation;

public class Page_Products_ProductTypeController implements Initializable {

    ProductTypeService productTypeService;
    @FXML
    private TableView<ProductType> table;
    @FXML
    private TableColumn<ProductType, String> nameColumn;
    @FXML
    private TableColumn<ProductType, Color> colorColumn;
    @FXML
    private ComboBox<String> CBDelete;
    @FXML
    private TextField txtName;
    @FXML
    private ColorPicker CPBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTable();
        CBDelete.setItems(FXCollections.observableArrayList("Деактивиране", "Принудително изтриване"));
    }

    private void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<ProductType, String>("name"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<ProductType, Color>("color"));
    }

    private void setTable() {
        initTable();
        SessionHelper.getCurrentUser();
        productTypeService = new ProductTypeService();
        if (SessionHelper.getCurrentUser().getRoleType() == RoleType.ADMIN) {
            table.getItems().addAll(FXCollections.observableArrayList(productTypeService.getProductTypes()));
        } else {
            table.getItems().addAll(FXCollections.observableArrayList(productTypeService.getProductTypes(SessionHelper.getCurrentUser().getId())));
        }
    }

    private void clearForm() {
        table.getItems().clear();
    }

    public void ADD(ActionEvent event) throws IOException {
        Set<ConstraintViolation<ProductType>> validations = ValidationUtil.getValidator().validateValue(ProductType.class, "name", txtName.getText());

        if (!validations.isEmpty()) {
            ValidationUtil.ShowErrors(validations);
        } else {
            clearForm();
            productTypeService = new ProductTypeService();
            javafx.scene.paint.Color fx = CPBox.getValue();
            java.awt.Color awtColor = new java.awt.Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());
            productTypeService.addProductType(txtName.getText(), awtColor);
            setTable();
        }
    }

    public void EDIT(ActionEvent event) throws IOException {
        Set<ConstraintViolation<ProductType>> validations = ValidationUtil.getValidator().validateValue(ProductType.class, "name", txtName.getText());

        if (!validations.isEmpty()) {
            ValidationUtil.ShowErrors(validations);
        } else {
            ProductType productType = table.getSelectionModel().getSelectedItem();
            if (productType != null) {
                productTypeService = new ProductTypeService();
                javafx.scene.paint.Color fx = CPBox.getValue();
                java.awt.Color awtColor = new java.awt.Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());
                productTypeService.updateProductType(productType.getId(), txtName.getText(), awtColor);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Опс...");
                alert.setHeaderText("Моля, посочете потребителя който искате да промените.");
                alert.showAndWait();
            }
            clearForm();
            setTable();
        }
    }

    public void DELETE(ActionEvent event) throws IOException {                      //TODO
        ProductType productType = table.getSelectionModel().getSelectedItem();
        if (productType != null) {
            productTypeService = new ProductTypeService();
            if (CBDelete.getSelectionModel().getSelectedIndex() == 2) {
                productTypeService.forceDeleteProductType(productType.getId());
            } else {
                productTypeService.deleteProductType(productType.getId());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Опс...");
            alert.setHeaderText("Моля, посочете какво искате да изтриете.");
            alert.showAndWait();
        }
        clearForm();
        setTable();
    }
}
