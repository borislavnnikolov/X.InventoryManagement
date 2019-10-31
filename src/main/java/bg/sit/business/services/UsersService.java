/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.HibernateUtil;
import bg.sit.business.entities.User;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Dell
 */
public class UsersService {

    private final SessionFactory sessionFactory;

    public UsersService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<User> getUsers() {
        Session session = null;
        List<User> users = null;
        try {
            session = sessionFactory.openSession();
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            sessionFactory.close();
        }

        return users;
    }

    public boolean login(String username, String password) {
        Session session = null;
        boolean isLoginSuccessfull = false;
        username = username.toLowerCase();
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END FROM User AS u WHERE u.username = :username AND LOWER(u.password) = :password";
            Query query = session.createQuery(hql, Boolean.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            isLoginSuccessfull = (boolean) query.getSingleResult();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            sessionFactory.close();
        }

        return isLoginSuccessfull;
    }
}
