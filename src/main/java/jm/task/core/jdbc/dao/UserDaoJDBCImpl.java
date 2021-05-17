package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection con = Util.getConnection();
    private PreparedStatement st;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Users(" +
                            "id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "name text NOT NULL, " +
                            "lastName text NOT NULL, " +
                            "age int NOT NULL)"
            );
            st.executeUpdate();
            con.commit();
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.closeQuietly(st);
        }
    }

    public void dropUsersTable() {
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
                    "DROP TABLE IF EXISTS Users"
            );
            st.executeUpdate();
            con.commit();
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.closeQuietly(st);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
                    "INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?)"
            );
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            con.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.closeQuietly(st);
        }
    }

    public void removeUserById(long id) {
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
                    "DELETE FROM Users WHERE id = ?"
            );
            st.setLong(1, id);
            st.executeUpdate();
            con.commit();
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.rollbackQuietly();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
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
            con.commit();
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.closeQuietly(st);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(
                    "DELETE FROM Users"
            );
            st.executeUpdate();
            con.commit();
        } catch (SQLException s) {
            Util.rollbackQuietly();
        } finally {
            Util.closeQuietly(st);
        }
    }
}
