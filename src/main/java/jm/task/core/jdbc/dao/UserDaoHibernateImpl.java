package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();
    Session session = null;
    Transaction transaction = null;
    Query query;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(" +
                    "id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name text NOT NULL, " +
                    "lastName text NOT NULL, " +
                    "age int NOT NULL)");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("DROP TABLE IF EXISTS Users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?)");
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("DELETE FROM Users WHERE id = ?");
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("SELECT * FROM Users").addEntity(User.class);
            userList = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            query = session.createSQLQuery("DELETE FROM Users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            Util.closeSession(session);
        }
    }
}
