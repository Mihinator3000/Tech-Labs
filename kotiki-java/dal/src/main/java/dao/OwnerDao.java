package dao;

import models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import java.util.List;

public class OwnerDao {

    public List<Owner> getAll() {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("from Owner order by id", Owner.class)
                .list();
    }

    public Owner get(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(Owner.class, id);
    }

    public void add(Owner owner) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        session.save(owner);
        transaction.commit();
        session.close();
    }

    public void update(Owner owner) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        session.merge(owner);
        transaction.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Owner owner = session.get(Owner.class, id);

        Transaction transaction = session.beginTransaction();
        session.delete(owner);
        transaction.commit();
        session.close();
    }
}
