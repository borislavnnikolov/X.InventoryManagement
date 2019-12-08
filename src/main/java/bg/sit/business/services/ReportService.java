/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.Product;
import bg.sit.business.enums.RoleType;
import bg.sit.session.SessionHelper;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
            LOGGER.warning("Getting count of products was unsuccessfull:\n" + e.getStackTrace());
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
            LOGGER.warning("Getting count of clients was unsuccessfull:\n" + e.getStackTrace());
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
            LOGGER.warning("Getting count of customer cards was unsuccessfull:\n" + e.getStackTrace());
        } finally {
            session.close();
        }

        return customerCardsCount;
    }

    public long countProductTypes() {
        Session session = null;
        long usersCount = 0;

        try {
            session = sessionFactory.openSession();
            String hql = "SELECT COUNT(pt) FROM ProductType AS pt WHERE pt.isDeleted = false";

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND pt.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            Query query = session.createQuery(hql, Long.class);
            usersCount = (long) query.getSingleResult();
        } catch (Exception e) {
            LOGGER.warning("Getting count of products types was unsuccessfull:\n" + e.getStackTrace());
        } finally {
            session.close();
        }

        return usersCount;
    }

    // Get all discarded products, inverted products all that are not discarded
    public List<Product> getDiscardedProducts(boolean inverted) {
        Session session = null;
        Transaction transaction = null;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();

            String hql = "SELECT p FROM Product AS p LEFT JOIN p.discardedProduct AS dp WHERE p.isDeleted = false AND ";
            if (inverted) {
                hql += "dp IS NULL";
            } else {
                hql += "dp IS NOT NULL";
            }

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND p.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            products = session.createQuery(hql, Product.class).list();
        } catch (Exception e) {
            LOGGER.warning("Getting discarded products was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return products;
    }

    // Get all avaliable products, inverted products all that are not avaliable
    public List<Product> getAvaliableProducts(boolean inverted) {
        Session session = null;
        Transaction transaction = null;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Product AS p WHERE p.isDeleted = false AND ";
            if (inverted) {
                hql += "p.isAvailable = false";
            } else {
                hql += "p.isAvailable = true";
            }

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND p.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            products = session.createQuery(hql, Product.class).list();
        } catch (Exception e) {
            LOGGER.warning("Getting avaliable products was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return products;
    }

    // Get all DMA products, if false products all that are MA
    public List<Product> getAllProductsByDMAorMA(boolean isDMA) {
        Session session = null;
        Transaction transaction = null;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Product AS p WHERE p.isDeleted = false AND ";
            if (isDMA) {
                hql += "p.isDMA = true";
            } else {
                hql += "p.isDMA = false";
            }

            if (SessionHelper.getCurrentUser().getRoleType() == RoleType.MOL) {
                hql += " AND p.user.id = " + SessionHelper.getCurrentUser().getId();
            }

            products = session.createQuery(hql, Product.class).list();
        } catch (Exception e) {
            LOGGER.warning("Getting products by DMA or MA was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return products;
    }
}
