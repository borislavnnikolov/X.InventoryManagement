/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 *
 * @author Dell
 */
public class BaseService {

    protected SessionFactory sessionFactory;

    public BaseService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
}
