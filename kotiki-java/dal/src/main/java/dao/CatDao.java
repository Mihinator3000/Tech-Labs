package dao;

import models.Cat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactory;

import java.util.List;

public class CatDao implements Dao<Cat> {

    public List<Cat> getAll() {
        return HibernateSessionFactory
                .getSession()
                .createQuery("from Cat order by id", Cat.class)
                .list();
    }

    public Cat get(int id) {
        return HibernateSessionFactory
                .getSession()
                .get(Cat.class, id);
    }

    public void add(Cat cat) {
        Session session = HibernateSessionFactory.getSession();

        Transaction transaction = session.beginTransaction();
        session.save(cat);
        transaction.commit();
        session.close();
    }

    public void update(Cat cat) {
        Session session = HibernateSessionFactory.getSession();

        Transaction transaction = session.beginTransaction();
        session.merge(cat);
        transaction.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = HibernateSessionFactory.getSession();

        Cat cat = session.get(Cat.class, id);

        Transaction transaction = session.beginTransaction();
        session.delete(cat);
        transaction.commit();
        session.close();
    }
}
