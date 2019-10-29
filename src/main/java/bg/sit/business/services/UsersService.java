/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.HibernateUtil;
import bg.sit.business.pojo.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
