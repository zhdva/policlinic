package com.haulmont.testtask.dao;

import com.haulmont.testtask.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BaseDAO<T> implements IBaseDAO<T> {

    protected final SessionFactory sf;
    private final Class<T> type;

    public BaseDAO(final Class<T> type) {
        this.type = type;
        sf = HibernateConfig.getSessionFactory();
    }

    @Override
    public T getById(final Long id) {
        Session session = sf.openSession();
        T t = session.get(type, id);
        session.close();
        return t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session session = sf.openSession();
        String query = "from " + type.getName();
        List<T> list = (List<T>) session.createQuery(query).list();
        session.close();
        return list;
    }

    @Override
    public void add(final T t) {
        Session session = sf.openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(t);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(final T t) {
        Session session = sf.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(t);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(final T t) {
        Session session = sf.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(t);
        tx1.commit();
        session.close();
    }

}
