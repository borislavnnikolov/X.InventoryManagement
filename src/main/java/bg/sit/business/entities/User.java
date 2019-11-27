/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import bg.sit.business.enums.RoleType;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", unique = true, nullable = false)
    private int id;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty(message = "Потребителското име не трябва да е празно!")
    @Size(min = 3, max = 30, message = "Потребителското име трябва да е между {min} и {max} знака!")
    private String name;

    @NotNull
    @NotEmpty(message = "Потребителското име не трябва да е празно!")
    @Size(min = 3, max = 30, message = "Потребителското име трябва да е между {min} и {max} знака!")
    @Column(name = "Username", unique = true, nullable = false)
    private String username;

    @NotNull
    @NotEmpty(message = "Паролата не трябва да е празна!")
    @Size(min = 4, max = 30, message = "Паролата трябва да е между {min} и {max} знака!")
    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "RoleType", nullable = false)
    private RoleType roleType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Customer> customers = new ArrayList<Customer>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Amortization> amortizations = new ArrayList<Amortization>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<ProductType> productTypes = new ArrayList<ProductType>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Product> products = new ArrayList<Product>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<DiscardedProduct> discardedProducts = new ArrayList<DiscardedProduct>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<CustomerCard> customerCards = new ArrayList<>();

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public User() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setUser(this);
    }

    public void removeCustomer(Customer customer) {
        this.customers.remove(customer);
    }

    public Collection<Amortization> getAmortizations() {
        return amortizations;
    }

    public void setAmortizations(Collection<Amortization> amortizations) {
        this.amortizations = amortizations;
    }

    public void addAmortization(Amortization amortization) {
        this.amortizations.add(amortization);
        amortization.setUser(this);
    }

    public void removeAmortization(Amortization amortization) {
        this.amortizations.remove(amortization);
    }

    public Collection<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Collection<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public void addProductType(ProductType productType) {
        this.productTypes.add(productType);
        productType.setUser(this);
    }

    public void removeProductType(ProductType productType) {
        this.productTypes.remove(productType);
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setUser(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public Collection<DiscardedProduct> getDiscardedProducts() {
        return discardedProducts;
    }

    public void setDiscardedProducts(Collection<DiscardedProduct> discardedProducts) {
        this.discardedProducts = discardedProducts;
    }

    public void addDiscardedProduct(DiscardedProduct discardedProduct) {
        this.discardedProducts.add(discardedProduct);
        discardedProduct.setUser(this);
    }

    public void removeDiscardedProduct(DiscardedProduct discardedProduct) {
        this.discardedProducts.remove(discardedProduct);
    }

    public Collection<CustomerCard> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(Collection<CustomerCard> customerCards) {
        this.customerCards = customerCards;
    }

    public void addCustomerCard(CustomerCard customerCard) {
        this.customerCards.add(customerCard);
        customerCard.setUser(this);
    }

    public void removeCustomerCard(CustomerCard customerCard) {
        this.customerCards.remove(customerCard);
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
