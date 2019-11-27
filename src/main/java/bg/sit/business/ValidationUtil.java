/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business;

import bg.sit.ui.MessagesUtil;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Dell
 */
public class ValidationUtil {

    private static ValidatorFactory buildValidatorFactory() {
        try {

            return Validation.buildDefaultValidatorFactory();

        } catch (Throwable ex) {

            System.err.println("Initial ValidatorFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Validator getValidator() {
        return buildValidatorFactory().getValidator();
    }

    public static <T> void ShowErrors(Set<ConstraintViolation<T>> validations) {
        String message = "";
        for (ConstraintViolation<T> next : validations) {
            message += next.getMessage() + "\n";
        }

        MessagesUtil.showMessage(message, Alert.AlertType.ERROR);
    }

}
