/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "amortizations")
public class Amortization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AmortizationID", unique = true, nullable = false)
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Days", nullable = false)
    private int days;

    @Column(name = "RepeatLimit", nullable = false)
    private int repeatLimit;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "amortization", fetch = FetchType.EAGER)
    private Collection<Product> products = new ArrayList<Product>();

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public Amortization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getRepeatLimit() {
        return repeatLimit;
    }

    public void setRepeatLimit(int repeat) {
        this.repeatLimit = repeat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setAmortization(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
