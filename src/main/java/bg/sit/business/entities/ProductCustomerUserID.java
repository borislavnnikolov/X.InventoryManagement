/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Embeddable
public class ProductCustomerUserID implements Serializable {

    @Column(name = "ProductID")
    private int productID;

    @Column(name = "CustomerID")
    private int customerID;

    @Column(name = "UserID")
    private int userID;

    private ProductCustomerUserID() {
    }

    public ProductCustomerUserID(
            int productID,
            int customerID,
            int userID) {
        this.productID = productID;
        this.customerID = customerID;
        this.userID = userID;
    }

    //Getters omitted for brevity
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCustomerUserID that = (ProductCustomerUserID) o;
        return Objects.equals(productID, that.productID)
                && Objects.equals(customerID, that.customerID)
                && Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, customerID, userID);
    }
}
