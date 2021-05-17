package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Leonard", "Hofstadter", (byte)38);
        userDaoJDBC.saveUser("Sheldon", "Cooper", (byte)38);
        userDaoJDBC.saveUser("Howard", "Wolowitz", (byte)38);
        userDaoJDBC.saveUser("Raj", "Koothrappali", (byte)38);
        for (User user : userDaoJDBC.getAllUsers()) {
            System.out.println(user);
        }
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
