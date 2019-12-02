/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "discarded_products")
public class DiscardedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscardedProductID", unique = true, nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Reason", nullable = false)
    private String reason;

    @Column(name = "DateDiscarded", nullable = false)
    private Date dateDiscarded = new Date();

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public DiscardedProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDateDiscarded() {
        return dateDiscarded;
    }

    public void setDateDiscarded(Date dateDiscarded) {
        this.dateDiscarded = dateDiscarded;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
