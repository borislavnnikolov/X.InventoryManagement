/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID", unique = true, nullable = false)
    private int id;

    @NotNull
    @NotEmpty(message = "Името не трябва да е празно!")
    @Size(min = 2, max = 30, message = "Името трябва да е между {min} и {max} знака!")
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull
    @NotEmpty(message = "Местожителство не трябва да е празна!")
    @Size(min = 2, max = 30, message = "Местожителството трябва да е между {min} и {max} знака!")
    @Column(name = "Location")
    private String location;

    @NotNull
    @NotEmpty(message = "Телефония номер не трябва да е празно!")
    @Size(min = 5, max = 16, message = "Телефония номер трябва да е между {min} и {max} знака!")
    @Column(name = "Phone")
    private String phone;

    @Column(name = "IsDeleted")
    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private Collection<CustomerCard> customerCards = new ArrayList<>();

    public Customer() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Collection<CustomerCard> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(Collection<CustomerCard> customerCards) {
        this.customerCards = customerCards;
    }

    public void addCustomerCard(CustomerCard customerCard) {
        this.customerCards.add(customerCard);
        customerCard.setCustomer(this);
    }

    public void removeCustomerCard(CustomerCard customerCard) {
        this.customerCards.remove(customerCard);
    }
}
