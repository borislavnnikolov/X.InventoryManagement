/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.tasks;

import bg.sit.business.entities.Product;
import bg.sit.business.services.ProductService;
import bg.sit.ui.MessagesUtil;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class DiscardProductTask extends TimerTask {

    private ProductService productService;

    public DiscardProductTask() {
        productService = new ProductService();
    }

    @Override
    public void run() {
        findAndDiscardProduct();
    }

    private void findAndDiscardProduct() {
        try {
            Product productForDiscard = productService.getProductForDiscard();

            if (productForDiscard == null) {
                return;
            }

            productService.discardProduct(productForDiscard.getId(), "Твърде стар!");

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MessagesUtil.showMessage("Продукт с инвентарен номер: " + productForDiscard.getInventoryNumber() + " и име " + productForDiscard.getProductType().getName() + " бе бракуван, защото е твърде стар!", Alert.AlertType.INFORMATION);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
