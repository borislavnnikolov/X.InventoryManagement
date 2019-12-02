/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import bg.sit.business.converters.ColorConverter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_types")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductTypeID", unique = true, nullable = false)
    private int id;

    @NotNull
    @NotEmpty(message = "Името на продукта не трябва да е празно!")
    @Size(min = 2, max = 30, message = "Името на продукта трябва да е между {min} и {max} знака!")
    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Color", nullable = false)
    @Convert(converter = ColorConverter.class)
    private Color color;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "productType")
    private Collection<Product> products = new ArrayList<Product>();

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public ProductType() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIsDeleted() {
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
        product.setProductType(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
