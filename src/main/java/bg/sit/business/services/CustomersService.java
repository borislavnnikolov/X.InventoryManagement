/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.Customer;
import bg.sit.business.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dell
 */
public class CustomersService extends BaseService {

    // Add customer to the database
    public Customer addCustomer(String name, String location, String phone, int userID) {
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
            newCustomer.setUser(session.get(User.class, userID));
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
}
