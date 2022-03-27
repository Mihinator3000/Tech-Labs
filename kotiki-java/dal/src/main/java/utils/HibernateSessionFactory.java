package utils;

import models.Cat;
import models.Owner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            var configuration = new Configuration()
                    .configure()
                    .addAnnotatedClass(Cat.class)
                    .addAnnotatedClass(Owner.class);

            var registryBuilder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
        }

        return sessionFactory;
    }
}
