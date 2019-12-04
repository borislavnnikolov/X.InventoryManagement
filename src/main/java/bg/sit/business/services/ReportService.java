/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.enums.RoleType;
import bg.sit.session.SessionHelper;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Dell
 */
public class ReportService extends BaseService {

    public int countProducts() {
        Session session = null;
        int productsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(*) FROM Product WHERE isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Integer.class);
            productsCount = (int) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return productsCount;
    }

    public int countClients() {
        Session session = null;
        int clientsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(*) FROM Client WHERE isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Integer.class);
            clientsCount = (int) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return clientsCount;
    }

    public int countCustomerCards() {
        Session session = null;
        int customerCardsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(*) FROM CustomerCard WHERE isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Integer.class);
            customerCardsCount = (int) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return customerCardsCount;
    }

    public int countUsers() {
        Session session = null;
        int usersCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(*) FROM User WHERE isDeleted = false";
            Query query = session.createQuery(hql, Integer.class);
            usersCount = (int) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return usersCount;
    }
}
