package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.saveUser("Leonard", "Hofstadter", (byte)38);
        userDao.saveUser("Sheldon", "Cooper", (byte)38);
        userDao.saveUser("Howard", "Wolowitz", (byte)38);
        userDao.saveUser("Raj", "Koothrappali", (byte)38);
        for (User u : userDao.getAllUsers()) {
            System.out.println(u);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
