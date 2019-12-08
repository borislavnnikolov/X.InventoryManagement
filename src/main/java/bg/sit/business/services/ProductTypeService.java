/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.entities.ProductType;
import bg.sit.business.entities.User;
import bg.sit.session.SessionHelper;
import java.awt.Color;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dell
 */
public class ProductTypeService extends BaseService {

    // Add product type to the database
    public ProductType addProductType(String name, Color color) {
        Session session = null;
        Transaction transaction = null;
        ProductType newProductType = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            LOGGER.info("Adding new product type.");
            newProductType = new ProductType();
            newProductType.setName(name);
            newProductType.setColor(color);
            newProductType.setUser(session.get(User.class, SessionHelper.getCurrentUser().getId()));
            LOGGER.info("Saving and commiting adding product type.");
            session.save(newProductType);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newProductType;
    }

    // Get all product types for specific user
    public List<ProductType> getProductTypes(int userID) {
        Session session = null;
        Transaction transaction = null;
        List<ProductType> productTypes = null;
        try {
            session = sessionFactory.openSession();
            LOGGER.info("Getting products types.");

            String hql = "FROM ProductType AS pt WHERE pt.isDeleted = false";
            if (userID > 0) {
                hql += " AND pt.user.id = " + userID;
            }

            productTypes = session.createQuery(hql, ProductType.class).list();
            LOGGER.info("Successfull gotten products types.");
        } catch (Exception e) {
            LOGGER.warning("Getting products types was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return productTypes;
    }

    // Get all product types
    public List<ProductType> getProductTypes() {
        return this.getProductTypes(-1);
    }

    // Update product type by productTypeID
    public ProductType updateProductType(int productTypeID, String name, Color color) {
        Session session = null;
        Transaction transaction = null;
        ProductType editProductType = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            LOGGER.info("Updating product type with ID: " + productTypeID);

            editProductType = (ProductType) session.createQuery("FROM ProductType AS pt WHERE pt.isDeleted = false AND pt.id = :productTypeID").setParameter("productTypeID", productTypeID).getSingleResult();

            if (editProductType == null) {
                throw new Exception("editProductType is null! (ProductTypeService -> updateProductType)");
            }

            if (name != null && !name.equals("")) {
                editProductType.setName(name);
            }

            if (color != null) {
                editProductType.setColor(color);
            }

            session.saveOrUpdate(editProductType);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.warning("Updating product type with ID " + productTypeID + " was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return editProductType;
    }

    // Soft delete product type by productTypeID
    public boolean deleteProductType(int productTypeID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            ProductType productType = session.find(ProductType.class, productTypeID);
            productType.setIsDeleted(true);
            LOGGER.info("Saving and commiting soft delete of product type.");
            session.save(productType);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            LOGGER.warning("Soft deleting product type with ID: " + productTypeID + " was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return isSuccessfull;
    }

    // Force delete product type by productTypeID from database, once deleted it cannot be reverted
    public boolean forceDeleteProductType(int productTypeID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            ProductType productType = session.find(ProductType.class, productTypeID);
            LOGGER.info("Saving and commiting force delete of product type.");
            session.delete(productType);
            transaction.commit();
            isSuccessfull = true;
        } catch (Exception e) {
            LOGGER.warning("Force deleting product type with ID: " + productTypeID + " was unsuccessfull:\n" + e.getStackTrace());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return isSuccessfull;
    }
}
