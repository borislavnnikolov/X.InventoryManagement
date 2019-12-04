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

    public long countProducts() {
        Session session = null;
        long productsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(p) FROM Product AS p WHERE p.isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND p.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Long.class);
            productsCount = (long) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return productsCount;
    }

    public long countClients() {
        Session session = null;
        long clientsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(c) FROM Customer AS c WHERE c.isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND c.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Long.class);
            clientsCount = (long) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return clientsCount;
    }

    public long countCustomerCards() {
        Session session = null;
        long customerCardsCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(cc) FROM CustomerCard AS cc WHERE cc.isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND cc.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Long.class);
            customerCardsCount = (long) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return customerCardsCount;
    }

    public long countUsers() {
        Session session = null;
        long usersCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(u) FROM User AS u WHERE u.isDeleted = false";
            Query query = session.createQuery(hql, Long.class);
            usersCount = (long) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

        return usersCount;
    }
}
