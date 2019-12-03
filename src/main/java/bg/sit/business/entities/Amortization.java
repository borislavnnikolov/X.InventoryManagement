/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "amortizations")
public class Amortization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AmortizationID", unique = true, nullable = false)
    private int id;

    @NotNull
    @NotEmpty(message = "Името не трябва да е празно!")
    @Size(min = 2, max = 30, message = "Името трябва да е между {min} и {max} знака!")
    @Column(name = "Name", nullable = false)
    private String name;

    @Digits(integer = 6, fraction = 2, message = "Цената трябва да е {integer} знака преди запетаята и  {fraction} знака след запетаята")
    @Column(name = "Price", nullable = false)
    private double price;

    @Min(value = 7, message = "Минималния период на амортизация е 7 дена")
    @Max(value = 365, message = "Максималния период на амортизация е 365 дена")
    @Column(name = "Days", nullable = false)
    private int days;

    @Min(value = 1, message = "Минималния брой повторения е 1 път ")
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
