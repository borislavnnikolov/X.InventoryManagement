/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.constants.Constants;
import bg.sit.business.entities.Amortization;
import bg.sit.business.entities.DiscardedProduct;
import bg.sit.business.entities.Product;
import bg.sit.business.entities.ProductType;
import bg.sit.business.entities.User;
import bg.sit.session.SessionHelper;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dell
 */
public class ProductService extends BaseService {

// Add product to the database
    public Product addProduct(int productTypeID, boolean isAvailable, double price, int amortizationID) {
        Session session = null;
        Transaction transaction = null;
        Product newProduct = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Integer lastID;
            lastID = (Integer) (session.createQuery("SELECT p.id FROM Product AS p ORDER BY p.id DESC").setMaxResults(1).uniqueResult());
            if (lastID == null) {
                lastID = 0;
            }

            newProduct = new Product();
            ProductType chosenProductType = session.get(ProductType.class, productTypeID);

            if (chosenProductType == null) {
                throw new Exception("Could not find product type with ID " + productTypeID);
            }

            chosenProductType.addProduct(newProduct);

            if (amortizationID > 0 && price > Constants.DEFAULT_MA_LIMIT) {
                newProduct.setIsDMA(true);
                Amortization chosenAmortization = session.get(Amortization.class, amortizationID);
                if (chosenAmortization == null) {
                    throw new Exception("Could not find amortization with ID " + amortizationID);
                }

                chosenAmortization.addProduct(newProduct);
            }

            newProduct.setIsAvailable(isAvailable);
            newProduct.setPrice(price);
            newProduct.setDateCreated(SessionHelper.getCurrentDate());
            newProduct.setUser(session.get(User.class, SessionHelper.getCurrentUser().getId()));

            newProduct.setInventoryNumber("U" + newProduct.getUser().getId() + "-PT" + newProduct.getProductType().getId() + "-" + (lastID + 1));
            session.save(newProduct);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newProduct;
    }

    // Get all product for specific user
    public List<Product> getProducts(int userID) {
        Session session = null;
        Transaction transaction = null;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Product AS p WHERE p.isDeleted = false";
            if (userID > 0) {
                hql += " AND p.user.id = " + userID;
            }

            products = session.createQuery(hql, Product.class).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return products;
    }

    // Get all product
    public List<Product> getProducts() {
        return this.getProducts(-1);
    }

    // Update product by productID
    public Product updateProduct(int productID, Boolean isAvailable, double price, int amortizationID) {
        Session session = null;
        Transaction transaction = null;
        Product editProduct = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            editProduct = (Product) session.createQuery("FROM Product AS p WHERE p.isDeleted = false AND p.id = :productID").setParameter("productID", productID).getSingleResult();

            if (editProduct == null) {
                throw new Exception("editProduct is null! (ProductService -> updateProduct)");
            }

            if (isAvailable != null) {
                editProduct.setIsAvailable(isAvailable);
            }

            if (price > 0) {
                editProduct.setPrice(price);
            }

            if (amortizationID > 0 && editProduct.getAmortization() != null) {
                Amortization oldAmortization = editProduct.getAmortization();
                Amortization newAmortization = session.get(Amortization.class, amortizationID);

                if (oldAmortization == null || newAmortization == null) {
                    throw new Exception("oldAmortization or newAmortization is null! (ProductService -> updateProduct)");
                }

                if (oldAmortization.getId() != newAmortization.getId()) {
                    oldAmortization.removeProduct(editProduct);
                    newAmortization.addProduct(editProduct);
                }
            }

            session.update(editProduct);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return editProduct;
    }

    // Soft delete product by productID
    public boolean deleteProduct(int productID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Product product = session.find(Product.class, productID);
            product.setIsDeleted(true);
            session.save(product);
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

    // Force delete product by productID from database, once deleted it cannot be reverted
    public boolean forceDeleteProductType(int productID) {
        Session session = null;
        Transaction transaction = null;
        boolean isSuccessfull = false;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Product product = session.find(Product.class, productID);
            session.delete(product);
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

    public DiscardedProduct discardProduct(int productID, String reason) {
        Session session = null;
        Transaction transaction = null;
        DiscardedProduct newDiscardedProduct = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            newDiscardedProduct = new DiscardedProduct();
            Product chosenProduct = session.get(Product.class, productID);

            if (chosenProduct == null) {
                throw new Exception("Could not find product with ID " + productID);
            }

            newDiscardedProduct.setProduct(chosenProduct);
            newDiscardedProduct.setUser(session.get(User.class, SessionHelper.getCurrentUser().getId()));
            newDiscardedProduct.setDateDiscarded(SessionHelper.getCurrentDate());
            newDiscardedProduct.setReason(reason);

            session.save(newDiscardedProduct);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return newDiscardedProduct;
    }

    // Get all product for specific user inverted means all products but specified customer
    public List<Product> getProductsByCustomer(int customerID, boolean inverted) {
        Session session = null;
        Transaction transaction = null;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();

            String hql = "FROM Product AS p WHERE p.isDeleted = false";
            if (customerID > 0) {
                hql += "AND ";

                if (inverted) {
                    hql += "NOT ";
                }

                hql += "p.customer.id = " + customerID;
            }

            products = session.createQuery(hql, Product.class).list();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return products;
    }
}
