/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.services;

import bg.sit.business.HibernateUtil;
import bg.sit.business.ValidationUtil;
import java.util.logging.Logger;
import javax.validation.Validator;
import org.hibernate.SessionFactory;

/**
 *
 * @author Dell
 */
public class BaseService {

    protected SessionFactory sessionFactory;
    protected static Validator validator;
    protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public BaseService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        validator = ValidationUtil.getValidator();
    }
}
