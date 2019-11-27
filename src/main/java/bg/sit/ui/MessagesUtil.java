/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.ui;

import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class MessagesUtil {

    public static void showMessage(String message, Alert.AlertType alertType) {
        Alert dialogoErro = new Alert(alertType);
        dialogoErro.setTitle("Съобщение от системата");
        dialogoErro.setContentText(message);
        dialogoErro.showAndWait();
    }
}
