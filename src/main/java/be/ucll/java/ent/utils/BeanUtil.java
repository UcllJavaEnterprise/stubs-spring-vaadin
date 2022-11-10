package be.ucll.java.ent.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Beanutil is een utility class voor het inladen/ophalen van Spring Beans/Services
 * Omdat het zelf ook een @Service service is heeft hij toegang tot de Spring applicatiecontext
 * voor het ophalen of 'on the fly' instantiëren van Spring Beans.
 * <p>
 * Het wordt gebruikt omdat je Autowired Beans eigenlijk pas gebruikt worden NA de constructors allemaal zijn afgewerkt
 * Daardoor is het gebruik van een Autowired Bean in een Constructor vaak niet mogelijk. Via dit tooltje kan het toch.
 */
@Service
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
