package com.alastairappleton.services;

import com.alastairappleton.entity.Colour;
import com.alastairappleton.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ColourService {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Colour> findAll() {

        List<Colour> result;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("from Colour"); // Use the same name as the Entity class (not the database table, if different)
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            result = null;
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return result;

    }

    public Colour find(Integer id) {

        Colour colour = new Colour();
        Session session = null;

        try {
            session = sessionFactory.openSession();
            colour = (Colour) session.get(Colour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return colour;
    }

}
