/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.User;
import bg.sit.business.enums.RoleType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Dell
 */
public class UsersService extends BaseService {

    // Get all users from database
    public List<User> getUsers() {
        Session session = null;
        List<User> users = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM User WHERE isDeleted = false";
            users = session.createQuery(hql).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            sessionFactory.close();
        }

        return users;
    }

    // Check if there is a user with this parameters
    public boolean login(String username, String password) {
        Session session = null;
        boolean isLoginSuccessfull = false;
        username = username.toLowerCase();
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END FROM User AS u WHERE u.username = :username AND LOWER(u.password) = :password AND u.isDeleted = false";
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

    // Add user to database
    public User addUser(String name, String username, String password, RoleType roleType) {
        Session session = null;
        Transaction transaction = null;
        User newUser = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            newUser = new User();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRoleType(roleType);

            session.save(newUser);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            sessionFactory.close();
        }

        return newUser;
    }

    // Get user from database by userID
    public User getUserByID(int userID) {
        Session session = null;
        User foundUser = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM User WHERE id = :userID AND isDeleted = false";
            foundUser = (User) session.createQuery(hql, User.class).setParameter("userID", userID).getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            sessionFactory.close();
        }

        return foundUser;
    }

    // Update user by userID in database
    public User updateUser(int userID, String name, String username, String password, RoleType roleType) {
        Session session = null;
        Transaction transaction = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, userID);

            if (name != null && !name.equals("")) {
                user.setName(name);
            }

            if (username != null && !username.equals("")) {
                user.setUsername(username);
            }

            if (password != null && !password.equals("")) {
                user.setPassword(password);
            }

            if (roleType != RoleType.NONE) {
                user.setRoleType(roleType);
            }

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            sessionFactory.close();
        }

        return user;
    }

    // Soft delete user by userID
    public boolean deleteUser(int userID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.find(User.class, userID);
            user.setIsDeleted(true);
            session.save(user);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            sessionFactory.close();
        }

        return isSuccessfull;
    }
    
    // Force delete user from database, once deleted it cannot be reverted
    public boolean forceDeleteUser(int userID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;
        
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.find(User.class, userID);
            session.delete(user);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            sessionFactory.close();
        }

        return isSuccessfull;
    }
}
