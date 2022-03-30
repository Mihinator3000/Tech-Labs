package dao;

import models.Cat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import java.util.List;

public class CatDao {

    public List<Cat> getAll() {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("from Cat order by id", Cat.class)
                .list();
    }

    public Cat get(int id) {
        return HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .get(Cat.class, id);
    }

    public void add(Cat cat) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();
    }

    public void update(Cat cat) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        session.merge(cat);
        transaction.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = HibernateSessionFactory
                .getSessionFactory()
                .openSession();

        Cat cat = session.get(Cat.class, id);

        Transaction transaction = session.beginTransaction();
        session.delete(cat);
        transaction.commit();
        session.close();
    }
}
