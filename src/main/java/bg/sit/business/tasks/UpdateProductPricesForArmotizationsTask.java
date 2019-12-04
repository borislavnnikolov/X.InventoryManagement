/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.tasks;

import bg.sit.business.entities.Product;
import bg.sit.business.services.ProductService;
import bg.sit.ui.MessagesUtil;
import java.util.List;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class UpdateProductPricesForArmotizationsTask extends TimerTask {

    private ProductService productService;

    public UpdateProductPricesForArmotizationsTask() {
        productService = new ProductService();
    }

    @Override
    public void run() {
        List<Product> products = productService.updateProductPricesForAmortizations();

        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MessagesUtil.showMessage("Продукт с инвентарен номер: " + product.getInventoryNumber() + " и име " + product.getProductType().getName() + " смени типа си на МА!", Alert.AlertType.INFORMATION);
                    }
                });
            }
        }
    }
}
