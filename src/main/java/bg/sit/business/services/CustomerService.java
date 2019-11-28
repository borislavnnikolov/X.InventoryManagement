/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.Customer;
import bg.sit.business.entities.CustomerCard;
import bg.sit.business.entities.Product;
import bg.sit.business.entities.User;
import bg.sit.session.SessionHelper;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Dell
 */
public class CustomerService extends BaseService {

    // Add customer to the database
    public Customer addCustomer(String name, String location, String phone) {
        Session session = null;
        Transaction transaction = null;
        Customer newCustomer = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setLocation(location);
            newCustomer.setPhone(phone);
            newCustomer.setUser(session.get(User.class, SessionHelper.getCurrentUser().getId()));
            session.save(newCustomer);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newCustomer;
    }

    // Get all customers for specific user
    public List<Customer> getCustomers(int userID) {
        Session session = null;
        Transaction transaction = null;
        List<Customer> customers = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Customer AS c WHERE c.isDeleted = false";
            if (userID > 0) {
                hql = "SELECT c FROM Customer as c INNER JOIN c.user AS u WHERE c.isDeleted = false AND u.id = :userID";
            }

            Query q = session.createQuery(hql, Customer.class);

            if (userID > 0) {
                q.setParameter("userID", userID);
            }

            customers = q.list();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return customers;
    }

    // Get all customers
    public List<Customer> getCustomers() {
        return this.getCustomers(-1);
    }

    // Update customer by customerID
    public Customer updateCustomer(int customerID, String name, String location, String phone) {
        Session session = null;
        Transaction transaction = null;
        Customer editCustomer = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            editCustomer = (Customer) session.createQuery("FROM Customer AS c WHERE c.isDeleted = false AND c.id = :customerID").setParameter("customerID", customerID).getSingleResult();

            if (editCustomer == null) {
                throw new Exception("editCustomer is null! (CustomersService -> updateCustomer)");
            }

            if (name != null && !name.equals("")) {
                editCustomer.setName(name);
            }

            if (location != null && !location.equals("")) {
                editCustomer.setLocation(location);
            }

            if (phone != null && !phone.equals("")) {
                editCustomer.setPhone(phone);
            }

            session.saveOrUpdate(editCustomer);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return editCustomer;
    }

    // Soft delete customer by customerID
    public boolean deleteCustomer(int customerID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Customer customer = session.find(Customer.class, customerID);
            customer.setIsDeleted(true);
            session.save(customer);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return isSuccessfull;
    }

    // Force delete customer by customerID from database, once deleted it cannot be reverted
    public boolean forceDeleteCustomer(int customerID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Customer customer = session.find(Customer.class, customerID);
            session.delete(customer);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return isSuccessfull;
    }

    // Add customer card to the database
    public CustomerCard addCustomerCard(int customerID, int productID) {
        Session session = null;
        Transaction transaction = null;
        CustomerCard newCustomerCard = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            newCustomerCard = new CustomerCard();

            Product chosenProduct = session.get(Product.class, productID);

            if (chosenProduct == null) {
                throw new Exception("chosenProduct is null. (CustomerService => addCustomerCard)");
            }

            newCustomerCard.setProduct(chosenProduct);

            Customer chosenCustomer = session.get(Customer.class, customerID);

            if (chosenCustomer == null) {
                throw new Exception("chosenCustomer is null. (CustomerService => addCustomerCard)");
            }

            newCustomerCard.setCustomer(chosenCustomer);

            newCustomerCard.setDateBorrowed(SessionHelper.getCurrentDate());
            newCustomerCard.setUser(session.get(User.class, SessionHelper.getCurrentUser().getId()));
            session.save(newCustomerCard);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newCustomerCard;
    }

    // Soft delete customer by customerID
    public boolean removeCustomerCard(int customerID, int productID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            CustomerCard customer = session.createQuery("FROM CustomerCard WHERE isDeleted = false AND product.id = " + productID + " AND customer.id = " + customerID, CustomerCard.class).getSingleResult();
            customer.setIsDeleted(true);
            session.save(customer);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return isSuccessfull;
    }
}
