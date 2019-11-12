/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.Amortization;
import bg.sit.business.entities.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dell
 */
public class AmortizationService extends BaseService {

    // Add amortization to the database
    public Amortization addAmortization(String name, double price, int days, int repeatLimit, int userID) {
        Session session = null;
        Transaction transaction = null;
        Amortization newAmortization = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            newAmortization = new Amortization();
            newAmortization.setName(name);
            newAmortization.setPrice(price);
            newAmortization.setDays(days);
            newAmortization.setRepeatLimit(repeatLimit);
            newAmortization.setUser(session.get(User.class, userID));
            session.save(newAmortization);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newAmortization;
    }

    // Get all amortizations for specific user
    public List<Amortization> getAmortizations(int userID) {
        Session session = null;
        Transaction transaction = null;
        List<Amortization> amortizations = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Amortization AS a WHERE a.isDeleted = false";
            if (userID > 0) {
                hql += " AND a.user.id = " + userID;
            }

            amortizations = session.createQuery(hql, Amortization.class).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return amortizations;
    }

    // Get all customers
    public List<Amortization> getAmortizations() {
        return this.getAmortizations(-1);
    }

    // Update amortization by amortizationID
    public Amortization updateAmortization(int amortizationID, String name, double price, int days, int repeatLimit) {
        Session session = null;
        Transaction transaction = null;
        Amortization editAmortization = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            editAmortization = (Amortization) session.createQuery("FROM Amortization AS a WHERE a.isDeleted = false AND a.id = :amortizationID").setParameter("amortizationID", amortizationID).getSingleResult();

            if (editAmortization == null) {
                throw new Exception("editAmortization is null! (AmortizationService -> updateAmortization)");
            }

            if (name != null && !name.equals("")) {
                editAmortization.setName(name);
            }

            if (price > 0) {
                editAmortization.setPrice(price);
            }

            if (days > 0) {
                editAmortization.setDays(days);
            }

            if (repeatLimit >= 0) {
                editAmortization.setRepeatLimit(repeatLimit);
            }

            session.saveOrUpdate(editAmortization);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return editAmortization;
    }

    // Soft delete amortization by amortizationID
    public boolean deleteAmortization(int amortizationID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Amortization amortization = session.find(Amortization.class, amortizationID);
            amortization.setIsDeleted(true);
            session.save(amortization);
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

    // Force delete amortization by amortizationID from database, once deleted it cannot be reverted
    public boolean forceDeleteAmortization(int amortizationID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Amortization amortization = session.find(Amortization.class, amortizationID);
            session.delete(amortization);
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
