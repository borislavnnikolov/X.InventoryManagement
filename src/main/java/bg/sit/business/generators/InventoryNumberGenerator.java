/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.business.generators;

import bg.sit.business.entities.Product;
import java.io.Serializable;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.annotations.Type;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;

/**
 *
 * @author Dell
 */
public class InventoryNumberGenerator extends SequenceStyleGenerator {

    public static final String SEPARATOR_PARAMETER = "codeNumberSeparator";
    public static final String SEPARATOR_DEFAULT = "-";

    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%05d";

    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
            Object object) throws HibernateException {
        return String.format(format, ((Product) object).getUser().getId(), ((Product) object).getProductType().getId(), super.generate(session, object));
    }

    public void configure(Type type, Properties params,
            ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        String separator = ConfigurationHelper.getString(SEPARATOR_PARAMETER, params, SEPARATOR_DEFAULT);
        String numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT).replace("%", "%2$");
        this.format = "%1$s" + separator + "%2$s" + separator + numberFormat;
    }

}
