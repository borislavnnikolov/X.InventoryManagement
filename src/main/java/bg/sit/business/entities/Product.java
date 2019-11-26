/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", unique = true, nullable = false)
    private int id;

    // Format U[UserID]-PT[ProductTypeID]-[SEQUANCE]
    @Column(name = "InventoryNumber", unique = true, nullable = false)
    private String inventoryNumber;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "IsDMA", nullable = false)
    private boolean isDMA;

    @Column(name = "IsAvailable", nullable = false)
    private boolean isAvailable;

    @Column(name = "DateCreated", nullable = false)
    private Date dateCreated = new Date();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private Collection<CustomerCard> customerCards = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER, optional = true)
    private DiscardedProduct discardedProduct;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductTypeID", nullable = false)
    private ProductType productType;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "AmortizationID", nullable = true)
    private Amortization amortization;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsDMA() {
        return isDMA;
    }

    public void setIsDMA(boolean isDMA) {
        this.isDMA = isDMA;
    }

    public DiscardedProduct getDiscardedProduct() {
        return discardedProduct;
    }

    public void setDiscardedProduct(DiscardedProduct discardedProduct) {
        this.discardedProduct = discardedProduct;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Amortization getAmortization() {
        return amortization;
    }

    public void setAmortization(Amortization amortization) {
        this.amortization = amortization;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Collection<CustomerCard> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(Collection<CustomerCard> customerCards) {
        this.customerCards = customerCards;
    }

    public void addCustomerCard(CustomerCard customerCard) {
        this.customerCards.add(customerCard);
        customerCard.setProduct(this);
    }

    public void removeCustomerCard(CustomerCard customerCard) {
        this.customerCards.remove(customerCard);
    }

}
