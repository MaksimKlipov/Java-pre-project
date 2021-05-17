package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;
import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    private PreparedStatement st;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            st = Util.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Users(" +
                            "id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "name text NOT NULL, " +
                            "lastName text NOT NULL, " +
                            "age int NOT NULL)"
            );
            st.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Failed to create table:\n" + s);
        } finally {
            try {
                assert st != null;
                st.close();
            } catch (SQLException s) {
                System.out.println("Error:\n" + s);
            }
        }
    }

    public void dropUsersTable() {
        try {
            st = Util.getConnection().prepareStatement(
                    "DROP TABLE IF EXISTS Users"
            );
            st.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Error:\n" + s);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            st = Util.getConnection().prepareStatement(
                    "INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?)"
            );
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException s) {
            System.out.println("saveUser error:\n" + s);
        }
    }

    public void removeUserById(long id) {
        try {
            st = Util.getConnection().prepareStatement(
              "DELETE FROM Users WHERE id = ?"
            );
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Error:\n" + s);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            st = Util.getConnection().prepareStatement(
                    "SELECT * FROM Users"
            );
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException s) {
            System.out.println("getAllUsers Error:\n" + s);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            st = Util.getConnection().prepareStatement(
                    "DELETE FROM Users"
            );
            st.executeUpdate();
        } catch (SQLException s) {
            System.out.println("Error:\n" + s);
        }
    }
}
