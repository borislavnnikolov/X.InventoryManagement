/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "customer_cards")
public class CustomerCard {

    @EmbeddedId
    private ProductCustomerUserID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID")
    @MapsId("productID")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerID")
    @MapsId("customerID")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    @MapsId("userID")
    private User user;

    @Column(name = "DateBorrowed", nullable = false)
    private Date dateBorrowed = new Date();

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public CustomerCard() {
    }

    public ProductCustomerUserID getId() {
        return id;
    }

    public void setId(ProductCustomerUserID id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
